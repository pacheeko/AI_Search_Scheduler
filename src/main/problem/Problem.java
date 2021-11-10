package main.problem;

import java.util.ArrayList;

import main.Env;
import main.Validator;

public class Problem {

    ArrayList<Element> elements;
    ArrayList<Assignment> assignments; 
    Env environment;

    public Problem() {
        elements = new ArrayList<Element>();
        assignments = new ArrayList<Assignment>();
        environment = new Env();
    }

    public Problem(ArrayList<Element> elements, ArrayList<Assignment> assignments, Env environment) {
        this.elements = elements;
        this.assignments = assignments;
        this.environment = environment;
    }

    public boolean addElement(Element element) {
        boolean success = false;

        return success;
    }

    public ArrayList<Element> getElements() {
        return this.elements;
    }

    public ArrayList<Assignment> getAssignments() {
        return this.assignments;
    }

    public boolean isSolved() {
        if (elements.isEmpty() && Validator.constr(assignments))
            return true;
        if (!Validator.constrPartial(assignments))
            return true;        
        if (Validator.evalPartial(assignments) > this.environment.getMinPenalty())
            return true;
        return false;
    }    

}