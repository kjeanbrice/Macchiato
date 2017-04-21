package com.macchiato.controllers.assignmentcontroller;

import com.google.appengine.api.datastore.*;
import com.hackerrank.api.client.ApiException;
import com.hackerrank.api.hackerrank.api.CheckerApi;
import com.hackerrank.api.hackerrank.model.Result;
import com.hackerrank.api.hackerrank.model.Submission;
import com.macchiato.beans.QuestionBean;
import com.macchiato.beans.QuestionListBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Raymond on 4/20/2017.
 */

@Controller
public class AssignmentController {

    @RequestMapping(value ="PopulateQues.htm",method = RequestMethod.GET)
    public void populateQuesRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @RequestMapping(value="Compile.htm", method = RequestMethod.POST)
    public void compileCode(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("text");
        PrintWriter out = response.getWriter();
        String text = request.getParameter("text");
        System.out.println(text);
        String apiKey = "hackerrank|2458825-1355|a7001ed51bce45bd9f6cc1e4bf499ef05d8d4495";
        String source = text;
        Integer lang = new Integer(3);
        String testcases = "[int i = 1;]";
        String format = "JSON";
        String callbackUrl = "";
        String wait = "false";

        try {
            CheckerApi checkerApi = new CheckerApi();
            Submission response1 = checkerApi.submission(apiKey, source, lang, testcases, format, callbackUrl, wait);
            Result answer = response1.getResult();
            System.out.println(answer.getCompilemessage());
            String finmessage = answer.getCompilemessage();
            out.println(finmessage);
        } catch (ApiException e) {
            System.out.printf("ApiException caught: %s\n", e.getMessage());
        }
    }

    @RequestMapping(value="SubmitSol.htm",method = RequestMethod.GET)
    public void submitSolution(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("text/html;charset=UTF-8");
    }

    @RequestMapping(value="LoadQues.htm",method = RequestMethod.GET)
    public void loadQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("application/json");

    }

    public QuestionListBean getQuestionListInfo(Key assignmentKey){

        QuestionListBean questions = new QuestionListBean();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query.Filter questionkey_filter = new Query.FilterPredicate("question_key",Query.FilterOperator.EQUAL,assignmentKey);
        Query q = new Query("Question").setFilter(questionkey_filter);
        PreparedQuery pq = datastore.prepare(q);
        /*Retrieve question list*/
        List<Entity> question_list = pq.asList(FetchOptions.Builder.withDefaults());
        for(int i = 0; i < question_list.size(); i++){

            Entity question = question_list.get(i);
            String problem = (String) question.getProperty("problem");
            String solution = (String) question.getProperty("solution");
            String answer = (String) question.getProperty("answer");
            QuestionBean qb = new QuestionBean(problem,solution,Integer.toString(i));
            qb.setAnswer(answer);
            questions.getProblems().add(qb);
        }

        return questions;

    }


}
