package com.ebi.dao;

import com.ebi.helper.Constants;
import com.ebi.model.BioSample;
import com.ebi.model.LineEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reader class which reads sample data provided in given file and returns a Map of
 * samples @{@link BioSample} with its provided details.
 * The file is in a TSV (Tab Separated Value) file format, where each row includes at most three values.
 * First value is the sample id, second value is an attribute name, followed by its value.
 *
 *  Example records:
    ERS008228	sex	female
    ERS000030	Country	Czech Republic
    ERS000042	Longitude	-83
    ERS000042	Latitude	40
 *
 * The reader class uses an attributes mapping Map to group synonym attributes under one key attribute.
 * The attributes mapping is defined in an 'xslx' excel file read and provided by by
 * @{@link com.ebi.helper.AttributeMappingReader}.
 * The reader class maintains a Map of samples with sample ids, and its associated attributes represented
 * as @{@link BioSample} object. @{@link LinkedHashMap} is used as to maintain insertion order.
 *
 * Created by abdu on 10/18/2017.
 */
public class SampleFileReader {

    private static final Logger logger = LoggerFactory.getLogger(SampleFileReader.class);
    private Map<String, Set<String>> attributeMappings = new HashMap<>();
    private Map<String, BioSample> bioSamples = new LinkedHashMap<>();

    /**
     * Reads a TSV file and returns Map for each sample with its provided information.
     * @param file TSV sample file with triples sample id, attribute name, attribute value
     * @return Map of sample ids and @{@link BioSample} with the sample key attributes
     * filled where provided in the file.
     * @throws IOException if the given file can't be read
     */
    public Map<String, BioSample> readSampleTSVFile(String file) throws IOException {
        logger.debug("Reading sample input file: {}", file);
        bioSamples = new LinkedHashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {
            List<String> lines = br.lines().collect(Collectors.toList());
            lines.forEach(line -> {
                LineEntry lineEntry = parseSingleLine(line, Constants.TAB_DELIMITER);
                if(lineEntry != null)
                    updateSamples(lineEntry);
            });
            logger.debug("Read {} lines", lines.size());
        }
        return bioSamples;
    }

    //TODO some lines doesn't have 3 cols
    /**
     * Parses a single line into sampleId, attribute name, and attribute value.
     * @param line a single line read from the TSV file
     * @param delimiter used as value separator (Tab '\t' is used here)
     * @return representation of the three values (if any) as a @{@link LineEntry} object.
     * if the third value (attribute value) is not found in the line, its assumed as 'null'.
     * The key attribute name is set on the object, if found, based on the attributes mapping Map.
     */
    private LineEntry parseSingleLine(String line, String delimiter) {
        String[] lineValues = line.split(delimiter);
        //assert(lineValues.length == 3);
        LineEntry lineEntry = null;
        if(lineValues.length >= 3) {
            lineEntry = new LineEntry(lineValues[0], lineValues[1], lineValues[2]);
            lineEntry.setAttributeKey(getAttributeMapping(lineEntry.getAttribute()));
        } else if(lineValues.length == 2) {
            lineEntry = new LineEntry(lineValues[0], lineValues[1], null);
            lineEntry.setAttributeKey(getAttributeMapping(lineEntry.getAttribute()));
        }
        logger.trace("{}", lineEntry);
        return lineEntry;
    }

    /**
     * Checks the key attribute name mapping for the given attribute name.
     * @param attribute the attribute name which is assumed to be grouped under a specific key attribute.
     * @return the key attribute name from the attributes mapping Map where the given input is an
     * element of the group. Return null if the mapping collection is empty or no map is found.
     */
    private String getAttributeMapping(String attribute) {
        logger.trace("checking for attribute: {}", attribute);
        for (Map.Entry<String, Set<String>> entry: attributeMappings.entrySet()) {
            if (entry.getValue().contains(attribute)) {
                logger.trace("found: {}", entry.getKey());
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Gets the sampleId from the current line entry & update the samples map.
     * The mapped attribute value from the given line entry is updated in the sample object.
     * If no sample entry with the given sample id exists yet in the map, a new @{@link BioSample} object
     * is created and put into the map.
     * @param lineEntry a single line read from the TSV file with sampleId, attribute name, and attribute value.
     */
    private void updateSamples(LineEntry lineEntry) {
        String sampleId = lineEntry.getSampleId();
        BioSample sample = bioSamples.get(sampleId);
        if(sample == null)
            bioSamples.put(sampleId, new BioSample(sampleId));
        updateSampleAttribute(bioSamples.get(sampleId), lineEntry);
    }

    /**
     * Update the given sample attribute with the given value.
     * @param sample the sample object which needs to update its state.
     * @param lineEntry line entry having the key attribute name (used to specify which
     * property to set in the sample object), the value to assign for the sample object property.
     * The implementation will set or concatenate the value whether the attribute has a value before or not.
     * The sample summary statistics @{@link com.ebi.model.BioSample.SampleSummary} is also updated.
     *
     * Latitude & Longitude values are specially handled to maintain a specific order for their values.
     * Depth & collection date values are also anticipated to have ranges (ex. 0-15 , 1980/2015).
     */
    private void updateSampleAttribute(BioSample sample, LineEntry lineEntry) {
        String keyAttribute = lineEntry.getAttributeKey();
        String value = lineEntry.getValue();
        String attribute = lineEntry.getAttribute();
        if(keyAttribute == null) {
            sample.setNonMappedAttribute();
            return;
        }

        switch (keyAttribute) {
            case "Cell type":
                sample.setCellType(value);
                break;
            case "Cell line":
                sample.setCellLine(value);
                break;
            case "Sex":
                sample.setSex(value);
                break;
            case "Depth":
                String attribute_lower = attribute.toLowerCase();
                if(attribute_lower.contains("start")) {
                    sample.setDepthStart(value);
                } else if(attribute_lower.contains("start")) {
                    sample.setDepthEnd(value);
                } else {
                    sample.setDepth(value);
                }
                break;
            case "Collection date":
                sample.setCollectionDate(value);
                break;
            case "latitude and longitude":
                //TODO check locales
                attribute_lower = attribute.toLowerCase();
                if(attribute_lower.contains("lat")) {
                    if(attribute_lower.contains("lon") || attribute_lower.contains("lan")) {
                        sample.setLatitudeAndLongitude(value);
                    }
                    else {
                        sample.setLatitude(value);
                    }
                } else {
                    sample.setLongitude(value);
                }
                break;
            default:
                sample.setNonMappedAttribute();
                break;
        }
    }

    public void setAttributeMappings(Map<String, Set<String>> attributeMappings) {
        this.attributeMappings = attributeMappings;
    }

}
