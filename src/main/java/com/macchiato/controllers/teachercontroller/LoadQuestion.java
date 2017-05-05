package com.macchiato.controllers.teachercontroller;

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
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.macchiato.utility.TeachersUtils.*;

/**
 * Created by Xiangbin on 5/5/2017.
 */
@Controller
public class LoadQuestion {
    //Load course function will load all the course owned by this teacher, and it will listed on teacher home page
    @RequestMapping(value = "/LoadQuestion.htm", method = RequestMethod.GET)
    public void LoadQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String assignmentKey=request.getParameter("assignmentKey");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        User active_user = GenUtils.getActiveUser();
        String instructor_email = active_user.getEmail();
        if (instructor_email== null) {
            System.out.print("There is no active_user");
        }
        else{
            System.out.println("instructor email: "+instructor_email);
            ArrayList<QuestionBean> newList= findAllQuestionBean(assignmentKey);
            //test data
            QuestionBean a=  new QuestionBean();
            a.setId("123");
            a.setSolution("hahah");
            a.setProblem("this is a test");
            QuestionBean b=  new QuestionBean();
            b.setId("123");
            b.setSolution("hahah");
            b.setProblem("this is a test");
            newList.add(a);
            newList.add(b);

            System.out.println(QuestionListJson(newList));
            out.println(QuestionListJson(newList));
        }
    }
}
