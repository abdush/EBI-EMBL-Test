package com.ebi.helper;

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
public class SampleFileWriter {

    private static final Logger logger = LoggerFactory.getLogger(SampleFileWriter.class);

    //TODO ordering
    public void writeOutputFile(Map<String, BioSample> bioSamples, String file) throws IOException {
        logger.debug("Writing to sample output file: {}", file);
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(file))) {
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

}
