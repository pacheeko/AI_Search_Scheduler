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

    public Problem() {
        this.elements = new ArrayList<Element>();
        this.assignments = new ArrayList<Assignment>();
    }

    public Problem(Problem problem) {
    	elements = new ArrayList<Element>();
    	assignments = new ArrayList<Assignment>();
    	
    	problem.elements.forEach((element) ->
    		this.elements.add(element));
    	problem.assignments.forEach((assignment) ->
    		this.assignments.add(assignment));
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

    public boolean assign(Element element, Slot slot) {
        if (!elements.contains(element))        
            return false;
        Assignment newAssignment = new Assignment(element, slot);
        assignments.add(newAssignment);
        elements.remove(element);
        return true;
    }    

}