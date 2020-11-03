package com.realstate.bdland.Database.Data_model;

public class ClientDataRequestModel {

    String CLIENT_NAME;
    String CLIENT_PHONE_NUMBER;
    String CLIENT_REQUIREMENT;
    String CLIENT_REQUEST_RENT;
    String CLIENT_OCCUPATION;
    String CLIENT_FAMILY_MEMBER;
    String CLIENT_REQUESTED_SECTOR;
    String REQUEST_STATUS;
    String CLIENT_ID;

    public String getCLIENT_NAME() {
        return CLIENT_NAME;
    }

    public void setCLIENT_NAME(String CLIENT_NAME) {
        this.CLIENT_NAME = CLIENT_NAME;
    }

    public String getCLIENT_PHONE_NUMBER() {
        return CLIENT_PHONE_NUMBER;
    }

    public void setCLIENT_PHONE_NUMBER(String CLIENT_PHONE_NUMBER) {
        this.CLIENT_PHONE_NUMBER = CLIENT_PHONE_NUMBER;
    }

    public String getCLIENT_REQUIREMENT() {
        return CLIENT_REQUIREMENT;
    }

    public void setCLIENT_REQUIREMENT(String CLIENT_REQUIREMENT) {
        this.CLIENT_REQUIREMENT = CLIENT_REQUIREMENT;
    }

    public String getCLIENT_REQUEST_RENT() {
        return CLIENT_REQUEST_RENT;
    }

    public void setCLIENT_REQUEST_RENT(String CLIENT_REQUEST_RENT) {
        this.CLIENT_REQUEST_RENT = CLIENT_REQUEST_RENT;
    }

    public String getCLIENT_OCCUPATION() {
        return CLIENT_OCCUPATION;
    }

    public void setCLIENT_OCCUPATION(String CLIENT_OCCUPATION) {
        this.CLIENT_OCCUPATION = CLIENT_OCCUPATION;
    }

    public String getCLIENT_FAMILY_MEMBER() {
        return CLIENT_FAMILY_MEMBER;
    }

    public void setCLIENT_FAMILY_MEMBER(String CLIENT_FAMILY_MEMBER) {
        this.CLIENT_FAMILY_MEMBER = CLIENT_FAMILY_MEMBER;
    }

    public String getCLIENT_REQUESTED_SECTOR() {
        return CLIENT_REQUESTED_SECTOR;
    }

    public void setCLIENT_REQUESTED_SECTOR(String CLIENT_REQUESTED_SECTOR) {
        this.CLIENT_REQUESTED_SECTOR = CLIENT_REQUESTED_SECTOR;
    }

    public String getREQUEST_STATUS() {
        return REQUEST_STATUS;
    }

    public void setREQUEST_STATUS(String REQUEST_STATUS) {
        this.REQUEST_STATUS = REQUEST_STATUS;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public ClientDataRequestModel() {
    }

    public ClientDataRequestModel(String CLIENT_NAME,
                                  String CLIENT_PHONE_NUMBER,
                                  String CLIENT_REQUIREMENT, String CLIENT_REQUEST_RENT,
                                  String CLIENT_OCCUPATION, String CLIENT_FAMILY_MEMBER, String CLIENT_REQUESTED_SECTOR,
                                  String REQUEST_STATUS, String CLIENT_ID) {
        this.CLIENT_NAME = CLIENT_NAME;
        this.CLIENT_PHONE_NUMBER = CLIENT_PHONE_NUMBER;
        this.CLIENT_REQUIREMENT = CLIENT_REQUIREMENT;
        this.CLIENT_REQUEST_RENT = CLIENT_REQUEST_RENT;
        this.CLIENT_OCCUPATION = CLIENT_OCCUPATION;
        this.CLIENT_FAMILY_MEMBER = CLIENT_FAMILY_MEMBER;
        this.CLIENT_REQUESTED_SECTOR = CLIENT_REQUESTED_SECTOR;
        this.REQUEST_STATUS = REQUEST_STATUS;
        this.CLIENT_ID = CLIENT_ID;
    }
}
