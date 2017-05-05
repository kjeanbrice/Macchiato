package com.macchiato.controllers.teachercontroller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.macchiato.beans.AssignmentBean;
import com.macchiato.beans.QuestionBean;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Xiangbin on 5/5/2017.
 */
@Controller
public class AddQuestion {
    //this function add the new course to the database
    @RequestMapping(value="addQuestion.htm", method = RequestMethod.POST)
    public void addQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User active_user = GenUtils.getActiveUser();
        String instructor_email =active_user.getEmail();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        if (instructor_email == null ) {
            System.out.println("active_user is null");
        }
        else{
            String assignmentKey=request.getParameter("assignmentKey");
            String problem=request.getParameter("problem");
            String solution=request.getParameter("solution");
            QuestionBean newQuestion=new QuestionBean();
            newQuestion.setProblem(problem);
            newQuestion.setSolution(solution);
            Entity user = new Entity("Question");

            user.setProperty("problem",problem);
            user.setProperty("solution", solution);
            user.setProperty("assignmentKey",assignmentKey);

            datastore.put(user);
            newQuestion.setId(user.getKey().toString());
            System.out.println("this is new key for this question: "+user.getKey().toString());
            System.out.print("From add: "+newQuestion.generateJSON());
            //out.println(newClass.generateJSON());
        }
    }


}
