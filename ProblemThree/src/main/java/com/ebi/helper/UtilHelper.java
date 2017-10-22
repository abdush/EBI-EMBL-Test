package com.ebi.helper;

import com.ebi.model.BioSample;
import com.ebi.model.LineEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * Utility Helper class which is used for common functionality used by other classes.
 * Used to return mapping for attributes names, and update the @{@link BioSample} map with
 * a @{@link LineEntry} read from file or db table.
 *
 * Created by abdu on 10/18/2017.
 */
public class UtilHelper {

    private static final Logger logger = LoggerFactory.getLogger(UtilHelper.class);

    /**
     * Checks the key attribute name mapping for the given attribute name.
     * @param attributeMappings map which holds set of attribute names with a common key name
     * @param attribute the attribute name which is assumed to be grouped under a specific key attribute.
     * @return the key attribute name from the attributes mapping Map where the given input is an
     * element of the group. Return null if the mapping collection is empty or no map is found.
     */
    public static String getAttributeMapping(Map<String, Set<String>> attributeMappings, String attribute) {
        logger.trace("checking for attribute: {}", attribute);
        if(attribute == null)
            return null;
        for (Map.Entry<String, Set<String>> entry: attributeMappings.entrySet()) {
            if (entry.getValue().contains(attribute)) {
                logger.trace("found: {}", entry.getKey());
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Gets the sampleId from the current line entry & update the samples map.
     * The mapped attribute value from the given line entry is updated in the sample object.
     * If no sample entry with the given sample id exists yet in the map, a new @{@link BioSample} object
     * is created and put into the map.
     * @param bioSamples map of sample entries where the line entry details to be updated on the
     *                   sample with same sample id as the map key.
     * @param lineEntry a single line read from the TSV file with sampleId, attribute name, and attribute value.
     */
    public static void updateSamples(Map<String, BioSample> bioSamples, LineEntry lineEntry) {
        String sampleId = lineEntry.getSampleId();
        BioSample sample = bioSamples.get(sampleId);
        if(sample == null)
            bioSamples.put(sampleId, new BioSample(sampleId));
        updateSampleAttribute(bioSamples.get(sampleId), lineEntry);
    }

    /**
     * Update the given sample attribute with the given value.
     * @param sample the sample object which needs to update its state.
     * @param lineEntry line entry having the key attribute name (used to specify which
     * property to set in the sample object), the value to assign for the sample object property.
     * The implementation will set or concatenate the value whether the attribute has a value before or not.
     * The sample summary statistics @{@link com.ebi.model.BioSample.SampleSummary} is also updated.
     *
     * Latitude & Longitude values are specially handled to maintain a specific order for their values.
     * Depth & collection date values are also anticipated to have ranges (ex. 0-15 , 1980/2015).
     */
    private static void updateSampleAttribute(BioSample sample, LineEntry lineEntry) {
        String keyAttribute = lineEntry.getAttributeKey();
        String value = lineEntry.getValue();
        String attribute = lineEntry.getAttribute();
        if(keyAttribute == null) {
            sample.setNonMappedAttribute();
            return;
        }

        switch (keyAttribute) {
            case "Cell type":
                sample.setCellType(value);
                break;
            case "Cell line":
                sample.setCellLine(value);
                break;
            case "Sex":
                sample.setSex(value);
                break;
            case "Depth":
                String attribute_lower = attribute.toLowerCase();
                if(attribute_lower.contains("start")) {
                    sample.setDepthStart(value);
                } else if(attribute_lower.contains("start")) {
                    sample.setDepthEnd(value);
                } else {
                    sample.setDepth(value);
                }
                break;
            case "Collection date":
                sample.setCollectionDate(value);
                break;
            case "latitude and longitude":
                //TODO check locales
                attribute_lower = attribute.toLowerCase();
                if(attribute_lower.contains("lat")) {
                    if(attribute_lower.contains("lon") || attribute_lower.contains("lan")) {
                        sample.setLatitudeAndLongitude(value);
                    }
                    else {
                        sample.setLatitude(value);
                    }
                } else {
                    sample.setLongitude(value);
                }
                break;
            default:
                sample.setNonMappedAttribute();
                break;
        }
    }

    public static void printOrderedSamples(Map<String, BioSample> bioSamples) {
        bioSamples.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach((e) -> logger.debug("{}: {}", e.getKey(), e.getValue()));
    }
}
