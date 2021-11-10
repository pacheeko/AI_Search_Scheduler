package main.model;

import java.util.ArrayList;

import main.problem.Assignment;
import main.problem.Element;
import main.problem.Problem;
import main.problem.Slot;

public class SearchModel {

    ArrayList<Slot> slots;

    public SearchModel() {
        this.slots = new ArrayList<Slot>();
    }

    public SearchModel(ArrayList<Slot> slots) {
        this.slots = slots;
    }

    public ArrayList<Problem> Div(Problem prob) {
        ArrayList<Element> elements = prob.getElements();
        ArrayList<Assignment> assignments = prob.getAssignments();        
        ArrayList<Problem> subProblems = new ArrayList<Problem>();

        for(Slot slot: slots) {

        }

        return subProblems;
    }

}