package com.ebi.helper;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Reader class for attributes mapping file.
 * Reads an attribute mapping excel file (.xlsx) and stores the mappings in a Map<String, Set<String>>.
 * The map key string is the attribute key name, and the Set<String> value are all the attribute names
 * representing the same attribute.
 *
 * Example: SEX, sex, Sex, gender are all group under one key name, Sex.
 *
 * Created by abdu on 10/18/2017.
 */
public class AttributeMappingReader {

    private static final Logger logger = LoggerFactory.getLogger(AttributeMappingReader.class);
    private Map<String, Set<String>> attributeMappings = new HashMap<>();

    public AttributeMappingReader(String mappingFile) throws IOException, InvalidFormatException {
        try {
            this.attributeMappings = readAttributeMappings(mappingFile);
        } catch (IOException e) {
            logger.error("IO Error reading file ", e);
            throw e;
        } catch (InvalidFormatException e) {
            logger.error("Invalid format error reading file ", e);
            throw e;
        }
    }

    public Map<String, Set<String>> getAttributeMappings() {
        return attributeMappings;
    }

    /**
     * Reads an attribute mapping excel file and stores the mappings in a Map of string & set of strings.
     * @param file the attribute mapping file (.xlsx). Each column represents a group of related attributes.
     * @return Map of string & Set of strings, where the key is the attribute name,
     * the value is the set of strings under the same key name.
     * @throws IOException if file can't be read.
     * @throws InvalidFormatException if file format is not a valid xlsx file.
     */
    private Map<String, Set<String>> readAttributeMappings(String file) throws IOException, InvalidFormatException {
        logger.debug("Reading attributes mapping file: {}", file);

        Map<String, Set<String>> attributeMappings = new HashMap<>();
        List<String> headers = new ArrayList<>();
        Workbook wb = WorkbookFactory.create(new File(file));
        Sheet sheet = wb.getSheetAt(0);

        //Read first row for headers
        Row header = sheet.getRow(0);
        for(int i=0; i< header.getLastCellNum(); i++) {
            String cellValue = header.getCell(i).getStringCellValue();
            headers.add(cellValue);
            attributeMappings.put(cellValue, new HashSet<>());
        }

        //Read other rows for mapping , ignoring null/empty cells
        for (int j=1; j< sheet.getLastRowNum() + 1; j++) {
            Row row = sheet.getRow(j);
            for(int i=0; i< header.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if(cell != null)
                    attributeMappings.get(headers.get(i)).add(cell.getStringCellValue());
            }
        }
        //attributeMappings.forEach((k, v) -> System.out.println(k + ": " + v.size()));
        return attributeMappings;
    }


}
