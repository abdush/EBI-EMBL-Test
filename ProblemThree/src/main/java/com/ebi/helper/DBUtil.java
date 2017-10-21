package com.ebi.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by abdu on 10/21/2017.
 */
public class DBUtil {

    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    Connection conn = null;

    public static Connection getConnection(String dbUrl, String user, String pass) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(dbUrl, user, pass);
        logger.debug("Connected to database {}", dbUrl);
        return conn;
    }

    public static ResultSet getQueryResult(String dbUrl, String user, String pass, String selectQuery) throws SQLException {
        try(Connection connection = DriverManager.getConnection(dbUrl, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery)) {
            return resultSet;
        }
    }


}
