package model;

import problem.Element;
import problem.Problem;
import problem.Slot;

import java.util.ArrayList;

public class SearchModel {    

    public static ArrayList<Problem> Div(Problem prob, ArrayList<Slot> slots) {
        Element nextElement = prob.nextElement();
        ArrayList<Problem> subProblems = new ArrayList<Problem>();

        for(Slot slot: slots) {
            Problem subProblem = new Problem(prob);
            if (subProblem.assign(nextElement, slot));            
                subProblems.add(subProblem);
        }

        return subProblems;
    }

    
}