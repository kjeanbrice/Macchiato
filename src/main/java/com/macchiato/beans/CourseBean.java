package com.macchiato.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by li on 4/21/2017.
 * Edited by Xiangbin Zeng
 * This class is for the bean of course which help us the build each course,
 * it include all the information we needed from a course
 */
public class CourseBean implements Serializable {
    //name of the class
    private String crsName;
    //random class code for students to enroll
    private String crsCode;
    //instr's Email
    private String instrEmail;
    //description of this class
    private String description;
    //valuable to help random generator
    private SecureRandom random = new SecureRandom();

    public CourseBean(String crsCode, String crsName, String instrEmail, String description){
        this.crsCode = crsCode;
        this.crsName = crsName;
        this.instrEmail = instrEmail;
        this.description = description;
    }

    public CourseBean(){}

    public CourseBean( String crsName, String instrEmail, String description){
        this.crsCode =  nextSessionId();
        this.crsName = crsName;
        this.instrEmail = instrEmail;
        this.description = description;
    }
    // Get methods
    public String getCrsCode() {return crsCode;}
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

    //random generator for crsCode
    public String nextSessionId() {
        return new BigInteger(25, random).toString(32);
    }
}
