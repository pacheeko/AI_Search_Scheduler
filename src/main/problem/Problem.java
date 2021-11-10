package main.problem;

import java.util.ArrayList;

import main.Env;
import main.Validator;

public class Problem {

    ArrayList<Element> elements;
    ArrayList<Assignment> assignments;
    Env environment;

    public Problem() {
        this.elements = new ArrayList<Element>();
        this.assignments = new ArrayList<Assignment>();
        this.environment = new Env();
    }

    public Problem(Problem problem) {
        this.elements = problem.elements;
        this.assignments = problem.assignments;
        this.environment = problem.environment;
    }

    public Problem(ArrayList<Element> elements, ArrayList<Assignment> assignments, Env environment) {
        this.elements = elements;
        this.assignments = assignments;
        this.environment = environment;
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
        elements.remove(0);
        return element;
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

    public boolean assign(Element element, Slot slot) {
        if (assignments.contains(element) || !elements.contains(element))        
            return false;
        Assignment newAssignment = new Assignment(element, slot);
        assignments.add(newAssignment);
        elements.remove(element);
        return true;
    }

}