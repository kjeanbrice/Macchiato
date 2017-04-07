package com.macchiato.controllers.urlmappingcontroller;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Karl on 4/5/2017.
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
}
