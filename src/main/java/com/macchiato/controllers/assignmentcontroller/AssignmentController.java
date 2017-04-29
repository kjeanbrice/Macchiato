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
/**
 * This controller will be used to map our urls to the frontend so we can activate our javascript
 * functions
 */
public class AssignmentController {

    /**
     * This method will be used to populate the question page with info
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value ="PopulateQues.htm",method = RequestMethod.GET)
    public void populateQuesRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        QuestionListBean newList = new QuestionListBean();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query.Filter assignment_filter = new Query.FilterPredicate("assignmentKey",Query.FilterOperator.EQUAL,"turtle");
        Query q = new Query("question").setFilter(assignment_filter);
        PreparedQuery pq = datastore.prepare(q);
        System.out.println("pq as a list is" + pq.asList(FetchOptions.Builder.withDefaults()));
        for(Entity e : pq.asList(FetchOptions.Builder.withDefaults())){
            QuestionBean question = new QuestionBean((String)e.getProperty("problem"),(String)e.getProperty("solution"),(String)e.getProperty("questionnum"));
            question.setAnswer((String)e.getProperty("studentans"));
            System.out.println("problem is" + question.getProblem());
            newList.getProblems().add(question);
        }
        System.out.println(newList.generateJSON());
        out.println(newList.generateJSON());
    }

    @RequestMapping(value="Compile.htm", method = RequestMethod.POST)
    public void compileCode(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("text");
        PrintWriter out = response.getWriter();
        String text = request.getParameter("text");
     // System.out.println(text);
        String apiKey = "hackerrank|2458825-1355|a7001ed51bce45bd9f6cc1e4bf499ef05d8d4495";
        String source = text;
        Integer lang = new Integer(3);
        String testcases = "[\"Test 1\", \"Test 2\"]";
        String format = "JSON";
        String callbackUrl = "";
        String wait = "true";

        try {
            CheckerApi checkerApi = new CheckerApi();
            Submission response1 = checkerApi.submission(apiKey, source, lang, testcases, format, callbackUrl, wait);
            Result answer = response1.getResult();

            List<String> stdout = response1.getResult().getStdout();
         //   System.out.println(answer.getCompilemessage());
            String finmessage = answer.getCompilemessage();
         //   System.out.println("Compiler message is " + finmessage);
         //   System.out.println("User entered text is " + text);
            if (finmessage.equals("")){
                out.println(stdout.get(0).trim());
            }
            else{
                out.println(finmessage);
            }

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

    /**
     * Helper method to store dummy data for testing purposes
     */
    public void useDummyData(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity exam1 = new Entity("assignment");
        exam1.setProperty("crsCode","1234");
        exam1.setProperty("assignmentKey","turtle");
        datastore.put(exam1);
        Entity question1 = new Entity("question");
        question1.setProperty("problem","Initialize an integer i with the value 1");
        question1.setProperty("solution","int i = 1;");
        question1.setProperty("studentans","");
        question1.setProperty("assignmentKey","turtle");
        question1.setProperty("questionnum","1");
        datastore.put(question1);
        Entity question2 = new Entity("question");
        question2.setProperty("problem","Initialize an integer i with the value 2");
        question2.setProperty("solution","int i = 2;");
        question2.setProperty("studentans","");
        question2.setProperty("assignmentKey","turtle");
        question2.setProperty("questionnum","2");
        datastore.put(question2);
    }
}
