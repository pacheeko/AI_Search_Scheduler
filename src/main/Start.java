package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import problem.*;

class Start {
    public static void main(String args[]) throws Exception {
    	//Check if file path was passed
    	if (args.length == 0) {
    		throw new FileNotFoundException("Please provide the path of a valid input file.");
    	}
    	//Instantiate Parser, parse file passed in arg[0]
    	Parser parser = new Parser();
    	parser.parseFile(args[0]);
    	System.out.println(parser.getPairs().get(0)[1].getName());
    	//Run the search
    	//ProblemState solution = run();
    	//printAssignments(solution);
    }
    
    public static void printAssignments(ProblemState state) {
    	System.out.println("Eval-Value: " + state.getEval());
    	ArrayList<Assignment> assignments = state.getProblem().getAssignments();
    	
    	assignments.sort(assignmentCompare);
    	for (Assignment a : assignments) {
    		if (a.getElement() instanceof Course) {
    			Course course = (Course) a.getElement();
    			//TODO: Remove lecture section for courses that only have 1 lab or tutorial section
        		System.out.println(course.getName() + " " + course.getNumber() + " LEC " + course.getSection() + "\t:" + a.getSlot().getDay() + "," + a.getSlot().getStartTime());
    		}
    		else {
    			Lab lab = (Lab) a.getElement();
    			//TODO: Remove lecture section for courses that only have 1 lab or tutorial section
        		System.out.println(lab.getCourse().getName() + " " + lab.getCourse().getNumber() + " LEC " + lab.getCourse().getSection() + " " + lab.getName() + " " + lab.getNumber() + "\t:" + a.getSlot().getDay() + "," + a.getSlot().getStartTime());
    		}

    	}
    }

    // Sort the assignments of the search solution alphabetically by departments, and
    // increasing in value in terms of class number and lecture number. Put each tutorial/lab
    // directly under the lecture section it belongs to, and place all labs for each section
    // before the next section.
    public static Comparator<Assignment> assignmentCompare = new Comparator<Assignment>() {
    	
    	public int compare (Assignment a1, Assignment a2) {
    		int cmp = a1.getElement().getDepartment().compareTo(a2.getElement().getDepartment());
    		
    		//If they are not the same department, sort them alphabetically by department
    		if (cmp != 0) {
    			return cmp;
    		}
    		
    		//If both assignments are courses
    		else if (a1.getElement() instanceof Course && a2.getElement() instanceof Course) {
    			Course c1 = (Course) a1.getElement();
    			Course c2 = (Course) a2.getElement();
    			// If they are the same course, sort by section number
    			if (c1.getNumber() == c2.getNumber()) {
    				if (c1.getSection() < c2.getSection()) {
    					return 1;
    				}
    				else {
    					return -1;
    				}
    			}
    			//Place the lower course number first
    			else if (c1.getNumber() < c2.getNumber()) {
    				return 1;
    			}
    			else {
    				return -1;
    			}
    		}
    		//If both assignments are labs
    		else if (a1.getElement() instanceof Lab && a2.getElement() instanceof Lab) {
    			Lab l1 = (Lab) a1.getElement();
    			Lab l2 = (Lab) a2.getElement();
    			if (l1.getCourse().getNumber() == l2.getCourse().getNumber()) {
        			//If they are part of the same course, sort by section
    				if (l1.getCourse().getSection() == l2.getCourse().getSection()) {
    					//If they are part of the same section, sort by lab number
    					if (l1.getNumber() == l2.getNumber()) {
    						if (l1.getName().compareTo("TUT") == 0) {
    							return -1;
    						}
    						else {
    							return 1;
    						}
    					}
    					else if (l1.getNumber() < l2.getNumber()) {
    						return 1;
    					}
    					else {
    						return -1;
    					}
    				}
    				else if (l1.getCourse().getSection() < l2.getCourse().getSection()) {
    					return 1;
    				}
    				else {
    					return -1;
    				}
    			}
    			else if (l1.getCourse().getNumber() < l1.getCourse().getNumber()){
    				return 1;
    			}
    			else {
    				return -1;
    			}
    		}
    		//If a1 is a course and a2 is a lab
    		else if (a1.getElement() instanceof Course && a2.getElement() instanceof Lab) {
    			Course c1 = (Course) a1.getElement();
    			Lab l2 = (Lab) a2.getElement();
    			if (c1.getNumber() == l2.getCourse().getNumber()) {
    				//If the lab course number is less than the course number, place the lab first,
    				//otherwise place the course first
    				if (c1.getSection() > l2.getCourse().getSection()) {
    					return -1;
    				}
    				else {
    					return 1;
    				}
    				
    			}
    			else if (c1.getNumber() < l2.getCourse().getNumber()) {
    				return 1;
    			}
    			else {
    				return -1;
    			}
    		}
    		//If a2 is a course and a1 is a lab
    		else if (a1.getElement() instanceof Lab && a2.getElement() instanceof Course) {
    			Lab l1 = (Lab) a1.getElement();
    			Course c2 = (Course) a2.getElement();
    			if (l1.getCourse().getNumber() == c2.getNumber()) {
    				
    			}
    			else if (l1.getCourse().getNumber() < c2.getNumber()) {
    				return 1;
    			}
    			else {
    				return -1;
    			}
    		}
			System.out.println("Error comparing assignments");
    		return 1;
    	}
    	
    };
    
    public static void run() {
    	
    }
}