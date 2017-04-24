package com.macchiato.beans;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;


/**
 * Created by Xiangbin on 4/19/2017.
 */
public class ClassBean {
    private String ClassName;
    private String ClassCode;
    private String email;
    private String TeacherName;
    private String CourseDescription;
    private String OfficeHours;
    private String OwnerEmeail;
    private ArrayList studentList=new ArrayList();
    private  ArrayList AssignmentList=new ArrayList();

    public ClassBean(){
        ClassName="";
        ClassCode=nextSessionId();
        CourseDescription="";
    }

    public ClassBean(String ClassName, String CourseDescription){
        this.ClassName=ClassName;
        ClassCode=nextSessionId();
        this.CourseDescription=CourseDescription;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        CourseDescription = courseDescription;
    }

    public String getOfficeHours() {
        return OfficeHours;
    }

    public void setOfficeHours(String officeHours) {
        OfficeHours = officeHours;
    }

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public  String  toString(){
        String toString="ClassNname: "+ClassName+ "ClassCode: "+ClassCode;
        return toString;
    }
}
