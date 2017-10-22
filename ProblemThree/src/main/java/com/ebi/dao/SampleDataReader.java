package com.ebi.dao;

import com.ebi.model.BioSample;

import java.util.Map;

/**
 * Generic interface for a bio-sample reader service.
 * Reads samples data from a source & return it as a map of @{@link BioSample} objects, with their unique ids.
 * @see SampleFileReader, {@link SampleDBReader} for implementations which uses TSV file & DB table respectively.
 *
 * Created by abdu on 10/21/2017.
 */
public interface SampleDataReader {

    Map<String, BioSample> readSampleData() throws Exception;
}
