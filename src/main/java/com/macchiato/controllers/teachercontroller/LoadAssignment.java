package com.macchiato.controllers.teachercontroller;

import com.google.appengine.api.users.User;
import com.macchiato.beans.AssignmentBean;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import static com.macchiato.utility.TeachersUtils.findAllAssigmentBean;
import static com.macchiato.utility.TeachersUtils.AssignmentListJson;

/**
 * Created by Xiangbin on 4/27/2017.
 * Load course function will load all the course owned by this teacher,
 * and it will listed on teacher home page
 */
@Controller
public class LoadAssignment {
    //Load course function will load all the course owned by this teacher, and it will listed on teacher home page
    @RequestMapping(value = "/LoadAssignment.htm", method = RequestMethod.GET)
    public void LoadCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ClassCrs=request.getParameter("crsCode");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        User active_user = GenUtils.getActiveUser();
        String instructor_email = active_user.getEmail();
        if (instructor_email== null) {
            System.out.print("There is no active_user");
        }
        else{
            System.out.println("instructor email: "+instructor_email);
            ArrayList<AssignmentBean> newList= findAllAssigmentBean(ClassCrs);
            System.out.println(AssignmentListJson(newList));
            out.println(AssignmentListJson(newList));
        }
    }
}














