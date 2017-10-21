package com.ebi.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by abdu on 10/21/2017.
 */
public class AppProperties {

    private static final Logger logger = LoggerFactory.getLogger(AppProperties.class);
    public static final String propertiesFile = "/application.properties";
    private Properties properties = new Properties();

    public AppProperties() throws IOException {
        loadProperties();
    }

    public Properties loadProperties() throws IOException {
        properties = new Properties();
        try {
            logger.debug("Attempting to read properties file {} ", propertiesFile);
            InputStream inputStream = getClass().getResourceAsStream(propertiesFile);
            if(inputStream == null)
                throw new FileNotFoundException("FileNotFound");
            properties.load(inputStream);
        } catch (IOException e) {
            logger.debug("Error loading properties file", e);
            throw e;
        }
        logger.debug("Read properties file {} ", propertiesFile);
        return properties;
    }

    public String getAttributesMappingFile() {
        String path = properties.getProperty("mapping.file");
        /*if(path == null)
            throw new RuntimeException("Missing required property for mapping file: " +
                    "mapping.file");*/
        return path;
    }

    public String getInputFile() {
        String path = properties.getProperty("input.file");
        /*if(path == null)
            throw new RuntimeException("Missing required property for input file: " +
                    "input.file");*/
        return path;
    }

    public String getOutputFile() {
        String path = properties.getProperty("output.file");
        /*if(path == null)
            throw new RuntimeException("Missing required property for output file: " +
                    "output.file");*/
        return path;
    }
}
