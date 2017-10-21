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
 * Created by abdu on 10/18/2017.
 */
public class SampleFileReader {

    private static final Logger logger = LoggerFactory.getLogger(SampleFileReader.class);
    private Map<String, Set<String>> attributeMappings = new HashMap<>();
    private Map<String, BioSample> bioSamples = new LinkedHashMap<>();

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
    //Parses a single line into sampleId, attribute, and value
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

    //Checks the mapping for the given attribute.
    // return null if the mapping collection is empty or no map is found.
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

    //Gets the sampleId from the current line entry & update the samples map.
    //The mapped attribute value is updated in the sample object.
    private void updateSamples(LineEntry lineEntry) {
        String sampleId = lineEntry.getSampleId();
        BioSample sample = bioSamples.get(sampleId);
        if(sample == null)
            bioSamples.put(sampleId, new BioSample(sampleId));
        updateSampleAttribute(bioSamples.get(sampleId), lineEntry.getAttributeKey(), lineEntry.getValue());
    }

    //Update the given sample attribute with the given value.
    //The implementation will set or concatenate the value whether the attribute has a value before or not.
    //The sample summary statistics is updated.
    private BioSample updateSampleAttribute(BioSample sample, String attribute, String value) {
        if(attribute == null) {
            sample.setNonMappedAttribute();
            return sample;
        }

        switch (attribute) {
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
                sample.setDepth(value);
                break;
            case "Collection date":
                sample.setCollectionDate(value);
                break;
            case "latitude and longitude":
                sample.setLatitudeAndLongitude(value);
                break;
            default:
                sample.setNonMappedAttribute();
                break;
        }
        return sample;
    }

    public void setAttributeMappings(Map<String, Set<String>> attributeMappings) {
        this.attributeMappings = attributeMappings;
    }

}
