package com.realstate.bdland.Database.Data_model;

public class IndividualSectorDataModel {

    private String BALCONY;
    private String BATHROOMS;
    private String BED_ROOMS;
    private String IMAGE_URL;
    private String KITCHEN;
    private String PROPERTY_TYPE;
    private String RENT_PER_MONTH;
    private String HOUSE_NUMBER;
    private String LOCATION;
    private String SECTOR_LOCATION;
    private String FLAT_DESCRIPTION;

    public String getBALCONY() {
        return BALCONY;
    }

    public void setBALCONY(String BALCONY) {
        this.BALCONY = BALCONY;
    }

    public String getBATHROOMS() {
        return BATHROOMS;
    }

    public void setBATHROOMS(String BATHROOMS) {
        this.BATHROOMS = BATHROOMS;
    }

    public String getBED_ROOMS() {
        return BED_ROOMS;
    }

    public void setBED_ROOMS(String BED_ROOMS) {
        this.BED_ROOMS = BED_ROOMS;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    public String getKITCHEN() {
        return KITCHEN;
    }

    public void setKITCHEN(String KITCHEN) {
        this.KITCHEN = KITCHEN;
    }

    public String getPROPERTY_TYPE() {
        return PROPERTY_TYPE;
    }

    public void setPROPERTY_TYPE(String PROPERTY_TYPE) {
        this.PROPERTY_TYPE = PROPERTY_TYPE;
    }

    public String getRENT_PER_MONTH() {
        return RENT_PER_MONTH;
    }

    public void setRENT_PER_MONTH(String RENT_PER_MONTH) {
        this.RENT_PER_MONTH = RENT_PER_MONTH;
    }

    public String getHOUSE_NUMBER() {
        return HOUSE_NUMBER;
    }

    public void setHOUSE_NUMBER(String HOUSE_NUMBER) {
        this.HOUSE_NUMBER = HOUSE_NUMBER;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getSECTOR_LOCATION() {
        return SECTOR_LOCATION;
    }

    public void setSECTOR_LOCATION(String SECTOR_LOCATION) {
        this.SECTOR_LOCATION = SECTOR_LOCATION;
    }

    public String getFLAT_DESCRIPTION() {
        return FLAT_DESCRIPTION;
    }

    public void setFLAT_DESCRIPTION(String FLAT_DESCRIPTION) {
        this.FLAT_DESCRIPTION = FLAT_DESCRIPTION;
    }

    public IndividualSectorDataModel() {
    }

    public IndividualSectorDataModel(String BALCONY, String BATHROOMS,
                                     String BED_ROOMS, String IMAGE_URL, String KITCHEN,
                                     String PROPERTY_TYPE, String RENT_PER_MONTH,
                                     String HOUSE_NUMBER, String LOCATION, String SECTOR_LOCATION,
                                     String FLAT_DESCRIPTION) {
        this.BALCONY = BALCONY;
        this.BATHROOMS = BATHROOMS;
        this.BED_ROOMS = BED_ROOMS;
        this.IMAGE_URL = IMAGE_URL;
        this.KITCHEN = KITCHEN;
        this.PROPERTY_TYPE = PROPERTY_TYPE;
        this.RENT_PER_MONTH = RENT_PER_MONTH;
        this.HOUSE_NUMBER = HOUSE_NUMBER;
        this.LOCATION = LOCATION;
        this.SECTOR_LOCATION = SECTOR_LOCATION;
        this.FLAT_DESCRIPTION = FLAT_DESCRIPTION;
    }
}
