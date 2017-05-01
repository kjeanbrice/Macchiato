package com.macchiato.controllers.teachercontroller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.macchiato.beans.CourseBean;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Xiangbin on 4/19/2017.
 * this class can add the Course to the database
 */
@Controller
public class Addcourse {

//this function add the new course to the database
@RequestMapping(value="addCourse.htm", method = RequestMethod.POST)
    public void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
    User active_user = GenUtils.getActiveUser();
    String instructor_email =active_user.getEmail();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    if (instructor_email == null ) {
        System.out.println("active_user is null");
    }
    else{
        String ClassName=request.getParameter("crsName");
        String ClassDis=request.getParameter("description");
        System.out.println("____________"+ClassDis+"_______________");
        String email=instructor_email;
        CourseBean newClass=new  CourseBean(ClassName,email,ClassDis);
        Entity user = new Entity("Course");
        user.setProperty("crsCode",newClass.getCrsCode());
        user.setProperty("crsName", ClassName);
        user.setProperty("description", ClassDis);
        user.setProperty("email",email);
        datastore.put(user);
        System.out.print(newClass.generateJSON());
    }

}


}


