package main;

import java.util.ArrayList;
import java.util.Comparator;

import control.Control;
import problem.*;


class Start {
	
	public final static long ONE_MINUTE = 60000000000L;//OL
	public final static long TWO_MINUTES = ONE_MINUTE*2;
	public final static long FIVE_MINUTES = ONE_MINUTE*5;
	
    public static void main(String args[]) throws Exception{    	
    	if (args.length < 5) {
			System.out.println("Missing input file and evaluation weights.");
    		printUsage();
			return;
    	}

		String file = args[0];

		float min_filled_weight;
		float pre_filled_weight;
		float pair_weight;
		float sec_diff_weight;

		try {
			min_filled_weight = Float.valueOf(args[1]);
			pre_filled_weight = Float.valueOf(args[2]);
			pair_weight = Float.valueOf(args[3]);
			sec_diff_weight = Float.valueOf(args[4]);

		} catch (Exception e) {
			System.out.println("Evaluation weights but be valid numbers.");
			return;
		}


    	//Instantiate Parser, parse file passed in arg[0]
		//try {
			Parser.parseFile(file);
		//} catch (Exception e) {
			//System.out.println("Errors parsing file " + file + ".");
			//return;
		//}		

    	
		Parser.printParsedInput();
    	//Set the weights of each soft constraint in the static Env class
    	Env.setMinfilledWeight(min_filled_weight);
    	Env.setPrefWeight(pre_filled_weight);
    	Env.setPairWeight(pair_weight);
    	Env.setSecdiffWeight(sec_diff_weight);
    	
    	ProblemState initialState = initialize();
    	
    	//Run the search
    	ProblemState solution = run(initialState);
    	printAssignments(solution);
    }
    
    private static void printUsage() {
		System.out.println("Usage: command  file_path minfilled pref pair secdiff");
	}

	public static ProblemState initialize() {
    	//Set up the initial problem with the elements to add
    	Problem initialProblem = new Problem();
    	Parser.getCourses().forEach((course) ->
    		initialProblem.addElement(course));
    	Parser.getLabs().forEach((lab) ->
    		initialProblem.addElement(lab));
    	
    	for (Assignment assignment : Parser.getPartialAssignments()) {
			if (!initialProblem.assign(assignment.getElement(), assignment.getSlot())) {
				System.out.println("Failed to assign partial assignments");
				System.exit(1);
			}
    	}
    		
    	return new ProblemState(initialProblem, null);
    }
    
    public static ProblemState run(ProblemState initialState) {
		ProblemState bestState = null;
		ProblemState currentState;
		//Initialize the control with the slots provided in the input file
		ArrayList<Slot> slots = new ArrayList<Slot>();
		Parser.getCourseSlots().forEach((courseSlot) -> 
				slots.add(courseSlot));
		Parser.getlabSlots().forEach((labSlot) ->
				slots.add(labSlot));
		Control control = new Control(initialState, slots);
		
    	long StartTime = System.nanoTime();
		long running_time = TWO_MINUTES;
		System.out.print("Running for a max of " + (running_time/ONE_MINUTE) + " minute");
		if(running_time != ONE_MINUTE){
			System.out.print("s");
		}
		System.out.println("...");		
    	while ((System.nanoTime() - StartTime < running_time) && (!control.getLeafs().isEmpty())) {
    		//Select the best leaf to work on
    		control.fleaf();

    		//Decide what to do with the current leaf
    		control.ftrans();

    		currentState = control.getSelectedLeaf();
    		//If the solution is complete and it has a better eval value than the current best state,
    		//then update the best state to the current state
    		if (!(currentState == null)) {
    			bestState = currentState;
    		}
    	}
		
    	if (bestState == null) {
    		System.out.println("No solution found.");
    		System.exit(1);
    	}
    	return bestState;
    }   
    
    public static void printAssignments(ProblemState state) {
    	System.out.println("Eval-Value: " + state.getEval());
    	ArrayList<Assignment> assignments = state.getProblem().getAssignments();
    	
    	assignments.sort(assignmentCompare);
    	for (Assignment a : assignments) {
    		if (a.getElement() instanceof Course) {
    			Course course = (Course) a.getElement();
    			if (course.getSection() == 0) {
    				System.out.println(course.getName() + "\t:" + a.getSlot().getDay() + "," + a.getSlot().getStartTime());
    			}
    			else {
    				System.out.println(course.getName() + "\t:" + a.getSlot().getDay() + "," + a.getSlot().getStartTime());
    			}
        		
    		}
    		else {
    			Lab lab = (Lab) a.getElement();
    			if (lab.getCourse().getSection() == 0) {
    				System.out.println(lab.getName() + "\t:" + a.getSlot().getDay() + "," + a.getSlot().getStartTime());
    			}
    			else {
    				System.out.println(lab.getName() + "\t:" + a.getSlot().getDay() + "," + a.getSlot().getStartTime());
    			}
        		
    		}

    	}
    }

    // Sort the assignments of the search solution alphabetically by departments, and
    // increasing in value in terms of class number and lecture number. Put each tutorial/lab
    // directly under the lecture section it belongs to, and place all labs for each section
    // before the next section.
    public static Comparator<Assignment> assignmentCompare = new Comparator<Assignment>() {
		//Returns 1 if a1 should be placed before a2, -1 otherwise
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
    					return -1;
    				}
    				else {
    					return 1;
    				}
    			}
    			//Place the lower course number first
    			else if (c1.getNumber() < c2.getNumber()) {
    				return -1;
    			}
    			else {
    				return 1;
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
    							return 1;
    						}
    						else {
    							return -1;
    						}
    					}
    					else if (l1.getNumber() < l2.getNumber()) {
    						return -1;
    					}
    					else {
    						return 1;
    					}
    				}
    				else if (l1.getCourse().getNumber() < l2.getCourse().getNumber()) {
    					return -1;
    				}
    				else {
    					return 1;
    				}
    			}
    			else if (l1.getCourse().getNumber() < l2.getCourse().getNumber()){
    				return -1;
    			}
    			else {
    				return 1;
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
    				return -1;
    			}
    			else {
    				return 1;
    			}
    		}
    		//If a2 is a course and a1 is a lab
    		else if (a1.getElement() instanceof Lab && a2.getElement() instanceof Course) {
    			Lab l1 = (Lab) a1.getElement();
    			Course c2 = (Course) a2.getElement();
    			if (l1.getCourse().getNumber() == c2.getNumber()) {
    				if (l1.getCourse().getSection() < c2.getSection()) {
    					return -1;
    				}
    				else {
    					return 1;
    				}
    			}
    			else if (l1.getCourse().getNumber() < c2.getNumber()) {
    				return -1;
    			}
    			else {
    				return 1;
    			}
    		}
			System.out.println("Error comparing assignments");
    		return -1;
    	}
    	
    };
}