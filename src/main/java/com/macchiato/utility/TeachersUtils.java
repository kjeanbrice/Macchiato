package com.macchiato.utility;

import com.google.appengine.api.datastore.*;
import com.macchiato.beans.AssignmentBean;
import com.macchiato.beans.CourseBean;
import com.macchiato.beans.QuestionBean;

import java.util.Date;


import java.util.ArrayList;

/**
 * Created by Xiangbin on 4/26/2017.
 */
public class TeachersUtils {

    //helping to find all the course that created by this teacher,
    // when you inputs a email it will return all the Coursebean list Created by this teacher
    public static ArrayList<CourseBean> isOwned(String email) {
        System.out.print(123);
        String CrsName;
        String CrsCode;
        String Crsdis;
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
                CrsName = (String) result.getProperty("crsName");
                CrsCode = (String) result.getProperty("crsCode");
                Crsdis = (String) result.getProperty("description");
                CourseBean newBean = new CourseBean();
                newBean.setInstrEmail(email);
                newBean.setCrsName(CrsName);
                newBean.setCrsCode(CrsCode);
                newBean.setDescription(Crsdis);
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
    public static ArrayList<AssignmentBean> findAllAssigmentBean(String crsCode) {
        String assignmentName;
        Date dueData;
        String key;
        ArrayList<AssignmentBean> AssignmentList = new ArrayList<AssignmentBean>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query.Filter crsCode_filter = new Query.FilterPredicate("crsCode", Query.FilterOperator.EQUAL, crsCode);
        Query q = new Query("Assignment").setFilter(crsCode_filter);
        PreparedQuery pq = datastore.prepare(q);
        int numberOfAssignment = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if (numberOfAssignment == 0) {
            System.out.println("There is no assignment that you own");
        } else {
            for (Entity result : pq.asIterable()) {
                crsCode = (String) result.getProperty("crsCode");
                assignmentName = (String) result.getProperty("assignmentName");
                key = result.getKey().toString();
                AssignmentBean newBean = new AssignmentBean();
                newBean.setCrsCode(crsCode);
                newBean.setAissignmentKey(key);
                newBean.setAissignmentName(assignmentName);
                AssignmentList.add(newBean);
            }
        }
        System.out.println("number of  assignment in the list:" + AssignmentList.size());
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


    public static ArrayList<QuestionBean> findAllQuestionBean(String assignmentKey) {
        String problem;
        String solution;
        String id;
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
                id = result.getKey().toString();
                QuestionBean newBean = new QuestionBean();
                newBean.setProblem(problem);
                newBean.setSolution(solution);
                newBean.setAssignmentKey(assignmentKey);
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
                outputString += QuestionList.get(i).generateJSONwithAssignmentKey() + "]";
            } else {
                outputString += QuestionList.get(i).generateJSONwithAssignmentKey() + ",";
            }
        }
        return outputString;
    }

}
