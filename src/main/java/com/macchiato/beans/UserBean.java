package com.macchiato.beans;

import java.io.Serializable;

/**
 * Created by li on 4/14/2017.
 */
public class UserBean implements Serializable {
    public static final String STUDENT_HOME = "Student.htm";
    public static final String INSTRUCTOR_HOME = "TeacherHomePage.htm";

    private String email;
    // 0 is Student, 1 is Instructor, 2 is Administrator
    private int userType;
    private String home;

    public UserBean(String email, int userType, String home){
        this.email = email;
        this.userType = userType;
        this.home = home;
    }

    public UserBean(String email, int userType){
        this.email = email;
        this.userType = userType;
        this.home = "NOT AVAILABLE";
    }

    public String getEmail() {return email;}

    public int getUserType() {return userType;}

    public void setEmail(String email) {this.email=email;}

    public void setUserType(int userType) {this.userType=userType;}


    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String generateJSON() {

        String outputString = "{\"email\":\"" + this.email + "\","
                + "\"home\":\"" + this.home + "\","
                + "\"userType\":\"" + Integer.toString(this.userType) + "\"";
        outputString += "}";

        return outputString;
    }

}
