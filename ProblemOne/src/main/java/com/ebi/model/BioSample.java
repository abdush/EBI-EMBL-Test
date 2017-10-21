package com.ebi.model;

/**
 * Created by abdu on 10/18/2017.
 */
public class BioSample {

    private static final String TAB_SEPARATOR = "\t";
    private static final String[] HEADINGS =
            {"sample ID", "cell type", "cell line", "sex", "depth", "collection date", "latitude and longitude"};

    private String sampleId;
    private String cellType;
    private String cellLine;
    private String sex;
    private String depth;
    private String collectionDate;
    private String latitudeAndLongitude;
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
        sampleSummary.incrementDepthAttrCounter();
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate=setOrConcatenate(this.collectionDate,collectionDate);
        sampleSummary.incrementCollectionDateAttrCounter();
    }

    public String getLatitudeAndLongitude() {
        return latitudeAndLongitude;
    }

    public void setLatitudeAndLongitude(String latitudeAndLongitude) {
        this.latitudeAndLongitude=setOrConcatenate(this.latitudeAndLongitude,latitudeAndLongitude);
        sampleSummary.incrementLatLongAttrCounter();
    }

    public SampleSummary getSampleSummary() {
        return sampleSummary;
    }

    public void setNonMappedAttribute() {
        sampleSummary.incrementOtherAttrCounter();
    }

    //If String is null, set the value otherwise concatenate to the existing value
    private String setOrConcatenate(String original, String value) {
        //TODO if value is null??
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
                .append(depth).append(TAB_SEPARATOR)
                .append(collectionDate).append(TAB_SEPARATOR)
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
        sb.append(", collectionDate='").append(collectionDate).append('\'');
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
