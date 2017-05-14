package com.macchiato.controllers.studentcontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.macchiato.beans.CourseBean;
import com.macchiato.beans.CourseListBean;
import com.macchiato.beans.StudentBean;
import com.macchiato.beans.UserBean;
import com.macchiato.utility.DiscussionBoardUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by li on 4/17/2017.
 */
@Controller
public class StudentController {
    @RequestMapping(value = "LoadStudent.htm", method = RequestMethod.GET)
    public void loadStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //storeDummyData();
        //System.out.println("Dummy");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        UserBean currUser;
        CourseListBean currList;
        StudentBean currStudent;
        // LOAD USER
        // Get the current user email and create userType
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String email = user.getEmail();
        long userType = 0;
        // Check if new login or the same user
        String checkEmail = (String)request.getSession().getAttribute("email");
        if (email.equals(checkEmail)){
            System.out.println("Loading User from session.");
            // If email has been set, no need to get anything just return user JSON
            currUser = new UserBean(email, userType);
        }else{
            // Else check in database if user is in there.
            // Get the datastore.
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            // Create the search keyword.
            Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email.trim());
            // Create the query
            Query q = new Query("User").setFilter(email_filter);
            PreparedQuery pq = datastore.prepare(q);
            // Find the UserBean Entity
            Entity result = pq.asSingleEntity();
            // If no student was found on the database create a new one.
            if(result == null){
                System.out.println("No User, creating and saving one now.");
                // Create an Entity to store
                Entity userEntity = new Entity("User",email);
                userEntity.setProperty("email", email);
                userEntity.setProperty("access", userType);
                datastore.put(userEntity);
            }else{
                System.out.println("Found User, loading User in now.");
                // Extract the information from result
                email = (String)result.getProperty("email");
                userType = (Long)result.getProperty("access");
                System.out.println(email);
                System.out.println(userType);
            }
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("access", userType);
            currUser = new UserBean(email, userType);

