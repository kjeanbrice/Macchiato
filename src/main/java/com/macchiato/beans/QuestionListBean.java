package com.macchiato.beans;

import java.util.ArrayList;

/**
 * Created by Raymond on 4/7/2017.
 */

/**
 * This class will be used so we can store multople questions under one object called
 * assignment because each assignment would
 * have multiple questions nested in it.
 */
public class QuestionListBean {
    private ArrayList<QuestionBean> problems;

    public QuestionListBean(){
        this.problems = new ArrayList<QuestionBean>();
    }

    public ArrayList<QuestionBean> getProblems() {
        return problems;
    }

    public void setProblems(ArrayList<QuestionBean> problems) {
        this.problems = problems;
    }

    /**
     * Used to allow us to bring our object to the front end through ajax
     * It would generate a json array of questions for an assignment
     * @return String
     */
    public String generateJSON() {
        String outputString = "{\"Questions\":[";

        if (problems.isEmpty()) {
            return outputString;
        }

        for (int i = 0; i < problems.size(); i++) {
            if (i == problems.size() - 1) {
                outputString += problems.get(i).generateJSON() + "]";
            } else {
                outputString += problems.get(i).generateJSON() + ",";
            }
        }

        outputString += "}";

        return outputString;
    }
}
