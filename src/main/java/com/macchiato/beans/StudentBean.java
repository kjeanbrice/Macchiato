package com.macchiato.beans;

import java.io.Serializable;

/**
 * Created by li on 4/22/2017.
 */
public class StudentBean implements Serializable{
    private UserBean user;
    private CourseListBean crsList;
    private CourseBean currCourse;

    public StudentBean(UserBean user, CourseListBean crsList, CourseBean currCourse){
        this.user = user;
        this.crsList = crsList;
        this.currCourse = currCourse;
    }

    public UserBean getUser() {return user;}
    public CourseListBean getCrsList() {return crsList;}
    public CourseBean getCurrCourse(){return currCourse;}

    public void setUser(UserBean user) {this.user = user;}
    public void setCrsList(CourseListBean crsList) {this.crsList = crsList;}
    public void setCurrCourse(CourseBean currCourse){this.currCourse = currCourse;}

    public String generateJSON(){
        String output = "\"Student\":[";
        output += user.generateJSON() + ",";
        output += crsList.generateJSON() + ",";
        if (currCourse == null){
            output += "[]";
        }else{
            output += currCourse.generateJSON();
        }
        output += "]";
        return output;
    }
}
