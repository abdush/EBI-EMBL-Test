package com.ebi;

import com.ebi.helper.AttributeMappingReader;
import com.ebi.helper.SampleFileReader;
import com.ebi.helper.SampleFileWriter;
import com.ebi.model.BioSample;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private Map<String, Set<String>> attributeMappings;
    private SampleFileReader reader;
    private Map<String, BioSample> bioSamples = new HashMap<>();

    @BeforeClass
    public void setup() throws IOException, InvalidFormatException {
        AttributeMappingReader attributeMappingReader = new AttributeMappingReader(mappingFile);
        attributeMappings = attributeMappingReader.getAttributeMappings();
        reader = new SampleFileReader();
        reader.setAttributeMappings(attributeMappings);
    }

    @Test
    //This is hack to test private helper methods and probably should not be used!
    public void checkAttributeMapping() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //existing mapped attribute
        Method method = SampleFileReader.class.getDeclaredMethod("getAttributeMapping", String.class);
        method.setAccessible(true);
        String mappedAttribute = (String) method.invoke(reader, "geographic location (latitude and longitude)");
        assertThat(mappedAttribute).isEqualTo("latitude and longitude");

        //existing non-mapped attribute
        mappedAttribute = (String) method.invoke(reader, "geographic location (country)");
        assertThat(mappedAttribute).isNull();

        //non-existing attribute
        mappedAttribute = (String) method.invoke(reader, "non-existing attribute");
        assertThat(mappedAttribute).isNull();
    }

    @Test
    public void updateSampleAttribute() {

    }

    //TODO test when line has 0, 1, 2, 3 null values
    @Test
    public void parseInputFileTest1() throws IOException {
        String inputFile = "src\\test\\resources\\input_test1.txt";
        Map<String, BioSample> samples = reader.readSampleTSVFile(inputFile);
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
        Map<String, BioSample> samples = reader.readSampleTSVFile(inputFile);
        assertThat(samples).hasSize(3);
        assertThat(samples).containsKey("ERS000042");
        BioSample bioSample = samples.get("ERS000042");
        assertThat(bioSample).isNotNull();
        assertThat(bioSample).hasFieldOrPropertyWithValue("latitudeAndLongitude", "-83 | 40");
        assertThat(bioSample).hasFieldOrPropertyWithValue("sex", null);
        assertThat(bioSample).hasFieldOrPropertyWithValue("depth", null);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("sexAttrCounter", 0);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("latLongAttrCounter", 2);
        assertThat(bioSample.getSampleSummary()).hasFieldOrPropertyWithValue("otherAttrCounter", 0);
    }

    @Test
    public void parseInputFile() throws IOException, InvalidFormatException {
        String inputFile = "src\\test\\resources\\input_data.txt";
        bioSamples = reader.readSampleTSVFile(inputFile);
        //logger.debug("{}", bioSamples.size());
        assertThat(bioSamples).hasSize(4607);
    }

    @Test(dependsOnMethods = {"parseInputFile"})
    public void writeOutputFile() throws IOException {
        String outputFile = "src\\test\\resources\\output_data.txt";
        SampleFileWriter writer = new SampleFileWriter();
        writer.writeOutputFile(bioSamples, outputFile);
    }

}
