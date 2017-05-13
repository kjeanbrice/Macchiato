package com.macchiato.controllers.teachercontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Xiangbin on 5/12/2017.
 */
@Controller
public class EditQuestion {
    @RequestMapping(value="/editQuestion.htm", method = RequestMethod.POST)
    //this methon will take input from edit box to help user edit problem and solution
    public void editCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String problem=request.getParameter("problem");
        String solution=request.getParameter("solution");
        String questionKey=request.getParameter("questionKey");
        System.out.println("Change question "+problem+"    "+solution+" from "+questionKey);
        User active_user = GenUtils.getActiveUser();
        String instructor_email =active_user.getEmail();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        if (instructor_email == null ) {
            System.out.println("active_user is null");
        }
        else{
            Query.Filter CrsCode_filter = new Query.FilterPredicate("questionKey", Query.FilterOperator.EQUAL,questionKey );
            Query q = new Query("Question").setFilter(CrsCode_filter);
            PreparedQuery pq = datastore.prepare(q);
            int numberOfClass = pq.asList(FetchOptions.Builder.withDefaults()).size();
            if(numberOfClass!=1){
                System.out.println("questionkey  error");
            }
            else{
                for (Entity result : pq.asIterable()) {
                    result.setProperty("problem", problem);
                    result.setProperty("solution", solution);
                    System.out.print(result.getProperty("problem")+":"+result.getProperty("solution"));
                    datastore.put(result);
                }
            }
        }
    }
}
