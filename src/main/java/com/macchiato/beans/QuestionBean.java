package com.macchiato.beans;

/**
 * Created by Raymond on 4/7/2017.
 */
public class QuestionBean {
    private String problem;
    private String solution;
    private String id;

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

    public String generateJSON() {

        String outputString = "{\"problem\":\"" + problem + "\","
                + "\"solution\":\"" + solution + "\","
                + "\"id\":\"" + "Question " + id + "\"";
        outputString += "}";

        return outputString;
    }
}
