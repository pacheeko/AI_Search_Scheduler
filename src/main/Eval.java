package main;

import problem.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Eval {

	//Eval calculates the penalties based on the soft constraints of the current assignments
	//The partial evaluate function only evaluates new penalties based on the latest assignment
	//The evaluate function is only called if the elements in the problemState is empty, and so
	//the problem is solved. The evaluate function then adds the penalties for minFilled to the eval
	//of the problemState

    private boolean DEBUG = true;
    ArrayList<Assignment> myAssignments;
    private ArrayList<Assignment> checkedCourseMin;
    private ArrayList<Assignment> checkedLabMin;
    PrintStream output;

    public int evaluate(ArrayList<Assignment> assignments, int assignmentNumber, int eval) {

        int score = 0;
        try {
            if (DEBUG)
                output = new PrintStream("eval_log_" + assignmentNumber + ".txt");

            myAssignments = assignments;
            checkedCourseMin = new ArrayList<>();
            checkedLabMin = new ArrayList<>();

            for(Assignment a : myAssignments){
                score += minFilled(a);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return eval + score;
    }

    public int partialEvaluate(ArrayList<Assignment> assignments, int eval) {
    	if (assignments.isEmpty()) {
    		return eval;
    	}
    	Assignment mostRecent = assignments.get(assignments.size()-1);
    	
    	if (partialSecDiff(assignments, mostRecent))
    		eval += (1 * Env.getSecdiffWeight());
    	if (!partialPairs(assignments, mostRecent)) {
    		eval += (1 * Env.getPairWeight());
    	};
    	eval += partialPref(assignments, mostRecent);
        return eval;
    }
    /*
    Compares the mostRecent assignment to each assignment in the problemState. Returns true 
    if there is another course with the same name, number, and slot in the assignments as the
    most recent assignment has.
     * 
     */
    private boolean partialSecDiff(ArrayList<Assignment> assignments, Assignment mostRecent) {
    	if (mostRecent.getSlot() instanceof LabSlot) return false;
    	for (Assignment a : assignments) {
    		if (compareCoursesNameAndNumber(a, mostRecent)) {
    			if (compareSlot(a.getSlot(), mostRecent.getSlot())) {
    				return true;
    			}
    		}
    	}
    	return false;
    }

    /*
    Checks the most recent assignments against the preferences list
    If the most recently assigned course or lab is in the preference list, partialPref
    then checks if the assignment and the preference have the same slot.
    If they do have the same slot, it returns 0. If they do not, it returns the weight given
    to the preference * the weight that preferences are given in the given search
     * 
     */
    private int partialPref(ArrayList<Assignment> assignments, Assignment mostRecent) {
    	ArrayList<Preference> preferences = Parser.getPreferences();
    	if (mostRecent.getElement() instanceof Course) {
    		Course c = (Course) mostRecent.getElement();
    		for (Preference p : preferences) {
        		if (p.getElement() instanceof Course) {
        			Course prefCourse = (Course) p.getElement();
        			if (compareCourses(c, prefCourse)) {
        				if (compareSlot(mostRecent.getSlot(), p.getSlot())) {
        					return 0;
        				}
        				else {
        					return p.getWeight() * Env.getPrefWeight();
        				}
        			}
        		}
        	}
    	} else {
    		Lab l = (Lab) mostRecent.getElement();
    		for (Preference p : preferences) {
    			if (p.getElement() instanceof Lab) {
    				Lab prefLab = (Lab) p.getElement();
    				if (compareLabs(l, prefLab)) {
    					if (compareSlot(mostRecent.getSlot(), p.getSlot())) {
    						return 0;
    					}
    					else {
    						return p.getWeight() * Env.getPrefWeight();
    					}
    				}
    			}
    		}
    	}
    	return 0;
    }
    
    /*
    Iterates through the pairs located in the parser class. Checks to see if the most recent
    assignment is located in the set of pairs. If it is, then checks to see if the other pair
    is also in the right slot or if it's in another slot. Returns false only if the two elements 
    in the pair are both in assignments and are located in different slots
    */
    private boolean partialPairs(ArrayList<Assignment> assignments, Assignment mostRecent) {
    	ArrayList<Element[]> pairs = Parser.getPairs();
    	if (mostRecent.getElement() instanceof Course) {
    		Course c = (Course) mostRecent.getElement();
        	for (Element[] pair : pairs) {
        		if (pair[0] instanceof Course) {
        			Course pairCourse = (Course) pair[0];
        			if (compareCourses(c, pairCourse)) {
        				return elementExistsSlot(pair[1],assignments,mostRecent.getSlot()); 
        			}
        		}
        		else if (pair[1] instanceof Course) {
        			Course pairCourse = (Course) pair[1];
        			if (compareCourses(c, pairCourse)) {
        				return elementExistsSlot(pair[0],assignments,mostRecent.getSlot());
        			}
        		}
        		
        	}
    	}
    	else {
    		Lab l = (Lab) mostRecent.getElement();
    		for (Element[] pair : pairs) {
    			if (pair[0] instanceof Lab) {
    				Lab pairLab = (Lab) pair[0];
    				if (compareLabs(l, pairLab)) {
    					return elementExistsSlot(pair[1],assignments,mostRecent.getSlot());
    				}
    			}
    			else if (pair[1] instanceof Lab) {
    				Lab pairLab = (Lab) pair[1];
    				if (compareLabs(l, pairLab)) {
    					return elementExistsSlot(pair[0],assignments,mostRecent.getSlot());
    				}
    			}
    		}
    	}

    	return true;
    }
    
    //Helper Functions
    private boolean compareCoursesNameAndNumber(Assignment a1, Assignment a2) {
    	if (a1.getElement() instanceof Lab || a2.getElement() instanceof Lab) return false;
    	if (a1.getSlot().equals(a2.getSlot())) {
    		Course c1 = (Course) a1.getElement();
    		Course c2 = (Course) a2.getElement();
    		if (c1.getName().equals(c2.getName())) {
    			if (c1.getNumber() == c2.getNumber()) {
    				if (c1.getSection() != c2.getSection()) {
        				return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    private boolean compareCourses(Course c1, Course c2) {
    	if (c1.getName().equals(c2.getName())) {
			if (c1.getNumber() == c2.getNumber()) {
				if (c1.getSection() == c2.getSection()) {
					return true;
				}
			}
		}
    	return false;
    }
    
    private boolean compareLabs(Lab l1, Lab l2) {
    	if (l1.getDepartment().equals(l2.getDepartment())) {
    		if (l1.getName().equals(l2.getName())) {
    			if (l1.getNumber() == l2.getNumber()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    private boolean compareSlot(Slot s1, Slot s2) {
    	if (s1.getDay().equals(s2.getDay())) {
    		if (s1.getStartTime().equals(s2.getStartTime())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //Returns false only if ele is assigned to a different time slot than slot
    private boolean elementExistsSlot(Element ele, ArrayList<Assignment> assignments, Slot slot) {
    	for (Assignment assign : assignments) {
    		if (assign.getElement().equals(ele)) {
    			if (compareSlot(assign.getSlot(), slot)) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    
    
    
    private int minFilled(Assignment assign){

        int penalty = 0;

        if(assign.getSlot() instanceof LabSlot){
            if(!checkedCourseMin.contains(assign)){
                penalty = meetsCourseMin(assign);
            }
        }else{
            if(!checkedLabMin.contains(assign)){
                penalty = meetsLabMin(assign);
            }
        }

        return penalty * Env.getMinfilledWeight();
    }

    private int meetsCourseMin(Assignment assign){

        Slot slot = assign.getSlot();

        int courseMin = slot.getMin();
        int courseAccumulator = 1;

        for(Assignment a : myAssignments){
            if (!a.equals(assign) && !checkedCourseMin.contains(a)) {
                if (slot.equals(a.getSlot())){
                    courseAccumulator++;
                    checkedCourseMin.add(a);
                }
            }
        }

        if(courseMin > courseAccumulator){
            if(DEBUG) {
                output.println("    Constraint Not Met: Course Min");
                output.println("    Course min of slot" + slot.getInfo() + " is "
                        + slot.getMin() + ", but the number of courses assigned is " + courseAccumulator);
            }
            return courseMin - courseAccumulator;
        }else{
            if(DEBUG)
                output.println("    Constraint Met\n");
            return 0;
        }
    }

    private int meetsLabMin(Assignment assign){

        Slot slot = assign.getSlot();

        int labMin = slot.getMin();
        int labAccumulator = 1;

        for(Assignment a : myAssignments){
            if (!a.equals(assign) && !checkedLabMin.contains(a)) {
                if (slot.equals(a.getSlot())){
                    labAccumulator++;
                    checkedLabMin.add(a);
                }
            }
        }

        if(labMin > labAccumulator){
            if(DEBUG) {
                output.println("    Constraint Not Met: Lab Min");
                output.println("    Lab min of slot" + slot.getInfo() + " is "
                        + slot.getMin() + ", but the number of lab assigned is " + labAccumulator);
            }
            return labMin - labAccumulator;
        }else{
            if(DEBUG)
                output.println("    Constraint Met\n");
            return 0;
        }
    }
}