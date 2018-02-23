package com.example.tansen.goparty1;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by tansen on 8/15/17.
 */

public class Party implements Serializable{
    String partyName, partyHost, partySize, startTime, endTime, address, zipcode, theme, description, id;

    public Party() {
    }

    public Party(String partyName, String partyHost, String partySize, String startTime, String endTime, String address, String zipcode, String theme, String description, String id) {
        this.partyName = partyName;
        this.partyHost = partyHost;
        this.partySize = partySize;
        this.startTime = startTime;
        this.endTime = endTime;
        this.theme = theme;
        this.id = id;
        this.description = description;
        this.address = address;
        this.zipcode = zipcode;
    }

    public String getPartyId() {
        return id;
    }
    public void setPartyId(String id) {
        this.id = id;
    }

    public String getPartyName() {
        return partyName;
    }
    public void setPartyName(String name) {
        this.partyName = name;
    }

    public String getPartyHost() {
        return partyHost;
    }
    public void setPartyHost(String host) {
        this.partyHost = host;
    }

    public String getPartySize() {
        return partySize;
    }
    public void setPartySize(String size) {
        this.partySize = size;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
