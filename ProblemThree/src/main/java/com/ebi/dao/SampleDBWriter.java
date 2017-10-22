package com.ebi.dao;

import com.ebi.helper.Constants;
import com.ebi.model.BioSample;
import com.ebi.model.DBResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;

/**
 * Writer class which writes bio-sample records into a db table.
 * Accepts @{@link DBResource} which specifies the output db url & table name.
 * Given a map of sample objects @{@link BioSample}, it writes the sample information into the table.
 * Each row contains the provided attributes for a specific sample.
 *
 * Example output:
 sample_id	cell_type	cell_line	sex	depth_start	depth_end	collection_date_start	collection_date_end	latitude_and_longitude
 1	null	value1	male	0	15	null	null	null
 2	value2	null	male	null	null	1900	2005	null
 *
 * Created by abdu on 10/21/2017.
 */
public class SampleDBWriter implements SampleDataWriter {

    private static final Logger logger = LoggerFactory.getLogger(SampleDBWriter.class);
    private DBResource dbResource;

    public SampleDBWriter(DBResource dbResource) {
        //Assertion for Non-null fields
        this.dbResource = dbResource;
    }

    public void writeSampleData(Map<String, BioSample> bioSamples) throws SQLException {
        logger.debug("Writing to db output table: {}", dbResource.getTableName());
        executeQuery(dbResource, bioSamples);
    }

    /**
     * Writes the bio-samples map into the db table specified in the given db resource.
     * @param dbResource db url, user, pass, and table where the output to be stored.
     * @param bioSamples map of samples records to be written to the file.
     * @throws SQLException if any db issue occurs.
     *
     * The output table with columns for the sample attribute will be created.
     * If a table with same name exists in the db, it will be dropped first.
     * Writes each sample record into its own row in the table.
     * The insert statement uses a batch with default size of 1000.
     */
    private void executeQuery(DBResource dbResource, Map<String, BioSample> bioSamples) throws SQLException {
        String tableName = dbResource.getTableName();
        String dropTable =
                "DROP TABLE IF EXISTS " + tableName;
        String createTable =
                "CREATE TABLE " + tableName + " (sample_id varchar(20) NOT NULL, " +
                        "cell_type varchar(250), " + "cell_line varchar(250), " + "sex varchar(250), " +
                        "depth_start varchar(250), " + "depth_end varchar(250), " + "collection_date_start varchar(250), " +
                        "collection_date_end varchar(250), " + "latitude_and_longitude varchar(250), " + "PRIMARY KEY (sample_id))";
        String insertData =
                "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(dbResource.getDatabaseUrl(),
                dbResource.getUserName(), dbResource.getPassword());
             Statement statement = connection.createStatement()) {

            logger.debug("Dropping table: {}", tableName);
            statement.executeUpdate(dropTable);

            logger.debug("Creating table: {}", tableName);
            statement.executeUpdate(createTable);

            //need to separate it from the first try cos of H2 test db error: table not found!
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {

                //TODO commit
                //Write Samples details
                long startTime = System.currentTimeMillis();

                int count = 0;
                for (BioSample bioSample : bioSamples.values()) {
                    preparedStatement.setString(1, bioSample.getSampleId());
                    preparedStatement.setString(2, bioSample.getCellType());
                    preparedStatement.setString(3, bioSample.getCellLine());
                    preparedStatement.setString(4, bioSample.getSex());
                    preparedStatement.setString(5, bioSample.getDepthStart());
                    preparedStatement.setString(6, bioSample.getDepthEnd());
                    preparedStatement.setString(7, bioSample.getFromCollectionDate());
                    preparedStatement.setString(8, bioSample.getToCollectionDate());
                    preparedStatement.setString(9, bioSample.getLatitudeAndLongitude());
                    preparedStatement.addBatch();
                    count++;
                    if (count % Constants.DB_BATCH_SIZE == 0)
                        preparedStatement.executeBatch();
                }
                preparedStatement.executeBatch();
                logger.debug("# of rows: {}", count);
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                logger.debug("Run insert queries in {} millisec", elapsedTime);
            }
        }
    }

    public DBResource getDbResource() {
        return dbResource;
    }

    public void setDbResource(DBResource dbResource) {
        this.dbResource = dbResource;
    }
}
