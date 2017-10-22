package com.ebi.dao;

import com.ebi.helper.Constants;
import com.ebi.helper.UtilHelper;
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
 * Accepts the tSV file path.
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
 * The attributes mapping is defined in an 'xlsx' excel file read and provided by
 * @ {@link com.ebi.helper.AttributeMappingReader}.
 * The reader class maintains a Map of samples with sample ids, and its associated attributes represented
 * as @{@link BioSample} object. @{@link LinkedHashMap} is used as to maintain insertion order.
 *
 * Created by abdu on 10/18/2017.
 */
public class SampleFileReader implements SampleDataReader{

    private static final Logger logger = LoggerFactory.getLogger(SampleFileReader.class);
    private String filePath;
    private Map<String, Set<String>> attributeMappings = new HashMap<>();
    private Map<String, BioSample> bioSamples = new LinkedHashMap<>();

    public SampleFileReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the object's TSV file and returns Map for each sample with its provided information.
     * The TSV sample file with triples sample id, attribute name, attribute value
     * @return Map of sample ids and @{@link BioSample} with the sample key attributes
     * filled where provided in the file.
     * @throws IOException if the given file can't be read
     * Parses each single line and update the samples map accordingly.
     * @see @{@link UtilHelper}
     */
    public Map<String, BioSample> readSampleData() throws IOException {
        logger.debug("Reading sample input file: {}", filePath);
        bioSamples = new LinkedHashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            List<String> lines = br.lines().collect(Collectors.toList());
            //TODO not good practice to mutate in a Java8 stream!!
            lines.forEach(line -> {
                LineEntry lineEntry = parseSingleLine(line, Constants.TAB_DELIMITER);
                if(lineEntry != null)
                    UtilHelper.updateSamples(bioSamples, lineEntry);
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
        if (lineValues.length >= 3) {
            lineEntry = new LineEntry(lineValues[0], lineValues[1], lineValues[2]);
            lineEntry.setAttributeKey(UtilHelper.getAttributeMapping(attributeMappings, lineEntry.getAttribute()));
        } else if (lineValues.length == 2) {
            lineEntry = new LineEntry(lineValues[0], lineValues[1], null);
            lineEntry.setAttributeKey(UtilHelper.getAttributeMapping(attributeMappings, lineEntry.getAttribute()));
        }
        logger.trace("{}", lineEntry);
        return lineEntry;
    }

    public void setAttributeMappings(Map<String, Set<String>> attributeMappings) {
        this.attributeMappings = attributeMappings;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
