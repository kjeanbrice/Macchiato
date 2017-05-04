package com.macchiato.general;

import java.io.Serializable;

/**
 * Created by Karl on 4/2/2017.
 */
public class CourseData implements Serializable {
    private String instructor_email;
    private String instructor_nickname;
    private String course;
    private String section;

    public CourseData(String instructor_email, String instructor_nickname, String course, String section) {
        this.instructor_email = instructor_email;
        this.instructor_nickname = instructor_nickname;
        this.course = course;
        this.section = section;
    }

    public CourseData() {
        this("default", "default", "default","default");
    }

    public String getInstructor_email() {
        return instructor_email;
    }

    public void setInstructor_email(String instructor_email) {
        this.instructor_email = instructor_email;
    }

    public String getInstructor_nickname() {
        return instructor_nickname;
    }

    public void setInstructor_nickname(String instructor_nickname) {
        this.instructor_nickname = instructor_nickname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "CourseData{" +
                "instructor_email='" + instructor_email + '\'' +
                ", instructor_nickname='" + instructor_nickname + '\'' +
                ", course='" + course + '\'' +
                '}';
    }

    public String generateJSON() {

        String outputString = "{\"instructorEmail\":\"" + this.instructor_email + "\","
                + "\"instructorNickname\":\"" + this.instructor_nickname + "\","
                + "\"section\":\"" + this.section + "\","
                + "\"course\":\"" + this.course + "\"";

        outputString += "}";

        return outputString;
    }
}

