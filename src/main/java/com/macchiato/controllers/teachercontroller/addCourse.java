package com.macchiato.controllers.teachercontroller;

import com.macchiato.beans.ClassBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Xiangbin on 4/19/2017.
 */
@Controller
public class addCourse {

    ArrayList<ClassBean> ClassBeanList=new ArrayList<ClassBean>();

@RequestMapping(value="/addCourse", method = RequestMethod.POST)
    public void addCourse(HttpServletRequest request, HttpServletResponse response){
    response.setContentType("text/html;charset=UTF-8");
    //PrintWriter out = response.getWriter();
    String ClassName=request.getParameter("ClassName");
    String ClassDis=request.getParameter("ClassDis");
    ClassBean newClass=new ClassBean(ClassName,ClassDis);
    System.out.print(newClass.getClassName()+newClass.getCourseDescription()+"dwaddawdawd "+newClass.getClassCode());
    //return "TeacherHomePage";
}





}


