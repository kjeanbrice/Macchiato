package com.macchiato.utility;

import com.google.appengine.api.datastore.*;
import com.macchiato.beans.AssignmentBean;
import com.macchiato.beans.CourseBean;
import com.macchiato.beans.QuestionBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Xiangbin on 4/26/2017.
 */
public class TeachersUtils {

    //helping to find all the course that created by this teacher,
    // when you inputs a email it will return all the Coursebean list Created by this teacher
    public static ArrayList<CourseBean> isOwned(String email) {
        String CrsName;
        String CrsCode;
        String Crsdis;
        String section;
        ArrayList<CourseBean> classList = new ArrayList<CourseBean>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query q = new Query("Course").setFilter(email_filter);
        PreparedQuery pq = datastore.prepare(q);
        int numberOfClass = pq.asList(FetchOptions.Builder.withDefaults()).size();
        System.out.println("number of course;" + numberOfClass);
        if (numberOfClass == 0) {
            System.out.println("There is no class that you own");
        } else {
            for (Entity result : pq.asIterable()) {
                email = (String) result.getProperty("email");
                CrsName = (String) result.getProperty("name");
                CrsCode = (String) result.getProperty("course_code");
                Crsdis = (String) result.getProperty("description");
                section=(String) result.getProperty("section");
                CourseBean newBean = new CourseBean();
                newBean.setInstrEmail(email);
                newBean.setCrsName(CrsName);
                newBean.setCrsCode(CrsCode);
                newBean.setDescription(Crsdis);
                newBean.setSection(section);
                classList.add(newBean);
            }
        }


        System.out.println("number of class in the list:" + classList.size());
        return classList;
    }

    //this function helps generate  CourseBean list to a List of json type of string
    public static String CourseListJson(ArrayList<CourseBean> courseList) {
        String outputString = "[";
        if (courseList.size() <= 0) {
            return "[]";
        }
        for (int i = 0; i < courseList.size(); i++) {
            if (i == courseList.size() - 1) {
                outputString += courseList.get(i).generateJSON() + "]";
            } else {
                outputString += courseList.get(i).generateJSON() + ",";
            }
        }
        return outputString;
    }

    //this function will help user to find all the assignment from this course
    public static ArrayList<AssignmentBean> findAllAssigmentBean(String course_code) {
        String assignmentName;
        String dueData;
        String key;
        Date newDate;
        ArrayList<AssignmentBean> AssignmentList = new ArrayList<AssignmentBean>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query.Filter crsCode_filter = new Query.FilterPredicate("course_code", Query.FilterOperator.EQUAL, course_code);
        Query q = new Query("Assignment").setFilter(crsCode_filter);
        PreparedQuery pq = datastore.prepare(q);
        int numberOfAssignment = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if (numberOfAssignment == 0) {
            System.out.println("There is no assignment that you own");
        } else {
            for (Entity result : pq.asIterable()) {
                course_code = (String) result.getProperty("course_code");
                assignmentName = (String) result.getProperty("assignmentName");
                dueData=(String) result.getProperty("duedate");
                newDate=dataGenerate(dueData);

                Date rightnow=new Date();

                System.out.println(rightnow+" vs "+newDate);
                key = result.getKey().toString();
                AssignmentBean newBean = new AssignmentBean();

                if (Passed(newDate)) {
                        newBean.setEnd("1");
                        result.setProperty("end","1");
                        datastore.put(result);
                        System.out.print("yes");
                    }else{
                        result.setProperty("end","0");
                        newBean.setEnd("0");
                        datastore.put(result);
                    }

                newBean.setCrsCode(course_code);
                newBean.setAissignmentKey(key);
                newBean.setAissignmentName(assignmentName);
                newBean.setDuedata(newDate);
                AssignmentList.add(newBean);
            }
        }
        System.out.println("number of  assignment in the list:" + AssignmentList.size());
        Collections.sort(AssignmentList);
        return AssignmentList;
    }

    //this function  helps generate  assignmentBean list to a List of json type of string
    public static String AssignmentListJson(ArrayList<AssignmentBean> assignmentList) {
        String outputString = "[";
        if (assignmentList.size() <= 0) {
            return "[]";
        }
        for (int i = 0; i < assignmentList.size(); i++) {
            if (i == assignmentList.size() - 1) {
                outputString += assignmentList.get(i).generateJSON() + "]";
            } else {
                outputString += assignmentList.get(i).generateJSON() + ",";
            }
        }
        return outputString;
    }

    //this function will function all the question bean from one assignment
    public static ArrayList<QuestionBean> findAllQuestionBean(String assignmentKey) {
        String problem;
        String solution;
        String id;
        String questionkey;
        String teacherAns;
        System.out.println("Load all the Question :"+assignmentKey);
        ArrayList<QuestionBean> questionList = new ArrayList<QuestionBean>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query.Filter assignmentKey_filter = new Query.FilterPredicate("assignmentKey", Query.FilterOperator.EQUAL, assignmentKey);
        Query q = new Query("Question").setFilter(assignmentKey_filter);
        PreparedQuery pq = datastore.prepare(q);
        int numberOfQuestion = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if (numberOfQuestion == 0) {
            System.out.println("There is no assignment that you own");
        } else {
            for (Entity result : pq.asIterable()) {
                problem = (String) result.getProperty("problem");
                solution = (String) result.getProperty("solution");
                questionkey=(String) result.getProperty("questionKey");
                teacherAns=(String) result.getProperty("teacherAnswer");
                id = result.getKey().toString();
                QuestionBean newBean = new QuestionBean();
                newBean.setProblem(problem);
                newBean.setSolution(solution);
                newBean.setAssignmentKey(assignmentKey);
                newBean.setQuestionKey(questionkey);
                newBean.setTeacherAnswer(teacherAns);
                newBean.setId(id);
                questionList.add(newBean);
            }
        }
        System.out.println("number of  assignment in the list:" + questionList.size());
        return questionList;
    }



    //this function  helps generate  QuestionBean list to a List of json type of string
    public static String QuestionListJson(ArrayList<QuestionBean> QuestionList) {
        String outputString = "[";
        if (QuestionList.size() <= 0) {
            return "[]";
        }
        for (int i = 0; i < QuestionList.size(); i++) {
            if (i == QuestionList.size() - 1) {
                outputString += QuestionList.get(i).generateJSON() + "]";
            } else {
                outputString += QuestionList.get(i).generateJSON() + ",";
            }
        }
        return outputString;
    }

    //this function will remove all the non-digit char in the string a
    public static String numberkeeper(String a){
        String c= a.replaceAll("[^\\d.]", "");
        return c;
    }

    //this function will change a string formed date to a int array with year,month,day in side
    public static Date dataGenerate(String b){
        Date newDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newDate= sdf.parse(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static  boolean  Passed(Date due){
        Date now=new Date();
        if(due.getYear()>now.getYear()){
            return false;
        }
        else if(due.getYear()<now.getYear()){
            return true;
        }else {
        if (due.getMonth() > now.getMonth()) {
                return false;
            }
            else if(due.getMonth() < now.getMonth()){
            return true;
            }
            else {
                if (due.getDate() > now.getDate()) {
                      return false;
                  }  else {
            return true;
        }
        }
        }
    }

}
