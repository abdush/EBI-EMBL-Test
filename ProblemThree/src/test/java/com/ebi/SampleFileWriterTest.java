package com.ebi;

import com.ebi.dao.SampleFileWriter;
import com.ebi.model.BioSample;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abdu on 10/19/2017.
 */
public class SampleFileWriterTest {

    Map<String, BioSample> bioSamples = new HashMap<>();

    @BeforeClass
    public void setup() {
        BioSample sample1 = new BioSample("1");
        sample1.setCellLine("value1");
        sample1.setSex("male");
        sample1.setDepth("0-15");
        bioSamples.put(sample1.getSampleId(), sample1);

        BioSample sample2 = new BioSample("2");
        sample2.setCellType("value2");
        sample2.setSex("male");
        sample2.setCollectionDate("1900/2005");
        bioSamples.put(sample2.getSampleId(), sample2);
    }

    @Test
    public void writeOutputFile() throws IOException {
        String outputFile = "src\\test\\resources\\output_data1.txt";
        SampleFileWriter writer = new SampleFileWriter(outputFile);
        writer.writeSampleData(bioSamples);
    }
}
