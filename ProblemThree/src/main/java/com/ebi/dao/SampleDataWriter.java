package com.ebi.dao;

import com.ebi.model.BioSample;

import java.util.Map;

/**
 * Generic interface for a bio-sample writer service.
 * writes samples data stored as a map of @{@link BioSample} objects, into a data sink.
 * @see SampleFileWriter, {@link SampleDBWriter} for implementations which uses TSV file & DB table respectively.
 *
 * Created by abdu on 10/21/2017.
 */
public interface SampleDataWriter {

    void writeSampleData(Map<String, BioSample> bioSamples) throws Exception;
}
