package com.macchiato.controllers.teachercontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.macchiato.beans.CourseBean;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Xiangbin on 5/3/2017.
 */
@Controller
public class EditCourseINFO {
    //this function will help teacher to change the information about this course
    @RequestMapping(value="editCourse.htm", method = RequestMethod.POST)
    public void editCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User active_user = GenUtils.getActiveUser();
        String instructor_email =active_user.getEmail();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        if (instructor_email == null ) {
            System.out.println("active_user is null");
        }
        else{
            String ClassCode=request.getParameter("crsCode");
            String ClassDis=request.getParameter("description");
            Query.Filter CrsCode_filter = new Query.FilterPredicate("crsCode", Query.FilterOperator.EQUAL,ClassCode );
            Query q = new Query("Course").setFilter(CrsCode_filter);
            PreparedQuery pq = datastore.prepare(q);
            int numberOfClass = pq.asList(FetchOptions.Builder.withDefaults()).size();
            if(numberOfClass!=1){

                System.out.println("Course code error");
            }
            else{
                for (Entity result : pq.asIterable()) {
                        result.setProperty("description", ClassDis);
                        System.out.print(result.getProperty("crsName")+":"+result.getProperty("description"));
                }
            }
        }
    }







}
