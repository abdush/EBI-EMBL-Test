package com.ebi;

import com.ebi.helper.AttributeMappingReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by abdu on 10/18/2017.
 */
public class AttributeMappingReaderTest {

    private Logger logger = LoggerFactory.getLogger(AttributeMappingReaderTest.class);
    private static final String testFile = "src\\test\\resources\\attribute_mappings.xlsx";
    private Map<String, Set<String>> attributeMappings;

    @Test
    public void readAttributeMappingFile() throws IOException, InvalidFormatException {
        logger.debug("Reading attributes mapping file: {}", testFile);
        AttributeMappingReader reader = new AttributeMappingReader(testFile);
        attributeMappings = reader.getAttributeMappings();
        assertThat(attributeMappings).hasSize(6);
        assertThat(attributeMappings).containsOnlyKeys("Cell line", "Cell type", "Sex","Collection date","Depth", "latitude and longitude");
        assertThat(attributeMappings.get("Cell line")).hasSize(13);
        assertThat(attributeMappings.get("Cell type")).hasSize(5);
        assertThat(attributeMappings.get("Sex")).hasSize(6);
        assertThat(attributeMappings.get("Collection date")).hasSize(15);
        assertThat(attributeMappings.get("Depth")).hasSize(18);
        assertThat(attributeMappings.get("latitude and longitude")).hasSize(28);
        assertThat(attributeMappings.get("Collection date")).contains("COLLECTION_DATE");
    }

}
