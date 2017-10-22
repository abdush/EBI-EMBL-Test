package com.ebi;

import com.ebi.dao.SampleFileReader;
import com.ebi.dao.SampleFileWriter;
import com.ebi.helper.AttributeMappingReader;
import com.ebi.helper.UtilHelper;
import com.ebi.model.BioSample;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by abdu on 10/18/2017.
 */
public class SampleFileReaderTest {
    private Logger logger = LoggerFactory.getLogger(SampleFileReaderTest.class);
    private static final String mappingFile = "src\\test\\resources\\attribute_mappings.xlsx";
    private String inputFile = "src\\test\\resources\\input_test1.txt";
    private Map<String, Set<String>> attributeMappings;
    private SampleFileReader reader;
    private Map<String, BioSample> bioSamples = new HashMap<>();

    @BeforeClass
    public void setup() throws IOException, InvalidFormatException {
        AttributeMappingReader attributeMappingReader = new AttributeMappingReader(mappingFile);
        attributeMappings = attributeMappingReader.getAttributeMappings();
        reader = new SampleFileReader(inputFile);
        reader.setAttributeMappings(attributeMappings);
    }

    @Test
    public void checkAttributeMapping() {
        String mappedAttribute = UtilHelper.getAttributeMapping(
                attributeMappings, "geographic location (latitude and longitude)");
        assertThat(mappedAttribute).isEqualTo("latitude and longitude");

        //existing non-mapped attribute
        mappedAttribute = UtilHelper.getAttributeMapping(
                attributeMappings, "geographic location (country)");
        assertThat(mappedAttribute).isNull();

        //non-existing attribute
        mappedAttribute = UtilHelper.getAttributeMapping(
                attributeMappings, "non-existing attribute");
        assertThat(mappedAttribute).isNull();
    }

    //TODO test when line has 0, 1, 2, 3 null values
    @Test
    public void parseInputFileTest1() throws IOException {
        String inputFile = "src\\test\\resources\\input_test1.txt";
        reader.setFilePath(inputFile);
        Map<String, BioSample> samples = reader.readSampleData();
        assertThat(samples).hasSize(1);
        assertThat(samples).containsKey("ERS008227");
        BioSample bioSample = samples.get("ERS008227");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("latitudeAndLongitude", "2.066667 W 12.65 N");
        assertThat(bioSample).hasFieldOrPropertyWithValue("sex", "female");
        assertThat(bioSample).hasFieldOrPropertyWithValue("depth", null);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("sexAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("latLongAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 1);
    }

    @Test
    public void parseInputFileTest2() throws IOException {
        String inputFile = "src\\test\\resources\\input_test2.txt";
        reader.setFilePath(inputFile);
        Map<String, BioSample> samples = reader.readSampleData();
        assertThat(samples).hasSize(3);
        assertThat(samples).containsKey("ERS000042");
        BioSample bioSample = samples.get("ERS000042");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("latitudeAndLongitude", "40 | -83");
        assertThat(bioSample).hasFieldOrPropertyWithValue("sex", null);
        assertThat(bioSample).hasFieldOrPropertyWithValue("depth", null);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("sexAttrCounter", 0);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("latLongAttrCounter", 2);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 0);
    }

    @Test
    public void parseInputFileTest3() throws IOException {
        String inputFile = "src\\test\\resources\\input_test3.txt";
        reader.setFilePath(inputFile);
        Map<String, BioSample> samples = reader.readSampleData();
        assertThat(samples).hasSize(2);
        assertThat(samples).containsKey("ERS017995");
        BioSample bioSample = samples.get("ERS017995");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("latitudeAndLongitude", "45° N, 93° W");
        assertThat(bioSample).hasFieldOrPropertyWithValue("sex", null);
        assertThat(bioSample).hasFieldOrPropertyWithValue("depth", "0-15");
        assertThat(bioSample).hasFieldOrPropertyWithValue("depthStart", "0");
        assertThat(bioSample).hasFieldOrPropertyWithValue("depthEnd", "15");
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("depthAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("latLongAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 4);

        assertThat(samples).containsKey("ERS016117");
        bioSample = samples.get("ERS016117");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("latitudeAndLongitude", "51.398 N 84.675 E");
        assertThat(bioSample).hasFieldOrPropertyWithValue("sex", null);
        assertThat(bioSample).hasFieldOrPropertyWithValue("depth", "1.7 +/- 0.3");
        //assertThat(bioSample).hasFieldOrPropertyWithValue("depthStart", "1.4");
        //assertThat(bioSample).hasFieldOrPropertyWithValue("depthEnd", "2.0");
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("depthAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("latLongAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 1);
    }

    @Test
    public void parseInputFileTest4() throws IOException {
        String inputFile = "src\\test\\resources\\input_test4.txt";
        reader.setFilePath(inputFile);
        Map<String, BioSample> samples = reader.readSampleData();
        assertThat(samples).hasSize(3);
        assertThat(samples).containsKey("ERS005609");
        BioSample bioSample = samples.get("ERS005609");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("collectionDate", "1800/2014");
        assertThat(bioSample).hasFieldOrPropertyWithValue("fromCollectionDate", "1800");
        assertThat(bioSample).hasFieldOrPropertyWithValue("toCollectionDate", "2014");
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("collectionDateAttrCounter", 1);

        assertThat(samples).containsKey("ERS009822");
        bioSample = samples.get("ERS009822");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("collectionDate", "1800-01-01/2015-01-01");
        assertThat(bioSample).hasFieldOrPropertyWithValue("fromCollectionDate", "1800-01-01");
        assertThat(bioSample).hasFieldOrPropertyWithValue("toCollectionDate", "2015-01-01");
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("collectionDateAttrCounter", 1);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 2);

        assertThat(samples).containsKey("ERS955657");
        bioSample = samples.get("ERS955657");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("collectionDate", "07-Nov-2012/31-Jan-2013");
        assertThat(bioSample).hasFieldOrPropertyWithValue("fromCollectionDate", "07-Nov-2012");
        assertThat(bioSample).hasFieldOrPropertyWithValue("toCollectionDate", "31-Jan-2013");
        //TODO 2?
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("collectionDateAttrCounter", 2);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 3);
    }

    @Test
    public void parseInputFile() throws IOException, InvalidFormatException {
        String inputFile = "src\\test\\resources\\input_data.txt";
        reader.setFilePath(inputFile);
        bioSamples = reader.readSampleData();
        //logger.debug("{}", bioSamples.size());
        assertThat(bioSamples).hasSize(4607);
    }

    @Test(dependsOnMethods = {"parseInputFile"})
    public void writeOutputFile() throws IOException {
        String outputFile = "src\\test\\resources\\output_data.txt";
        SampleFileWriter writer = new SampleFileWriter(outputFile);
        writer.writeSampleData(bioSamples);
    }

}
