package com.ebi.helper;

import com.ebi.model.BioSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by abdu on 10/20/2017.
 */
public class SampleSummary {

    private static final Logger logger = LoggerFactory.getLogger(SampleSummary.class);
    private Map<String, BioSample> bioSamples = new HashMap<>();

    public SampleSummary(Map<String, BioSample> bioSamples) {
        this.bioSamples = bioSamples;
    }

    public void showSummary() {
        logger.debug("Showing some Summary of sample data....");
        logger.debug("Total # of samples read: {}", getTotalNumberOfSamples());
        logger.debug("Total # of samples with lat & long attribute provided twice: {}",
                getNumberOfSamplesWithTwoLatLongAttribute());
        logger.debug("Total # of samples with depth attribute provided more than once: {}",
                getNumberOfSamplesWithMoreThanOneDepthAttribute());
        logger.debug("Total # of samples with collection date attribute provided more than once: {}",
                getNumberOfSamplesWithMoreThanOneDateAttribute());
        logger.debug("Total # of samples with (any) attribute provided more than once: {}",
                getNumberOfSamplesWithMoreThanOneAttribute());
        logger.debug("Total # of samples where depth attribute is provided: {}",
                getNumberOfSamplesWithAtLeastOneDepthAttribute());
        logger.debug("Distinct depth attribute values provided: {}",
                getUniqueDepthValues());
    }

    public int getTotalNumberOfSamples() {
        return bioSamples.size();
    }

    public long getNumberOfSamplesWithTwoLatLongAttribute() {
        return bioSamples.values().stream()
                .filter(sample -> sample.getSampleSummary().getLatLongAttrCounter() == 2)
                .count();
    }

    public long getNumberOfSamplesWithMoreThanOneAttribute() {
        return bioSamples.values().stream()
                .filter(SampleSummary::attributeMoreThanOnce)
                .count();
    }

    public long getNumberOfSamplesWithAtLeastOneDepthAttribute() {
        return bioSamples.values().stream()
                .filter(sample -> sample.getSampleSummary().getDepthAttrCounter() > 0)
                .count();
    }

    public long getNumberOfSamplesWithMoreThanOneDepthAttribute() {
        return bioSamples.values().stream()
                .filter(sample -> sample.getSampleSummary().getDepthAttrCounter() > 1)
                .count();
    }

    public long getNumberOfSamplesWithMoreThanOneDateAttribute() {
        return bioSamples.values().stream()
                .filter(sample -> sample.getSampleSummary().getCollectionDateAttrCounter() > 1)
                .count();
    }

    public List<BioSample> getSamplesWithMoreThanOneDepthAttribute() {
        return bioSamples.values().stream()
                .filter(sample -> sample.getSampleSummary().getDepthAttrCounter() > 1)
                .collect(Collectors.toList());
    }

    public List<BioSample> getSamplesWithMoreThanOneDateAttribute() {
        return bioSamples.values().stream()
                .filter(sample -> sample.getSampleSummary().getCollectionDateAttrCounter() > 1)
                .collect(Collectors.toList());
    }

    private static boolean attributeMoreThanOnce(BioSample bioSample) {
        BioSample.SampleSummary sampleSummary = bioSample.getSampleSummary();
        return sampleSummary.getCollectionDateAttrCounter() > 1 ||
                sampleSummary.getDepthAttrCounter() > 1 ||
                sampleSummary.getCellLineAttrCounter() > 1 ||
                sampleSummary.getCellTypeAttrCounter() > 1 ||
                sampleSummary.getLatLongAttrCounter() > 1 ||
                sampleSummary.getSexAttrCounter() > 1;
    }

    private List<String> getUniqueDepthValues() {
        return bioSamples.values().stream()
                .map(BioSample::getDepth)
                .distinct()
                .collect(Collectors.toList());
    }
}
