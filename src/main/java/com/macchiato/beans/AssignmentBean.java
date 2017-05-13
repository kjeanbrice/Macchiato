package com.macchiato.beans;

import java.util.Date;

/**
 * Created by Xiangbin on 5/4/2017.
 */
public class AssignmentBean {
    private String crsCode;
    private String assignmentName="";
    private Date duedate=new Date(2017,7,1);;
    private String assignmentKey;

    public AssignmentBean(){}
    public AssignmentBean(String crsCode,String assignmentName,String assignmentKey){
        this.crsCode=crsCode;
        this.assignmentKey=assignmentKey;
        this.assignmentName=assignmentName;
    }
    public AssignmentBean(String crsCode,String assignmentNam){
        this.crsCode=crsCode;
        this.assignmentName=assignmentNam;
    }


    //getter and setter
    public String getCrsCode() {
        return crsCode;
    }

    public void setCrsCode(String crsCode) {
        this.crsCode = crsCode;
    }

    public String getAissignmentName() {
        return assignmentName;
    }

    public void setAissignmentName(String aissignmentName) {
        this.assignmentName = aissignmentName;
    }

    public String getAissignmentKey() {
        return assignmentKey;
    }

    public void setAissignmentKey(String aissignmentKey) {
        this.assignmentKey = aissignmentKey;
    }

    public Date getDuedata() {
        return duedate;
    }

    public void setDuedata(Date duedata) {
        this.duedate = duedata;
    }



    // Generates a String in JSON format
    public String generateJSON(){
        return "{\"assignmentName\":\"" + assignmentName + "\","
                + "\"crsCode\":\"" + crsCode + "\","
                + "\"assignmentKey\":\"" + assignmentKey + "\","
                + "\"duedate\":\"" + duedate.toString() + "\"}";
    }

}
