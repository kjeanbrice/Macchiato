package com.macchiato.beans;

import java.util.ArrayList;

/**
 * Created by Raymond on 4/7/2017.
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
