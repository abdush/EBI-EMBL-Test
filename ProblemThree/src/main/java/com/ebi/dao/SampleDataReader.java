package com.ebi.dao;

import com.ebi.model.BioSample;

import java.util.Map;

/**
 * Created by abdu on 10/21/2017.
 */
public interface SampleDataReader {

    Map<String, BioSample> readSampleData() throws Exception;
}
