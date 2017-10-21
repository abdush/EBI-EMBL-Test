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
 * Created by abdu on 10/19/2017.
 */
public class SampleFileWriter implements SampleDataWriter{

    private static final Logger logger = LoggerFactory.getLogger(SampleFileWriter.class);
    private String filePath;

    public SampleFileWriter(String filePath) {
        this.filePath = filePath;
    }

    //TODO ordering
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
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}