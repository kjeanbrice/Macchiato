package com.macchiato.controllers.teachercontroller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.macchiato.beans.AssignmentBean;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Xiangbin on 4/27/2017.
 */
@Controller
public class AddAssignment {
    //this function add the new course to the database
    @RequestMapping(value="addAssignment.htm", method = RequestMethod.POST)
    public void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User active_user = GenUtils.getActiveUser();
        String instructor_email =active_user.getEmail();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        if (instructor_email == null ) {
            System.out.println("active_user is null");
        }
        else{
            String course_code=request.getParameter("course_code");
            String assignmentName=request.getParameter("assignmentName");
            AssignmentBean newAissignment=new  AssignmentBean(course_code,assignmentName);
            Entity user = new Entity("Assignment");

            System.out.println("this is new key for this assignment: "+user.getKey().toString());
            user.setProperty("course_code",course_code);
            user.setProperty("assignmentName", assignmentName);
            System.out.println();
            datastore.put(user);
            System.out.println(user.getKey().toString());
            System.out.print("From add: "+newAissignment.generateJSON());
            //out.println(newClass.generateJSON());
        }
    }
}
