package com.macchiato.beans;

/**
 * Created by Raymond on 4/7/2017.
 */

/**
 * This class will be used to store all the fields necessary for a question
 */
public class QuestionBean {
    private String problem;
    private String solution;
    private String id;
    private String answer;

    public QuestionBean(String problem, String solution, String id){
        this.problem = problem;
        this.solution = solution;
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     * Used to allow us to bring our object to the front end through ajax
     * It would generate a json object of a question
     * @return String
     */
    public String generateJSON() {

        String outputString = "{\"problem\":\"" + problem + "\","
                + "\"solution\":\"" + solution + "\","
                + "\"id\":\"" + "Question " + id + "\"";
        outputString += "}";

        return outputString;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
