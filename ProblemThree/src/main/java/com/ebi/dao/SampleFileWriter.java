package com.ebi.dao;

import com.ebi.model.BioSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Writer class which writes bio-sample records into a TSV (Tab Separated Value) file.
 * Accepts the output TSV file path.
 * Given a map of sample objects @{@link BioSample}, it writes the sample information into the TSV file.
 * Each line contains the provided attributes for a specific sample.
 *
 * Example output:
 sample ID	cell type	cell line	sex	depth start	depth end	collection date start	collection date end	latitude and longitude
 1	null	value1	male	0	15	null	null	null
 2	value2	null	male	null	null	1900	2005	null
 *
 * Created by abdu on 10/19/2017.
 */
public class SampleFileWriter implements SampleDataWriter{

    private static final Logger logger = LoggerFactory.getLogger(SampleFileWriter.class);
    private String filePath;

    public SampleFileWriter(String filePath) {
        this.filePath = filePath;
    }

    //TODO ordering
    /**
     * Writes the bio-samples map into the file specified in the object field.
     * @param bioSamples map of samples records to be written to the file.
     * @throws IOException if file can't be written.
     * File starts with headings for the attributes names.
     * Writes each sample record into its own line in a TSV format.
     */
    public void writeSampleData(Map<String, BioSample> bioSamples) throws IOException {
        logger.debug("Writing to sample output file: {}", filePath);
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(filePath))) {
            //Write header line
            bufferedWriter.write(BioSample.getHeadingsTSV());
            bufferedWriter.newLine();

            //Write Samples details
            for(BioSample bioSample: bioSamples.values()) {
                bufferedWriter.write(bioSample.toTSVString());
                bufferedWriter.newLine();
            }
        }
        logger.debug("Wrote {} lines", bioSamples.size());
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
