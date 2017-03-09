package com.macchiato;

import com.google.appengine.api.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by Karl Jean-Brice and Raymond Xue
 */
@Controller
public class Test {


    @RequestMapping(value = "test.htm", method = RequestMethod.GET)
    public void processTestRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        System.out.println("Test");
        }
    }


