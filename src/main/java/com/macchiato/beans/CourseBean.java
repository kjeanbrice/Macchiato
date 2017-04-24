package com.macchiato.beans;

import java.io.Serializable;

/**
 * Created by li on 4/21/2017.
 */
public class CourseBean implements Serializable {
    private String crsName;
    private String crsCode;
    private String instrEmail;
    private String description;

    public CourseBean(String crsCode, String crsName, String instrEmail, String description){
        this.crsCode = crsCode;
        this.crsName = crsName;
        this.instrEmail = instrEmail;
        this.description = description;
    }
    // Get methods
    public String getCrsCode() {return crsCode;    }
    public String getCrsName() {return crsName;}
    public String getInstrEmail() {return instrEmail;}
    public String getDescription() {return description;}
    // Set methods
    public void setCrsCode(String crsCode) {this.crsCode = crsCode;}
    public void setCrsName(String crsName) {this.crsName = crsName;}
    public void setInstrEmail(String instrEmail) {this.instrEmail = instrEmail;}
    public void setDescription(String description) {this.description = description;}
    // Generates a String in JSON format
    public String generateJSON(){
        return "{\"crsCode\":\"" + crsCode + "\","
                + "\"crsName\":\"" + crsName + "\","
                + "\"instrEmail\":\"" + instrEmail + "\","
                + "\"description\":\"" + description + "\"}";
    }
}
