package com.ebi.helper;

import com.ebi.model.DBResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Holds configuration parameters for the application.
 * Reads the properties file 'application.properties' under the resources folder (src/main/resources)
 * and set some configuration parameters.
 * Can be used before running the application to set location of the different files (mapping, input, output).
 * The file is also required to set the input & output database resources.
 *
 * Used properties:
 *  - mapping.file : for the attributes mapping file path.
 *  - input.file : for the samples input TSV file.
 *  - output.file : for the samples output TSV file.
 *  - input.db.url: url for the db where input sample data is stored.
 *  - input.db.user: user for the input db.
 *  - input.db.pass: password for the input db.
 *  - input.db.table: table name in input db where sample data are read from.
 *  - output.db.url: url for the db where output sample data is stored.
 *  - output.db.user: user for the output db.
 *  - output.db.pass: password for the output db.
 *  - output.db.table: table name in output db where sample data are written to.
 *
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

    //TODO check if keys are not provided
    public DBResource getInputDBConfig() {
        String url = properties.getProperty("input.db.url");
        String user = properties.getProperty("input.db.user");
        String pass = properties.getProperty("input.db.pass");
        String table = properties.getProperty("input.db.table");
        if(url == null || user == null || pass == null || table == null)
            throw new RuntimeException("Missing required property for input DB config: " +
                    "[input.db.url, input.db.user, input.db.pass, input.db.table]");
        return new DBResource(url, user, pass, table);
    }

    public DBResource getOutputDBConfig() {
        String url = properties.getProperty("output.db.url");
        String user = properties.getProperty("output.db.user");
        String pass = properties.getProperty("output.db.pass");
        String table = properties.getProperty("output.db.table");
        if(url == null || user == null || pass == null || table == null)
            throw new RuntimeException("Missing required property for output DB config: " +
                    "[output.db.url, output.db.user, output.db.pass, output.db.table]");
        return new DBResource(url, user, pass, table);
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
