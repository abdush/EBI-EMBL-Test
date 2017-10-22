package com.ebi;

import com.ebi.dao.SampleFileReader;
import com.ebi.dao.SampleFileWriter;
import com.ebi.helper.*;
import com.ebi.model.BioSample;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by abdu on 10/18/2017.
 */
public class ProblemOne {

    private static final Logger logger = LoggerFactory.getLogger(ProblemOne.class);

    public static void main(String[] args) {

        try {
            //Set the files path:
            // first attempt to load from application.properties,
            //second option use default files in resources folder
            AppProperties appProperties = new AppProperties();
            final String mappingFile = appProperties.getAttributesMappingFile() != null?
                    appProperties.getAttributesMappingFile(): Constants.ATTRIBUTE_MAPPING_FILE;
            final String inputFile = appProperties.getInputFile() != null ?
                    appProperties.getInputFile() : Constants.INPUT_FILE;
            final String outputFile = appProperties.getOutputFile() != null ?
                    appProperties.getOutputFile() : Constants.OUTPUT_FILE;

            //Read attributes mapping file
            AttributeMappingReader attributeMappingReader = null;
            try {
                attributeMappingReader = new AttributeMappingReader(mappingFile);
            } catch (IOException | InvalidFormatException e) {
                logger.error("Error: Cannot read attributes mapping file {}", mappingFile);
                logger.error("Exception: " + e);
                System.exit(1);
            }

            //Read sample input file
            SampleFileReader sampleFileReader = new SampleFileReader();
            sampleFileReader.setAttributeMappings(attributeMappingReader.getAttributeMappings());
            Map<String, BioSample> bioSamples = null;
            try {
                bioSamples = sampleFileReader.readSampleTSVFile(inputFile);
            } catch (IOException e) {
                logger.error("Error: Cannot read sample input file {}", inputFile);
                logger.error("Exception: " + e);
                System.exit(1);
            }

            //Write output file
            SampleFileWriter sampleFileWriter = new SampleFileWriter();
            try {
                sampleFileWriter.writeOutputFile(bioSamples, outputFile);
            } catch (IOException e) {
                logger.error("Error: Cannot write sample output file {}", outputFile);
                logger.error("Exception: " + e);
                System.exit(1);
            }

            //Show some summary details about the samples
            SampleSummary sampleSummary = new SampleSummary(bioSamples);
            sampleSummary.showSummary();

        } catch (Exception e) {
            logger.error("Error occurred while processing..");
            logger.error("Exception: " + e);
        }

    }
}
