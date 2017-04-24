package com.macchiato.beans;

import java.io.Serializable;

/**
 * Created by li on 4/22/2017.
 */
public class StudentBean implements Serializable{
    private UserBean user;
    private CourseListBean crsList;

    public StudentBean(UserBean user, CourseListBean crsList){
        this.user = user;
        this.crsList = crsList;
    }

    public UserBean getUser() {return user;}
    public CourseListBean getCrsList() {return crsList;}

    public void setUser(UserBean user) {this.user = user;}
    public void setCrsList(CourseListBean crsList) {this.crsList = crsList;}

    public String generateJSON(){
        String output = "{\"Student\":[";
        output += user.generateJSON() + ",";
        output += crsList.generateJSON();
        output += "]}";
        return output;
    }
}
