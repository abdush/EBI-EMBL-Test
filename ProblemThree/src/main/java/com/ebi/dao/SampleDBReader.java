package com.ebi.dao;

import com.ebi.helper.UtilHelper;
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
 * Reader class which reads sample data provided in a db table and returns a Map of
 * samples @{@link BioSample} with its provided details.
 * Accepts @{@link DBResource} which specifies the input db url & table name.
 * Each row in the db table includes at most three values.
 * First value is the sample id, second value is an attribute name, followed by its value.
 *
 * Example records:
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
        bioSamples = new LinkedHashMap<>();
        logger.debug("Reading from input table: {}", dbResource.getTableName());
        String query = "SELECT * FROM " + dbResource.getTableName();
        processQueryResult(dbResource.getDatabaseUrl(),
                dbResource.getUserName(), dbResource.getPassword(), query);
        return bioSamples;
    }

    private void processQueryResult(String dbUrl, String user, String pass, String selectQuery) throws SQLException {
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
                lineEntry.setAttributeKey(UtilHelper.getAttributeMapping(attributeMappings, lineEntry.getAttribute()));
                UtilHelper.updateSamples(bioSamples,lineEntry);
                count++;
            }
            logger.debug("# of Rows {}", count);
        }
    }

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
