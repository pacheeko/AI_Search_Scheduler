package model;

import problem.Element;
import problem.Problem;
import problem.Slot;

import java.util.ArrayList;

public class SearchModel {

    ArrayList<Slot> slots;    

    public SearchModel() {
        this.slots = new ArrayList<Slot>();
    }

    public SearchModel(ArrayList<Slot> slots) {
        this.slots = slots;
    }    

    public ArrayList<Problem> Div(Problem prob) {
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