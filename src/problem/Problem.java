package problem;

import java.util.ArrayList;

public class Problem {

    ArrayList<Element> elements;
    ArrayList<Assignment> assignments;

    public Problem() {
        this.elements = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public Problem(Problem problem) {
    	elements = new ArrayList<>();
    	assignments = new ArrayList<>();
    	for(Element element : problem.getElements()) {            
            this.elements.add(element.clone());
        }
    	for(Assignment assignment : problem.getAssignments()) {
            Assignment copy = new Assignment(assignment);
            this.assignments.add(copy);
        }    	    		
    }

    public Problem(ArrayList<Element> elements) {
        this.elements = elements;
        this.assignments = new ArrayList<>();
    }

    public Problem(ArrayList<Element> elements, ArrayList<Assignment> assignments) {
        this.elements = elements;
        this.assignments = assignments;
    }

    public boolean elementAssigned(Element element) {        
        for(Assignment assignment : assignments) {
            if ( element.equals(assignment.getElement()) ) 
                return true;
        }    
        return false;
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
            || elementAssigned(newElement))
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
        if (!elements.contains(element))      {
            System.out.println(element.getName() + " does not exist");
            return false;
        }

        Assignment newAssignment = new Assignment(element, slot);
        assignments.add(newAssignment);
        elements.remove(element);
        return true;
    }    

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Problem))
            return false;
        Problem prob = (Problem)other;
        if (this.assignments.size() == prob.getAssignments().size()) {
			if (this.elements.size() == prob.getElements().size()) {
				for (Assignment assign : this.getAssignments()) {
					if (!prob.getAssignments().contains(assign)) {
						return false;
					}
				}
				for (Element element : this.getElements()) {
					if (!prob.getElements().contains(element)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
		
		for (Assignment assign : this.assignments) {
			result = 31 * result + assign.hashCode();
		}

		for (Element element : this.elements) {
			result = 31 * result + element.hashCode();
		}

        return result;
    }

}