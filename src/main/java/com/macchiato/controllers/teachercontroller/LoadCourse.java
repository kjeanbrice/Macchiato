package com.macchiato.controllers.teachercontroller;

import com.google.appengine.api.users.User;
import com.macchiato.beans.CourseBean;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.macchiato.utility.TeachersUtils.CourseListJson;
import static com.macchiato.utility.TeachersUtils.isOwned;

/**
 * Created by Xiangbin on 4/19/2017.
 */
@Controller
public class LoadCourse {
    //Load course function will load all the course owned by this teacher, and it will listed on teacher home page
    @RequestMapping(value = "/LoadCourse.htm", method = RequestMethod.GET)
    public void LoadCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        User active_user = GenUtils.getActiveUser();
        String instructor_email = active_user.getEmail();
        if (instructor_email== null) {
            System.out.print("ERROR2");
        }
        else{
            System.out.println("instructor email: "+instructor_email);
            //System.out.print("Part3");
            ArrayList<CourseBean> newList= isOwned(instructor_email);
            //test---
//            CourseBean newBean=new CourseBean("CSE123","teacher1@gmial.com","yes");
//            CourseBean newBean2=new CourseBean("CSE122","teacher1@gmial.com","yes");
//            newList.add(newBean2);
//            newList.add(newBean);
            //test---
            System.out.println(CourseListJson(newList));
            out.println(CourseListJson(newList));
        }
    }
}
