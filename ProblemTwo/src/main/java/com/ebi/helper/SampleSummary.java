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
        logger.debug("Showing Some Summary of sample data....");
        logger.debug("Total # of samples read: {}", getTotalNumberOfSamples());
        logger.debug("Total # of samples with more the one depth: {}",
                getNumberOfSamplesWithMoreThanOneDepthAttribute());
        logger.debug("Total # of samples with more the one collection date: {}",
                getNumberOfSamplesWithMoreThanOneDateAttribute());
        logger.debug("Total # of samples with more the one any attribute: {}",
                getNumberOfSamplesWithMoreThanOneAttribute());
    }

    public int getTotalNumberOfSamples() {
        return bioSamples.size();
    }

    public long getNumberOfSamplesWithMoreThanOneAttribute() {
        return bioSamples.values().stream()
                .filter(SampleSummary::attributeMoreThanOnce)
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
}
