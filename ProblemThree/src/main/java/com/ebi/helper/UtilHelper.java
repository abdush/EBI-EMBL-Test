package com.ebi.helper;

import com.ebi.model.BioSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by abdu on 10/18/2017.
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
