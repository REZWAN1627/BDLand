package com.realstate.bdland.Database.Data_model;

import com.google.firebase.firestore.GeoPoint;

public class IndividualUserLocationDataModel {

    private String PROPERTY_TYPE;
    private String RENT_PER_MONTH;
    private String HOUSE_NUMBER;
    private String HOUSE_OWNER_PHONE_NUMBER;
    private String HOUSE_OWNER_NAME;
    private GeoPoint EXACT_LOCATION;
    private String BED_ROOMS;
    private String BATHROOMS;

    @Override
    public String toString() {
        return "IndividualUserLocationDataModel{" +
                "PROPERTY_TYPE='" + PROPERTY_TYPE + '\'' +
                ", RENT_PER_MONTH='" + RENT_PER_MONTH + '\'' +
                ", HOUSE_NUMBER='" + HOUSE_NUMBER + '\'' +
                ", HOUSE_OWNER_PHONE_NUMBER='" + HOUSE_OWNER_PHONE_NUMBER + '\'' +
                ", HOUSE_OWNER_NAME='" + HOUSE_OWNER_NAME + '\'' +
                ", EXACT_LOCATION=" + EXACT_LOCATION +
                ", BED_ROOMS='" + BED_ROOMS + '\'' +
                ", BATHROOMS='" + BATHROOMS + '\'' +
                '}';
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

    public String getHOUSE_OWNER_PHONE_NUMBER() {
        return HOUSE_OWNER_PHONE_NUMBER;
    }

    public void setHOUSE_OWNER_PHONE_NUMBER(String HOUSE_OWNER_PHONE_NUMBER) {
        this.HOUSE_OWNER_PHONE_NUMBER = HOUSE_OWNER_PHONE_NUMBER;
    }

    public String getHOUSE_OWNER_NAME() {
        return HOUSE_OWNER_NAME;
    }

    public void setHOUSE_OWNER_NAME(String HOUSE_OWNER_NAME) {
        this.HOUSE_OWNER_NAME = HOUSE_OWNER_NAME;
    }

    public GeoPoint getEXACT_LOCATION() {
        return EXACT_LOCATION;
    }

    public void setEXACT_LOCATION(GeoPoint EXACT_LOCATION) {
        this.EXACT_LOCATION = EXACT_LOCATION;
    }

    public String getBED_ROOMS() {
        return BED_ROOMS;
    }

    public void setBED_ROOMS(String BED_ROOMS) {
        this.BED_ROOMS = BED_ROOMS;
    }

    public String getBATHROOMS() {
        return BATHROOMS;
    }

    public void setBATHROOMS(String BATHROOMS) {
        this.BATHROOMS = BATHROOMS;
    }

    public IndividualUserLocationDataModel() {
    }

    public IndividualUserLocationDataModel(String PROPERTY_TYPE,
                                           String RENT_PER_MONTH, String HOUSE_NUMBER, String HOUSE_OWNER_PHONE_NUMBER,
                                           String HOUSE_OWNER_NAME, GeoPoint EXACT_LOCATION, String BED_ROOMS,
                                           String BATHROOMS) {
        this.PROPERTY_TYPE = PROPERTY_TYPE;
        this.RENT_PER_MONTH = RENT_PER_MONTH;
        this.HOUSE_NUMBER = HOUSE_NUMBER;
        this.HOUSE_OWNER_PHONE_NUMBER = HOUSE_OWNER_PHONE_NUMBER;
        this.HOUSE_OWNER_NAME = HOUSE_OWNER_NAME;
        this.EXACT_LOCATION = EXACT_LOCATION;
        this.BED_ROOMS = BED_ROOMS;
        this.BATHROOMS = BATHROOMS;
    }
}
