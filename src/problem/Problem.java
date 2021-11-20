package problem;

import main.Constr;
import main.Env;
import main.Eval;
import main.Parser;

import java.util.ArrayList;

public class Problem {

    ArrayList<Element> elements;
    ArrayList<Assignment> assignments;
    Env environment;
    Constr myConstr = new Constr(new Parser());
    Eval myEval = new Eval(1, 1, 1, 1);

    public Problem() {
        this.elements = new ArrayList<Element>();
        this.assignments = new ArrayList<Assignment>();
    }

    public Problem(Problem problem) {
        this.elements = problem.elements;
        this.assignments = problem.assignments;
    }

    public Problem(ArrayList<Element> elements) {
        this.elements = elements;
        this.assignments = new ArrayList<Assignment>();
    }

    public Problem(ArrayList<Element> elements, ArrayList<Assignment> assignments) {
        this.elements = elements;
        this.assignments = assignments;
    }

    
    /** 
     * Add a new element to the element list.
     * Fails if this element is already on the list, or
     * or is part of an assignment in assignments.
     * 
     * @param newElement Element to add
     * @return boolean  Success
     */
    public boolean addElement(Element newElement) {
        if (elements.contains(newElement) 
            || assignments.contains(newElement))
            return false;
        elements.add(newElement);
        return true;
    }

    public ArrayList<Element> getElements() {
        return this.elements;
    }

    public Element nextElement() {
        if (elements.isEmpty())
            return null;
        Element element = elements.get(0);
        return element;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public boolean isSolved() {

        //assignment number is for debug, element list is for testing only
        if (elements.isEmpty() && myConstr.checkConstraints(assignments, 0))
            return true;
        if (!myConstr.checkPartialConstraints(assignments, 0, false))
            return true;
        if (myEval.partialEvaluate(assignments) > Env.getMinPenalty())
            return true;
        return false;
    }

    public boolean assign(Element element, Slot slot) {
        if (assignments.contains(element) || !elements.contains(element))        
            return false;
        Assignment newAssignment = new Assignment(element, slot);
        assignments.add(newAssignment);
        elements.remove(element);
        return true;
    }    

}