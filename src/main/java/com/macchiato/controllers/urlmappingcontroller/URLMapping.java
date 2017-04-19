package com.macchiato.controllers.urlmappingcontroller;

import com.google.appengine.api.datastore.*;
import com.macchiato.beans.QuestionBean;
import com.macchiato.beans.QuestionListBean;
import com.macchiato.beans.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.hackerrank.api.client.*;
import com.hackerrank.api.hackerrank.api.*;
import com.hackerrank.api.hackerrank.model.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;




/**
 * Created by Karl on 4/5/2017.
 * Edited by Raymond Xue
 */
@Controller
public class URLMapping {

    @RequestMapping(value = "Home.htm", method = RequestMethod.GET)
    public ModelAndView loadHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ModelAndView model = new ModelAndView("Home");
        return model;
    }

    @RequestMapping(value = "Student.htm", method = RequestMethod.GET)
    public ModelAndView loadStudentPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ModelAndView model = new ModelAndView("Student");
        return model;
    }



    @RequestMapping(value = "CourseInfo.htm", method = RequestMethod.GET)
    public ModelAndView loadCourseInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ModelAndView model = new ModelAndView("CourseInfo");
        return model;
    }

    @RequestMapping(value = "Discussionboard.htm", method = RequestMethod.GET)
    public ModelAndView loadDiscussionBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ModelAndView model = new ModelAndView("Discussionboard");
        return model;
    }

    @RequestMapping(value = "Question.htm", method = RequestMethod.GET)
    public ModelAndView loadQuestionPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ModelAndView model = new ModelAndView("Question");
        return model;
    }

    @RequestMapping(value ="PopulateQues.htm",method = RequestMethod.GET)
    public void populateQuesRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        /* FOR TESTING PURPOSE SINCE WE HAVE NO DATABASE YET */
        QuestionListBean newList = new QuestionListBean();
        QuestionBean q1 = new QuestionBean("Initialize an integer i with the value 1","int i = 1;","1");
        QuestionBean q2 = new QuestionBean("Initialize an integer i with the value 2","int i = 2;","2");
        QuestionBean q3 = new QuestionBean("Initialize an integer i with the value 3","int i = 3;","3");
        newList.getProblems().add(q1);
        newList.getProblems().add(q2);
        newList.getProblems().add(q3);

        System.out.println(newList.generateJSON());
        out.println(newList.generateJSON());


    }

    @RequestMapping(value="Compile.htm", method = RequestMethod.GET)
    public void compileCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String apiKey = "hackerrank|2458825-1355|a7001ed51bce45bd9f6cc1e4bf499ef05d8d4495";
        String source = "puts 'Testing'";
        Integer lang = new Integer(5);
        String testcases = "[\"Test 1\", \"Test 2\"]";
        String format = "JSON";
        String callbackUrl = "https://testing.com/response/handler";
        String wait = "true";

        try {
            CheckerApi checkerApi = new CheckerApi();
            Submission response1 = checkerApi.submission(apiKey, source, lang, testcases, format, callbackUrl, wait);
            System.out.println(response1);
        } catch (ApiException e) {
            System.out.printf("ApiException caught: %s\n", e.getMessage());
        }
    }

    @RequestMapping(value="SubmitSol.htm",method = RequestMethod.GET)
    public void submitSolution(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
    }
}
