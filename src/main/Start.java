package main;

import java.util.ArrayList;
import java.util.Comparator;

import control.Control;
import problem.*;


class Start {
	
	public final static long ONE_MINUTE = 60000000000L;//OL
	private static long running_time;
	
    public static void main(String args[]) throws Exception{

        running_time = ONE_MINUTE*1440;

    	if (args.length < 5) {
			System.out.println("Missing input file or evaluation weights.");
    		printUsage();
			return;
    	} else if(args.length > 10){
			System.out.println("Too many arguments.");
			return;
		} else if(args.length == 9){
            running_time *=  Long.valueOf(args[9]);
        }



		float min_filled_weight;
		float pre_filled_weight;
		float pair_weight;
		float sec_diff_weight;
		float pen_coursemin;
		float pen_labmin;
		float pen_section;
		float pen_not_paired;

		try {
			min_filled_weight = Float.valueOf(args[1]);
			pre_filled_weight = Float.valueOf(args[2]);
			pair_weight = Float.valueOf(args[3]);
			sec_diff_weight = Float.valueOf(args[4]);
			pen_coursemin = Float.valueOf(args[5]);
			pen_labmin = Float.valueOf(args[6]);
			pen_section = Float.valueOf(args[7]);
			pen_not_paired = Float.valueOf(args[8]);
			
		} catch (Exception e) {
			System.out.println("Evaluation weights but be valid numbers.");
			return;
		}

		String file = args[0];
    	//Instantiate Parser, parse file passed in arg[0]
		try {
			Parser.parseFile(file);
		} catch (Exception e) {
			System.out.println("Errors parsing file " + file + ".");
			return;
		}		

		//Parser.printParsedInput();
    	//Set the weights of each soft constraint in the static Env class
    	Env.setMinfilledWeight(min_filled_weight);
    	Env.setPrefWeight(pre_filled_weight);
    	Env.setPairWeight(pair_weight);
    	Env.setSecdiffWeight(sec_diff_weight);
    	Env.setPen_coursemin(pen_coursemin);
    	Env.setPen_labmin(pen_labmin);
    	Env.setPen_section(pen_section);
    	Env.setPen_not_paired(pen_not_paired);

    	ProblemState initialState = initialize();
    	//Run the search
    	ProblemState solution = run(initialState);
    	printAssignments(solution);
    }
    
    private static void printUsage() {
		System.out.println("Arguments: FILE_PATH MIN_FILL_WEIGHT PREF_WEIGHT PAIR_WEIGHT SEC_DIFF_WEIGHT PEN_COURSEMIN PEN_LABMIN PEN_SECTION PEN_NOT_PAIRED");
	}

	public static ProblemState initialize() {
    	//Set up the initial problem with the elements to add
    	Problem initialProblem = new Problem();
    	Parser.getCourses().forEach(initialProblem::addElement);
    	Parser.getLabs().forEach(initialProblem::addElement);
    	ProblemState initialPS = new ProblemState(initialProblem, null);
    	
    	//Add partial assignments to initial problem
    	for (Assignment assignment : Parser.getPartialAssignments()) {
			if (!initialPS.getProblem().assign(assignment.getElement(), assignment.getSlot()) ||
				!initialPS.getConstr().checkConstraints(initialPS.getProblem().getAssignments())) {
				System.out.println("Failed to assign partial assignments.");
				System.exit(1);
			}
    	}

    	//root node
    	return new ProblemState(initialProblem, null);
    }
    
    public static ProblemState run(ProblemState initialState)  {

		ProblemState bestState = null;
		ProblemState currentState;

		//Initialize the control with the slots provided in the input file
		ArrayList<Slot> slots = new ArrayList<>();
		slots.addAll(Parser.getCourseSlots());
		slots.addAll(Parser.getlabSlots());
		Control control = new Control(initialState, slots);
		
    	long StartTime = System.nanoTime();

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
    		currentState = control.getCurrentLeaf();

    		//If the solution is complete and it has a better eval value than the current best state,
    		//then update the best state to the current state
    		if (!(currentState == null)) {
    			bestState = currentState;
    			printAssignments(bestState);
    		}
    	}
		
    	if (bestState == null) {
    		System.out.println("No solution found.");
    		System.exit(1);
    	}

		System.out.println("\nBest Solution Found:");
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

    //Returns 1 if a1 should be placed before a2, -1 otherwise
    // Sort the assignments of the search solution alphabetically by departments, and
    // increasing in value in terms of class number and lecture number. Put each tutorial/lab
    // directly under the lecture section it belongs to, and place all labs for each section
    // before the next section.
    public static Comparator<Assignment> assignmentCompare = (a1, a2) -> {
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
                } else {
                    return 1;
                }
            }
            //Place the lower course number first
            else if (c1.getNumber() < c2.getNumber()) {
                return -1;
            } else {
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
                        } else {
                            return -1;
                        }
                    } else if (l1.getNumber() < l2.getNumber()) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if (l1.getCourse().getNumber() < l2.getCourse().getNumber()) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (l1.getCourse().getNumber() < l2.getCourse().getNumber()) {
                return -1;
            } else {
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
                    return 1;
                } else {
                    return -1;
                }

            } else if (c1.getNumber() < l2.getCourse().getNumber()) {
                return -1;
            } else {
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
                } else {
                    return 1;
                }
            } else if (l1.getCourse().getNumber() < c2.getNumber()) {
                return -1;
            } else {
                return 1;
            }
        }
        System.out.println("Error comparing assignments");
        return -1;
    };
}