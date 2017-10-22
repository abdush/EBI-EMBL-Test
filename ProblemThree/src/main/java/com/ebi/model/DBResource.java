package com.ebi.model;

/**
 * Representation for database resource.
 * Holds the db url, user, pass, and table name which are related to the sample data.
 * Input & Output db resources for the sample may be different.
 *
 * Created by abdu on 10/21/2017.
 */
public class DBResource {

    private String databaseUrl;
    private String userName;
    private String password;
    private String tableName;

    public DBResource(String databaseUrl, String userName, String password, String tableName) {
        this.databaseUrl = databaseUrl;
        this.userName = userName;
        this.password = password;
        this.tableName = tableName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
