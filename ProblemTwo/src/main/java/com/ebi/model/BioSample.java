package com.ebi.model;

/**
 * Representation of bio-sample record.
 * Includes information such as sample id, cell type, cell line, sex, depth, etc.
 * It also includes summary statistics @{@link SampleSummary} for the attributes
 * showing how many time each attribute was actually set for this sample.
 * For any attribute, If it was set more than once, the attribute final value will be a
 * concatenation of the new value with previous one (using | symbol).
 *
 * For Longitude & Latitude: it attempts to always put them in the order (lat | long).
 * This is achieved by investigating the mapping file for the different possible values.
 *
 * Special handling is applied for depth & collection date fields to interpret ranges.
 * If depth value has a '-' symbol between two strings, the left part is assumed the depth start,
 * and the right part the depth end.
 * Similarly, if collection date has a '/' between two parts, the left part is assumed the
 * collection date start, and the right part the collection date end.
 *
 * Created by abdu on 10/18/2017.
 */
public class BioSample {

    private static final String TAB_SEPARATOR = "\t";
    private static final String[] HEADINGS =
            {"sample ID", "cell type", "cell line", "sex", "depth start", "depth end", "collection date start", "collection date end", "latitude and longitude"};

    private String sampleId;
    private String cellType;
    private String cellLine;
    private String sex;
    private String depth;
    private String depthStart;
    private String depthEnd;
    private String collectionDate;
    private String fromCollectionDate;
    private String toCollectionDate;
    private String latitudeAndLongitude;
    private String latitude;
    private String longitude;
    private SampleSummary sampleSummary = new SampleSummary();

    public BioSample() {}

    public BioSample(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType =setOrConcatenate(this.cellType, cellType);
        sampleSummary.incrementCellTypeAttrCounter();
    }

    public String getCellLine() {
        return cellLine;
    }

    public void setCellLine(String cellLine) {
        this.cellLine = setOrConcatenate(this.cellLine, cellLine);
        sampleSummary.incrementCellLineAttrCounter();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex=setOrConcatenate(this.sex,sex);
        sampleSummary.incrementSexAttrCounter();
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth=setOrConcatenate(this.depth,depth);
        setDepthRange(this.depth);
        //setDepthStart(this.depth);
        //setDepthEnd(this.depth);
        sampleSummary.incrementDepthAttrCounter();
    }

    public String getDepthStart() {
        return depthStart;
    }

    public void setDepthStart(String depthStart) {
        this.depthStart = depthStart;
    }

    public String getDepthEnd() {
        return depthEnd;
    }

    public void setDepthEnd(String depthEnd) {
        this.depthEnd = depthEnd;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate=setOrConcatenate(this.collectionDate,collectionDate);
        setCollectionDateRange(this.collectionDate);
        sampleSummary.incrementCollectionDateAttrCounter();
    }

    public String getFromCollectionDate() {
        return fromCollectionDate;
    }

    public void setFromCollectionDate(String fromCollectionDate) {
        this.fromCollectionDate = fromCollectionDate;
    }

    public String getToCollectionDate() {
        return toCollectionDate;
    }

    public void setToCollectionDate(String toCollectionDate) {
        this.toCollectionDate = toCollectionDate;
    }

    public String getLatitudeAndLongitude() {
        return latitudeAndLongitude;
    }

    public void setLatitudeAndLongitude(String latitudeAndLongitude) {
        this.latitudeAndLongitude=setOrConcatenate(this.latitudeAndLongitude,latitudeAndLongitude);
        sampleSummary.incrementLatLongAttrCounter();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        sampleSummary.incrementLatLongAttrCounter();
        if(latitude == null)
            return;
        this.latitudeAndLongitude = this.latitudeAndLongitude == null ?
                latitude : latitude + " | " + this.latitudeAndLongitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        sampleSummary.incrementLatLongAttrCounter();
        if(longitude == null)
            return;
        this.latitudeAndLongitude = this.latitudeAndLongitude == null ?
                longitude : this.latitudeAndLongitude + " | " + longitude;
    }

    public SampleSummary getSampleSummary() {
        return sampleSummary;
    }

    public void setNonMappedAttribute() {
        sampleSummary.incrementOtherAttrCounter();
    }

    //attempt to set depth range: start & end.
    //based on given data this can be as 0-15
    private void setDepthRange(String depth) {

        /*String[] parts = depth.split("\\+/-");
        if(parts.length == 2) {
            String leftOperand = parts[0].trim();
            String rightOperand = parts[1].trim();
            //TODO check number
            double leftValue = Double.valueOf(leftOperand);
            double rightValue = Double.valueOf(rightOperand);
            setDepthStart(String.valueOf(leftValue - rightValue));
            setDepthEnd(String.valueOf(leftValue + rightValue));
            return;
        }*/

        String[] parts = depth.split("-");
        if(parts.length == 2) {
            setDepthStart(parts[0]);
            setDepthEnd(parts[1]);
            return;
        }
        setDepthStart(depth);
        setDepthEnd(depth);
    }

    //attempt to set collection date range: start & end.
    //based on given data this can be as 1980/2015
    private void setCollectionDateRange(String collectionDate) {

        String[] parts = collectionDate.split("/");
        if(parts.length == 2) {
            setFromCollectionDate(parts[0]);
            setToCollectionDate(parts[1]);
            return;
        }

        setFromCollectionDate(collectionDate);
        setToCollectionDate(collectionDate);
    }

