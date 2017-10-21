package com.ebi;

import com.ebi.dao.SampleDBReader;
import com.ebi.dao.SampleDBWriter;
import com.ebi.helper.AppProperties;
import com.ebi.helper.AttributeMappingReader;
import com.ebi.model.BioSample;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by abdu on 10/18/2017.
 */
public class SampleDBReaderWriterTest {
    private Logger logger = LoggerFactory.getLogger(SampleDBReaderWriterTest.class);
    private static final String mappingFile = "src\\test\\resources\\attribute_mappings.xlsx";
    private Map<String, Set<String>> attributeMappings;
    private SampleDBReader reader;
    private Map<String, BioSample> bioSamples = new HashMap<>();
    private AppProperties appProperties;

    @BeforeClass
    public void setup() throws IOException, InvalidFormatException {
        appProperties = new AppProperties();
        AttributeMappingReader attributeMappingReader = new AttributeMappingReader(mappingFile);
        attributeMappings = attributeMappingReader.getAttributeMappings();
        reader = new SampleDBReader(appProperties.getInputDBConfig());
        reader.setAttributeMappings(attributeMappings);
    }

    @Test
    public void parseInputTableTest1() throws SQLException {
        bioSamples = reader.readSampleData();
        assertThat(bioSamples).hasSize(4);
        assertThat(bioSamples).containsKey("ERS008227");
        BioSample bioSample = bioSamples.get("ERS008227");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("latitudeAndLongitude", "2.066667 W 12.65 N");
        assertThat(bioSample).hasFieldOrPropertyWithValue("sex", "female");
        assertThat(bioSample).hasFieldOrPropertyWithValue("depth", null);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("sexAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("latLongAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 1);
    }

    @Test(dependsOnMethods = {"parseInputTableTest1"})
    public void writeOutputFile() throws IOException, SQLException {
        SampleDBWriter writer = new SampleDBWriter(appProperties.getOutputDBConfig());
        writer.writeSampleData(bioSamples);
    }

}
