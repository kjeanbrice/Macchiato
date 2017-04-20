package com.macchiato.controllers.studentcontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
//import com.macchiato.beans.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by li on 4/17/2017.
 */
@Controller
public class LoadStudent {
    @RequestMapping(value = "LoadStudent.htm", method = RequestMethod.GET)
    public void loadStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int userType = 1;
        String email = (String)request.getSession().getAttribute("Email");
        ArrayList<String> crsCodes = new ArrayList<String>();
        ArrayList<String> crsNames = new ArrayList<String>();
        crsNames.add("CSE114");
        crsNames.add("CSE214");
        crsNames.add("CSE114");

        // Check if the user has already been loaded
        if(email != null){
            System.out.println("User already in Session");
        }else{
            System.out.println("Loading User");
            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            email = user.getEmail();
            userType = 1;
            System.out.println(email);
            // If the user is already in the database load it from the datastore, else save it to the datastore.
            // Get the datastore.
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            // Create the search keyword.
            Query.Filter email_filter = new Query.FilterPredicate("Email", Query.FilterOperator.EQUAL, email.trim());
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
                userEntity.setProperty("Email", email);
                userEntity.setProperty("UserType", Integer.toString(userType));
                datastore.put(userEntity);
            }else{
                System.out.println("Found User, loading User in now.");
                // Extract the information from result
                email = (String)result.getProperty("Email");
                userType = Integer.parseInt((String)result.getProperty("UserType"));
                System.out.println(email);
                System.out.println(userType);
                // Find the courses the student is enrolled
                // Create the query
                q = new Query("Enrollment").setFilter(email_filter);
                pq = datastore.prepare(q);

                // Find all the courses the user is enrolled in.
                for (Entity enrollmentEntity : pq.asIterable()) {
                    crsCodes.add((String)enrollmentEntity.getProperty("CrsCode"));
                }

                // Construct the list of course names
                Query.Filter crsCode_filter;
                Entity courseEntity;
                for(int i = 0; i < crsCodes.size(); i++){
                    crsCode_filter = new Query.FilterPredicate("CrsCode", Query.FilterOperator.EQUAL, crsCodes.get(i).trim());
                    q = new Query("Enrollment").setFilter(crsCode_filter);
                    pq = datastore.prepare(q);
                    courseEntity = pq.asSingleEntity();
                    crsNames.set(i,(String)courseEntity.getProperty("Name"));
                }
            }
            // Set current session attribute
            request.getSession().setAttribute("Email", email);
            request.getSession().setAttribute("CrsCodes", crsCodes);
            request.getSession().setAttribute("CrsNames", crsNames);
        }

      //  UserBean newUser = new UserBean(email,userType);
        String outputJSON = "{\"email\":\"" + email + "\"," + "\"CrsNames\":[";


        for(int i = 0; i < crsNames.size() - 1; i++){
            outputJSON += "\"" + crsNames.get(i) + "\",";
        }
        if (crsNames.size() >0){
            outputJSON += "\"" + crsNames.get(crsNames.size()-1) + "\"";
        }

        outputJSON += "]}";

        System.out.println(outputJSON);
        out.println(outputJSON);
    }
}
