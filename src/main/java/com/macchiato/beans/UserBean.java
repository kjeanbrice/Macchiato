package com.macchiato.beans;

import java.io.Serializable;

/**
 * Created by li on 4/14/2017.
 */
public class UserBean implements Serializable {
    private String email;
    // 0 is Student, 1 is Instructor, 2 is Administrator
    private int userType;

    public UserBean(String email, int userType){
        this.email = email;
        this.userType = userType;
    }

    public String getEmail() {return email;}

    public int getUserType() {return userType;}

    public void setEmail(String email) {this.email=email;}

    public void setUserType(int userType) {this.userType=userType;}

    public String generateJSON() {

        String outputString = "{\"email\":\"" + this.email + "\","
                + "\"userType\":\"" + Integer.toString(this.userType) + "\"";
        outputString += "}";

        return outputString;
    }
}
