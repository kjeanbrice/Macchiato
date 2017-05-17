package com.macchiato.beans;

import java.util.Date;

/**
 * Created by Xiangbin on 5/4/2017.
 */
public class AssignmentBean implements Comparable<AssignmentBean>  {
    private String course_code = "";
    private String assignmentName="";
    private Date duedate=new Date();
    private String assignmentKey = "";
    private String grade ="";
    private String end="";

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

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    // Generates a String in JSON format
    public String generateJSON(){
        String stringdata=(duedate.toString()).substring(0,11) +changeYear(duedate.toString().substring(24,28));
        String endString;
        System.out.print(end);
        if(end.equals("1")){
            endString="Ended";
        }else{
            endString="Available";
        }
         return"{\"assignmentName\":\"" + assignmentName + "\","
                + "\"course_code\":\"" + course_code + "\","
                + "\"assignmentKey\":\"" + assignmentKey + "\","
                + "\"grade\":\"" + grade + "\","
                + "\"endString\":\"" + endString + "\","
                + "\"duedate\":\"" + stringdata+"\"}";
    }

    @Override
    public int compareTo(AssignmentBean o) {
        if (getDuedata() == null || o.getDuedata() == null)
            return 0;
        return getDuedata().compareTo(o.getDuedata());
    }

    //this function will help
    public String changeYear(String year){
        int a= Integer.parseInt(year);
        return Integer.toString(a);
    }

    // Generates a String in JSON format
    public String generateEndJSON(){
        String output = "{\"assignmentName\":\"" + assignmentName + "\","
                + "\"course_code\":\"" + course_code + "\","
                + "\"assignmentKey\":\"" + assignmentKey + "\","
                + "\"grade\":\"" + grade + "\","
                + "\"duedate\":\"" + end+"\"}";
        return output;
    }
}
