package model;

import problem.Element;
import problem.Problem;
import problem.Slot;

import java.util.ArrayList;

public class SearchModel {    

    public static ArrayList<Problem> Div(Problem prob, ArrayList<Slot> slots) {
        Element nextElement = prob.nextElement();
        ArrayList<Problem> subProblems = new ArrayList<>();

        //checking all slots for possible divisions of the problem
        for(Slot slot: slots) {
            Problem subProblem = new Problem(prob);
            //if element is able to be added to the slot
            if (subProblem.assign(nextElement, slot));            
                subProblems.add(subProblem);
            }

        return subProblems;
    }

    
}