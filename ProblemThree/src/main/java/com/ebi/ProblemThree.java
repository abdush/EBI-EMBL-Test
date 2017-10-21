package com.ebi;

import com.ebi.dao.SampleDBReader;
import com.ebi.dao.SampleDBWriter;
import com.ebi.helper.AppProperties;
import com.ebi.helper.AttributeMappingReader;
import com.ebi.helper.Constants;
import com.ebi.helper.SampleSummary;
import com.ebi.model.BioSample;
import com.ebi.model.DBResource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by abdu on 10/18/2017.
 */
public class ProblemThree {

    private static final Logger logger = LoggerFactory.getLogger(ProblemThree.class);

    public static void main(String[] args) {

        try {
            //Read DB config properties
            DBResource inputDB = null;
            DBResource outputDB= null;
            String mappingFile = null;
            try {
                AppProperties appProperties = new AppProperties();
                inputDB = appProperties.getInputDBConfig();
                outputDB = appProperties.getOutputDBConfig();
                mappingFile = appProperties.getAttributesMappingFile() != null?
                        appProperties.getAttributesMappingFile(): Constants.ATTRIBUTE_MAPPING_FILE;
            } catch (IOException e) {
                logger.debug("Cannot load properties file {}. Required file to specify DB config.", AppProperties.propertiesFile);
                System.exit(1);
            }

            //Read attributes mapping file

            AttributeMappingReader attributeMappingReader = null;
            try {
                attributeMappingReader = new AttributeMappingReader(mappingFile);
            } catch (IOException | InvalidFormatException e) {
                logger.error("Error: Cannot read attributes mapping file {}", mappingFile);
                logger.error("Exception: " + e);
                System.exit(1);
            }

            //Read sample input table
            SampleDBReader sampleDBReader = new SampleDBReader(inputDB);
            sampleDBReader.setAttributeMappings(attributeMappingReader.getAttributeMappings());
            Map<String, BioSample> bioSamples = null;
            try {
                bioSamples = sampleDBReader.readSampleData();
            } catch (SQLException e) {
                logger.error("Error: Cannot read sample input table {}", Constants.INPUT_TABLE);
                logger.error("Exception: " + e);
                System.exit(1);
            }

            //Write output table
            SampleDBWriter sampleDBWriter = new SampleDBWriter(outputDB);
            try {
                sampleDBWriter.writeSampleData(bioSamples);
            } catch (SQLException e) {
                logger.error("Error: Cannot write sample output table {}", Constants.OUTPUT_TABLE);
                logger.error("Exception: " + e);
                logger.error("Exception: ", e);
                System.exit(1);
            }

            //Show some summary details
            SampleSummary sampleSummary = new SampleSummary(bioSamples);
            sampleSummary.showSummary();

        } catch (Exception e) {
            logger.error("Error occurred while processing..");
            logger.error("Exception: ", e);
            logger.error("Exception: " + e);
        }

    }
}