            System.out.println(currUser.generateJSON());
        }

        // LOAD COURSE LIST
        ArrayList<String> crsCodes = new ArrayList<String>();
        ArrayList<CourseBean> crsList = (ArrayList<CourseBean>) request.getSession().getAttribute("crsList");
        // Check the course list
        if(crsList == null){
            System.out.println("No course list in session");
            // If no course, load it up
            crsList = new ArrayList<CourseBean>();
            // Find all the crsCodes the user is enrolled in.
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email.trim());
            Query q = new Query("CourseEnrollment").setFilter(email_filter);
            PreparedQuery pq = datastore.prepare(q);
            for (Entity enrollmentEntity : pq.asIterable()) {
                crsCodes.add((String)enrollmentEntity.getProperty("course_code"));
            }
            if (crsCodes.size() > 0){
                System.out.println("Loading courses");
                // Find all the courses the user is enrolled in
                Query.Filter crsCode_filter;
                Entity course;
                CourseBean crsBean;
                for (int i = 0; i<crsCodes.size(); i++){
                    crsCode_filter = new Query.FilterPredicate("course_code", Query.FilterOperator.EQUAL, crsCodes.get(i).trim());
                    q = new Query("Course").setFilter(crsCode_filter);
                    pq = datastore.prepare(q);
                    course = pq.asSingleEntity();
                    crsBean = new CourseBean((String)course.getProperty("course_code"),
                            (String)course.getProperty("name"),
                            (String)course.getProperty("email"),
                            (String)course.getProperty("description"));
                    crsList.add(crsBean);
                }
                request.getSession().setAttribute("crsList", crsList);
            }else{
                System.out.println("User not enrolled in any courses");
            }
        }else{
            System.out.println("Loading Courses from session");
        }


        CourseBean currCourse = null;
        boolean enrolling = false;
        /* 0 for not enrolling
           1 for enroll success
           2 for already enrolled
           3 for course does not exist*/
        int enrollStatus = 0;
        // LOAD THE CURR COURSE
        if(request.getParameter("crsName") != null){
            System.out.println("We're in");
            System.out.println(request.getParameter("crsName"));
            String changeCourse = request.getParameter("crsName");
            for (int i = 0; i <crsList.size(); i++){
                if(crsList.get(i).getCrsCode().compareTo(changeCourse) == 0){
                    currCourse = crsList.get(i);
                    request.getSession().setAttribute("currCourse",currCourse);
                    System.out.println("Swapping course");
                }
            }
        }// Check we're enrolling
        else if(request.getParameter("enroll") != null){
            enrolling = true;
            boolean newCourse = true;
            System.out.println(request.getParameter("enroll"));
            String enrollCode = request.getParameter("enroll");
            for(int i = 0; i<crsList.size(); i++){
                if(enrollCode.compareTo(crsList.get(i).getCrsCode()) == 0){
                    newCourse = false;
                    enrollStatus = 2;
                    System.out.println("Already enrolled in course");
                }
            }
            if(newCourse){
                // Look for course in database
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                // Create the search keyword.
                Query.Filter enroll_filter = new Query.FilterPredicate("course_code", Query.FilterOperator.EQUAL, enrollCode.trim());
                // Create the query
                Query q = new Query("Course").setFilter(enroll_filter);
                PreparedQuery pq = datastore.prepare(q);
                Entity result = pq.asSingleEntity();
                // If course does not exist, return a 3
                if(result == null){
                    enrollStatus = 3;
                    System.out.println("Course does not exist");
                }else{
                    CourseBean enrollCourse = new CourseBean((String)result.getProperty("course_code"),
                            (String)result.getProperty("name"),
                            (String)result.getProperty("email"),
                            (String)result.getProperty("description"));
                    crsList.add(enrollCourse);
                    request.getSession().setAttribute("crsList", crsList);
                    currCourse = enrollCourse;
                    request.getSession().setAttribute("currCourse",currCourse);
                    long u_set = 0;
                    Entity enrollmentEntity = new Entity("CourseEnrollment");
                    enrollmentEntity.setProperty("course_code", enrollCode);
                    enrollmentEntity.setProperty("email", email);
                    //enrollmentEntity.setProperty("username", email);
                    //enrollmentEntity.setProperty("access", userType);
                    //enrollmentEntity.setProperty("u_set", u_set);
                    //enrollmentEntity.setProperty("course_id", (Long)result.getProperty("id") );
                    //enrollmentEntity.setProperty("forum_key", DiscussionBoardUtils.getForumKey(enrollCourse.getInstrEmail(), enrollCourse.getCrsCode(),enrollCourse.getSection()));
                    datastore.put(enrollmentEntity);
                    System.out.println("Enrolled into new course");
                }
            }

        }else{
            currCourse = (CourseBean)request.getSession().getAttribute("currCourse");
        }
        // If no course in session,
        if(currCourse == null){
            // get the first course
            if(crsList.size() > 0){
                currCourse = crsList.get(0);
                request.getSession().setAttribute("currCourse",currCourse);
            }else{
                // create an empty one
                currCourse = new CourseBean();
            }
        }


        // Sort the crsList alphabetically
        Collections.sort(crsList, new Comparator<CourseBean>() {
            @Override
            public int compare(CourseBean course2, CourseBean course1)
            {

                return  course2.getCrsName().compareTo(course1.getCrsName());
            }
        });
        currList = new CourseListBean(crsList);
        currStudent = new StudentBean(currUser,currList,currCourse);
        String JSONoutput = "{";
        JSONoutput += currStudent.generateJSON();
        JSONoutput += ",\"Enroll\": {\"status\": \"";
        JSONoutput += Integer.toString(enrollStatus);
        JSONoutput += "\"}}";
        System.out.println(JSONoutput);
        out.println(JSONoutput);
    }





    @RequestMapping(value = "LoadStudentAssignments.htm", method = RequestMethod.GET)
    public void loadStudentAssignments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Email should be set in session
        // crsList of courses should be set in session
        // currCourse should be set in session
        // Find the list of assignments
        // Sort them alphabetically
        // Return them
    }

    public void storeDummyData(){
        // Store 3 dummy courses
        long id = 1;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity cse114 = new Entity("Course");
        cse114.setProperty("name", "CSE114");
        cse114.setProperty("course_code", "1111");
        cse114.setProperty("description", "This is CSE114");
        cse114.setProperty("section", "01");
        cse114.setProperty("email", "teacher1@gmail.com");
        cse114.setProperty("id", id);
        datastore.put(cse114);
        id = 2;
        Entity cse307 = new Entity("Course");
        cse307.setProperty("name", "CSE307");
        cse307.setProperty("course_code", "2222");
        cse307.setProperty("description", "This is CSE307");
        cse307.setProperty("section", "01");
        cse307.setProperty("email", "teacher1@gmail.com");
        cse307.setProperty("id", id);
        datastore.put(cse307);
        id = 3;
        Entity cse308 = new Entity("Course");
        cse308.setProperty("name", "CSE308");
        cse308.setProperty("course_code", "3333");
        cse308.setProperty("description", "This is CSE308");
        cse308.setProperty("section", "01");
        cse308.setProperty("email", "teacher1@gmail.com");
        cse308.setProperty("id", id);
        datastore.put(cse308);
        /*// Store 2 dummy enrollment
        Entity enrollmentEntity = new Entity("CourseEnrollment");
        enrollmentEntity.setProperty("course_code", "1111");
        enrollmentEntity.setProperty("email", "test@example.com");
        datastore.put(enrollmentEntity);
        //System.out.println("Enroll in 114");
        Entity enrollment307 = new Entity("CourseEnrollment");
        enrollment307.setProperty("course_code", "2222");
        enrollment307.setProperty("email", "test@example.com");
        datastore.put(enrollment307);*/
        //System.out.println("Enroll in 307");

        // Store 6 dummy assignments
    }
}
