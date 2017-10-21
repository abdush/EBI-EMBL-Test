package com.ebi.helper;

/**
 * Created by abdu on 10/20/2017.
 */
public class Constants {

    public static final String ATTRIBUTE_MAPPING_FILE = "src\\main\\resources\\attribute_mappings.xlsx";
    public static final String INPUT_FILE = "src\\main\\resources\\input_data.txt";
    public static final String OUTPUT_FILE = "src\\main\\resources\\output_data.txt";
    public static final String INPUT_DB = "jdbc:mysql://localhost:3307/ebi?rewriteBatchedStatements=true";
    public static final String INPUT_USER = "admin";
    public static final String INPUT_PASS = "admin";
    public static final String INPUT_TABLE = "samples_input";
    public static final String OUTPUT_DB = "jdbc:mysql://localhost:3307/ebi?rewriteBatchedStatements=true";
    public static final String OUTPUT_USER = "admin";
    public static final String OUTPUT_PASS = "admin";
    public static final String OUTPUT_TABLE = "samples_output";
    public static final int DB_BATCH_SIZE = 1000;
    public static final String TAB_DELIMITER = "\\t";


}
