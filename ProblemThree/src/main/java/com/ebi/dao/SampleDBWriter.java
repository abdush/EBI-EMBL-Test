package com.ebi.dao;

import com.ebi.helper.Constants;
import com.ebi.model.BioSample;
import com.ebi.model.DBResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;

/**
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

    public void executeQuery(DBResource dbResource, Map<String, BioSample> bioSamples) throws SQLException {
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
                    //TODO batch
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
