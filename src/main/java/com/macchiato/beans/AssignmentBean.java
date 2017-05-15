package com.macchiato.beans;

import java.util.Date;

/**
 * Created by Xiangbin on 5/4/2017.
 */
public class AssignmentBean {
    private String course_code = "";
    private String assignmentName="";
    private Date duedate=new Date(2017,7,1);;
    private String assignmentKey = "";
    private String grade ="";

    public AssignmentBean(){}
    public AssignmentBean(String crsCode,String assignmentName,String assignmentKey){
        this.course_code=crsCode;
        this.assignmentKey=assignmentKey;
        this.assignmentName=assignmentName;
    }
    public AssignmentBean(String crsCode,String assignmentNam){
        this.course_code=crsCode;
        this.assignmentName=assignmentNam;
    }


    //getter and setter
    public String getCrsCode() {
        return course_code;
    }

    public void setCrsCode(String crsCode) {
        this.course_code = crsCode;
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

    public String getGrade(){return grade;}

    public void setGrade(String grade){this.grade = grade;}



    // Generates a String in JSON format
    public String generateJSON(){
        return "{\"assignmentName\":\"" + assignmentName + "\","
                + "\"course_code\":\"" + course_code + "\","
                + "\"assignmentKey\":\"" + assignmentKey + "\","
                + "\"grade\":\"" + grade + "\","
                + "\"duedate\":\"" + duedate.toString() + "\"}";
    }

}
