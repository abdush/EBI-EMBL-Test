package com.ebi.helper;

import com.ebi.model.BioSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by abdu on 10/18/2017.
 */

/**Challenges:
 * 1. Line Splitter attribute: different mappings, sometimes not exist in mapping
 *  1.1 simple solution:
 *      scan first token as sample id, loop through the keywords dictionary try to split by
 *      (problem some words are sub-words of others), then scan next as the value.
 *      need to store the delimiter used and find its key term.
 * 2. Read excel file by col values
 */
public class UtilHelper {

    private static final Logger logger = LoggerFactory.getLogger(UtilHelper.class);

    public static void printOrderedSamples(Map<String, BioSample> bioSamples) {
        bioSamples.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach((e) -> logger.debug("{}: {}", e.getKey(), e.getValue()));
    }
}