    private String[] splitRange(String rangeValue, String delimiter) {
        String[] parts = rangeValue.split(delimiter);
        if(parts.length == 2)
            return parts;
        return new String[] {rangeValue, rangeValue};
    }

    //If String is null, set the value otherwise concatenate to the existing value
    private String setOrConcatenate(String original, String value) {
        if(value == null)
            return original;
        return (original == null ? value: original + " | " + value);
    }

    public static String getHeadingsTSV() {
        StringBuilder sb = new StringBuilder();
        for(String str: HEADINGS) {
            sb.append(str).append(TAB_SEPARATOR);
        }
        return sb.toString();
    }

    public String toTSVString() {
        StringBuilder sb = new StringBuilder();
        sb.append(sampleId).append(TAB_SEPARATOR)
                .append(cellType).append(TAB_SEPARATOR)
                .append(cellLine).append(TAB_SEPARATOR)
                .append(sex).append(TAB_SEPARATOR)
                .append(depthStart).append(TAB_SEPARATOR)
                .append(depthEnd).append(TAB_SEPARATOR)
                .append(fromCollectionDate).append(TAB_SEPARATOR)
                .append(toCollectionDate).append(TAB_SEPARATOR)
                .append(latitudeAndLongitude);
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BioSample{");
        sb.append("sampleId='").append(sampleId).append('\'');
        sb.append(", cellType='").append(cellType).append('\'');
        sb.append(", cellLine='").append(cellLine).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", depth='").append(depth).append('\'');
        sb.append(", depthStart='").append(depthStart).append('\'');
        sb.append(", depthEnd='").append(depthEnd).append('\'');
        sb.append(", collectionDate='").append(collectionDate).append('\'');
        sb.append(", fromCollectionDate='").append(fromCollectionDate).append('\'');
        sb.append(", toCollectionDate='").append(toCollectionDate).append('\'');
        sb.append(", latitudeAndLongitude='").append(latitudeAndLongitude).append('\'');
        sb.append(", summary='").append(sampleSummary).append('\'');
        sb.append('}');
        return sb.toString();
    }

    //Shows summary of how many times the different attributes appears for a sample.
    public class SampleSummary {

        private int cellTypeAttrCounter;
        private int cellLineAttrCounter;
        private int sexAttrCounter;
        private int depthAttrCounter;
        private int collectionDateAttrCounter;
        private int latLongAttrCounter;
        private int otherAttrCounter;

        public void incrementCellTypeAttrCounter() {
            this.cellTypeAttrCounter++;
        }

        public void incrementCellLineAttrCounter() {
            this.cellLineAttrCounter++;
        }

        public void incrementSexAttrCounter() {
            this.sexAttrCounter++;
        }

        public void incrementDepthAttrCounter() {
            this.depthAttrCounter++;
        }

        public void incrementCollectionDateAttrCounter() {
            this.collectionDateAttrCounter++;
        }

        public void incrementLatLongAttrCounter() {
            this.latLongAttrCounter++;
        }

        public void incrementOtherAttrCounter() {
            this.otherAttrCounter++;
        }

        //Getters & Setters
        public int getCellTypeAttrCounter() {
            return cellTypeAttrCounter;
        }

        public void setCellTypeAttrCounter(int cellTypeAttrCounter) {
            this.cellTypeAttrCounter = cellTypeAttrCounter;
        }

        public int getCellLineAttrCounter() {
            return cellLineAttrCounter;
        }

        public void setCellLineAttrCounter(int cellLineAttrCounter) {
            this.cellLineAttrCounter = cellLineAttrCounter;
        }

        public int getSexAttrCounter() {
            return sexAttrCounter;
        }

        public void setSexAttrCounter(int sexAttrCounter) {
            this.sexAttrCounter = sexAttrCounter;
        }

        public int getDepthAttrCounter() {
            return depthAttrCounter;
        }

        public void setDepthAttrCounter(int depthAttrCounter) {
            this.depthAttrCounter = depthAttrCounter;
        }

        public int getCollectionDateAttrCounter() {
            return collectionDateAttrCounter;
        }

        public void setCollectionDateAttrCounter(int collectionDateAttrCounter) {
            this.collectionDateAttrCounter = collectionDateAttrCounter;
        }

        public int getLatLongAttrCounter() {
            return latLongAttrCounter;
        }

        public void setLatLongAttrCounter(int latLongAttrCounter) {
            this.latLongAttrCounter = latLongAttrCounter;
        }

        public int getOtherAttrCounter() {
            return otherAttrCounter;
        }

        public void setOtherAttrCounter(int otherAttrCounter) {
            this.otherAttrCounter = otherAttrCounter;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("SampleSummary{");
            sb.append("cellTypeAttrCounter=").append(cellTypeAttrCounter);
            sb.append(", cellLineAttrCounter=").append(cellLineAttrCounter);
            sb.append(", sexAttrCounter=").append(sexAttrCounter);
            sb.append(", depthAttrCounter=").append(depthAttrCounter);
            sb.append(", collectionDateAttrCounter=").append(collectionDateAttrCounter);
            sb.append(", latLongAttrCounter=").append(latLongAttrCounter);
            sb.append(", otherAttrCounter=").append(otherAttrCounter);
            sb.append('}');
            return sb.toString();
        }
    }
}
