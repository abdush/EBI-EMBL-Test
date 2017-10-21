package com.ebi.model;

/**
 * Created by abdu on 10/19/2017.
 */
public class LineEntry {

    private String sampleId;
    private String attribute;
    private String attributeKey;
    private String value;

    public LineEntry() {}

    public LineEntry(String sampleId, String attribute, String value) {
        this.sampleId = sampleId;
        this.attribute = attribute;
        this.value = value;
    }

    public LineEntry(String sampleId, String attribute, String attributeKey, String value) {
        this(sampleId, attribute, value);
        this.attributeKey = attributeKey;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LineEntry{");
        sb.append("sampleId='").append(sampleId).append('\'');
        sb.append(", attribute='").append(attribute).append('\'');
        sb.append(", attributeKey='").append(attributeKey).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
