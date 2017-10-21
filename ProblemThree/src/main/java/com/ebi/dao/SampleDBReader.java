package com.ebi.dao;

import com.ebi.model.BioSample;
import com.ebi.model.DBResource;
import com.ebi.model.LineEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by abdu on 10/21/2017.
 */
public class SampleDBReader implements SampleDataReader {
    private static final Logger logger = LoggerFactory.getLogger(SampleDBReader.class);
    private DBResource dbResource;
    private Map<String, Set<String>> attributeMappings = new HashMap<>();
    private Map<String, BioSample> bioSamples = new LinkedHashMap<>();

    public SampleDBReader(DBResource dbResource) {
        this.dbResource = dbResource;
    }

    public Map<String, BioSample> readSampleData() throws SQLException {
        logger.debug("Reading from input table: {}", dbResource.getTableName());
        String query = "SELECT * FROM " + dbResource.getTableName();
        processQueryResult(dbResource.getDatabaseUrl(),
                dbResource.getUserName(), dbResource.getPassword(), query);
        return bioSamples;
    }

    public void processQueryResult(String dbUrl, String user, String pass, String selectQuery) throws SQLException {
        try(Connection connection = DriverManager.getConnection(dbUrl, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery)) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            logger.debug("# of Columns {}", resultSetMetaData.getColumnCount());
            int count = 0;
            while (resultSet.next()) {
                String sampleId = resultSet.getString(1);
                String attribute = resultSet.getString(2);
                String value = resultSet.getString(3);
                LineEntry lineEntry = new LineEntry(sampleId, attribute, value);
                lineEntry.setAttributeKey(getAttributeMapping(lineEntry.getAttribute()));
                logger.trace("Row {}", lineEntry);
                updateSamples(lineEntry);
                count++;
            }
            logger.debug("# of Rows {}", count);
        }
    }

    //Checks the mapping for the given attribute.
    // return null if the mapping collection is empty or no map is found.
    private String getAttributeMapping(String attribute) {
        logger.trace("checking for attribute: {}", attribute);
        for (String key : attributeMappings.keySet()) {
            for (String value : attributeMappings.get(key)) {
                if (attribute.equals(value)) {
                    logger.trace("found: {}", key);
                    return key;
                }
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

    //Update the given sample attribute(from the  with the given value.
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

    //TODO: constructor(what if no mappings?)
    public void setAttributeMappings(Map<String, Set<String>> attributeMappings) {
        this.attributeMappings = attributeMappings;
    }

    public DBResource getDbResource() {
        return dbResource;
    }

    public void setDbResource(DBResource dbResource) {
        this.dbResource = dbResource;
    }
}
