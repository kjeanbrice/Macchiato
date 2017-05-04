package com.macchiato.general.discussiondata;

import com.macchiato.utility.GenUtils;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.Serializable;

/**
 * Created by Karl on 3/31/2017.
 */

public class EnrollmentData implements Serializable {


    private long enrollmentID;
    private String email;
    private String username;
    private String course;
    private long access;



    public EnrollmentData() {

    }

    public EnrollmentData(long enrollmentID, String email, String username, String course, long access) {
        this.enrollmentID = enrollmentID;
        this.username = username;
        this.email = email;
        this.course = course;
        this.access = access;
    }

    public long getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(long enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAccess() {
        if(access == GenUtils.STUDENT){
            return "STUDENT";
        }

        if(access == GenUtils.INSTRUCTOR){
            return "INSTRUCTOR";
        }

        if(access == GenUtils.ADMIN){
            return "ADMIN";
        }
        return "";
    }

    public void setAccess(long access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "EnrollmentData{" +
                "enrollmentID=" + enrollmentID +
                ", email='" + StringEscapeUtils.escapeJava(email) + '\'' +
                ", username='" + StringEscapeUtils.escapeJava(username) + '\'' +
                ", course='" + StringEscapeUtils.escapeJava(course) + '\'' +
                ", access=" + access +
                '}';
    }


    public String generateJSON(){
        String outputString = "{\"enrollmentID\":\"" + enrollmentID
                +"\", \"email\":\"" + StringEscapeUtils.escapeJava(email)
                + "\"," + "\"username\":\"" + StringEscapeUtils.escapeJava(username)
                + "\"," + "\"course\":\"" + StringEscapeUtils.escapeJava(course)
                +"\",\"access\":\"" + access +"\"}";
        return outputString;
    }
}
