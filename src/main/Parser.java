package main;

import problem.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.*;
import java.util.Scanner;

public class Parser {
    // IDEA: have functions to return each type of data present in the input file.
    // ie. slots, course identifiers, pairs, etc.
    // Each return should be of a data type that coincides with the object being
    // represented.

    //
    // GLOBAL VARIABLES
    //
    private static File inputFile;
    private static String name;
    private static ArrayList<CourseSlot> courseSlots;
    private static ArrayList<LabSlot> labSlots;
    private static ArrayList<Course> courses;
    private static ArrayList<Lab> labs;
    private static ArrayList<Element[]> notCompatible;
    private static ArrayList<Element[]> pairs;
    private static ArrayList<Assignment> unwanted;
    private static ArrayList<Assignment> partialAssignments;
    private static ArrayList<Preference> preferences;

    //
    // UTILITY METHODS
    //

    // createScanner - Creates a new scanner to parse an input file
    // INPUT: None
    // RETURNS: A scanner object
    private static Scanner createScanner() throws Exception {
        Scanner scanner = new Scanner(inputFile);
        scanner.useDelimiter(",");
        return scanner;
    }

    // getDay - Returns the enum type of a day, given a matching string type
    // INPUT: String representing abbreviated day
    // RETURNS: A Day enum type
    private static Day getDay(String string) {
        if (string.equals("MO")) {
            return Day.MO;
        }
        if (string.equals("TU")) {
            return Day.TU;
        }
        if (string.equals("FR")) {
            return Day.FR;
        }
        System.out.println("ERROR: Invalid day in input file: " + "<" + string + ">" + "\nExiting program...");
        System.exit(1);
        return null;
    }

    // getCourse - Returns a course object contained in the courses arraylist with
    // the same name as the input string
    // INPUT: Course name string of form "<department> <number> <section>"
    // RETURNS: A course object if one is found
    private static Course getCourse(String string, boolean strict) {
        for (Course course : courses) {
            if (course.getName().equals(string)) {
                return course;
            }
        }
        if(strict) {
            System.out.println("ERROR: Invalid course in input file: " + "<" + string + ">" + "\nExiting program...");
            System.exit(1);
            return null;
        }
        else {
        	return null;
        }
    }

    // getLab - Returns a lab object contained in the labs arraylist with the same
    // name as the input string
    // INPUT: Lab name string of form "<course.getName()> TUT <number>"
    // RETURNS: A lab object if one is found
    private static Lab getLab(String string, boolean strict) {
        for (Lab lab : labs) {
            if (lab.getName().equals(string)) {
                return lab;
            }
        }
        if(strict) {
            System.out.println("ERROR: Invalid lab in input file: " + "<" + string + ">" + "\nExiting program...");
            System.exit(1);
            return null;
        }
        else {
        	return null;
        }

    }

    // getCourseSlot - Returns a slot object contained in the courseSlots arraylist
    // with the same properties as the inputs
    // INPUT: the slot's day and time
    // RETURNS: A slot object from the courseSlots arraylists, if one is found
    private static Slot getCourseSlot(Day day, LocalTime time, boolean strict){
        for (Slot slot : courseSlots) {
            if (slot.getDay().equals(day) && slot.getStartTime().equals(time)) {
                return slot;
            }
        }
        if (strict) {
		    System.out.println("ERROR: Invalid course slot in input file! " + "<" + day + ", " + time + ">" + "\nExiting program...");
		    System.exit(1);
		    return null;
        }
        else {
        	return null;
        }
    }

    // getLabSlot - Returns a slot object contained in the labSlots arraylist with
    // the same properties as the inputs
    // INPUT: the slot's day and time
    // RETURNS: A slot object from the labSlots arraylists, if one is found
    private static Slot getLabSlot(Day day, LocalTime time, boolean strict) {
        for (Slot slot : labSlots) {
            if (slot.getDay().equals(day) && slot.getStartTime().equals(time)) {
                return slot;
            }
        }
        if(strict) {
	        System.out.println("ERROR: Invalid lab slot in input file! " + "<" + day + ", " + time + ">" + "\nExiting program...");
	        System.exit(1);
	        return null;
        }
        else {
        	return null;
        }
    }

    // isCourse - Returns if a given input string represents a course
    // INPUT: Element name string
    // RETURNS: true if Course, false if Lab or otherwise
    private static boolean isCourse(String[] string) {
        if (string[2].equals("TUT")) {
            return false;
        }
        if (string.length == 6) {
            return false;
        }
        if (string[2].equals("LEC")) {
            return true;
        }
        return false;
    }

    //
    // PARSE METHODS
    //

    // parseName - Parses input file for the name of the example
    // INPUT: None
    // RETURNS: A string representing the name of the example
    private static String parseName() throws Exception{
        Scanner scanner = createScanner();
        String name = "";
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equals("Name:") && scanner.hasNextLine()) {
                name = scanner.nextLine();
                break;
            }
        }
        if (name.equals("")) {
            System.out.println("ERROR: No name specified\nExiting program...");
            System.exit(1);
            return null;
        }
        scanner.close();
        return name;
    }

    // parseCourseSlots - Parses input file for course slots
    // INPUT: None
    // RETURNS: An arraylist of all course slots
    private static ArrayList<CourseSlot> parseCourseSlots() throws Exception {
        Scanner scanner = createScanner();
        String lineStr;
        ArrayList<CourseSlot> slots = new ArrayList<CourseSlot>();
        while (scanner.hasNextLine()) {

            if (scanner.nextLine().equals("Course slots:")) {

                lineStr = scanner.nextLine();
                while (lineStr.equals("") == false) {
                    lineStr = lineStr.replaceAll("\\s", "");
                    String[] parts = lineStr.split(",");
                    if (parts.length != 4) {
                        System.out.println("ERROR: Course slot with invalid number of parameters: " + "<" + lineStr + ">" + "\nExiting program...");
                        System.exit(1);
                    }
                    CourseSlot slot = new CourseSlot(getDay(parts[0]),
                            LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m")), Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]));
                    
                    if (ValidateInput.courseSlot(slot)) {
                        slots.add(slot);
                    }
                    else {
                    	System.out.println("ERROR: Invalid course slot: " + "<" + slot.getDay() + ", " + slot.getStartTime() + ">" + " Only course slots from the problem description are permitted!\nExiting program...");
                    	System.exit(1);
                    }
                    lineStr = scanner.nextLine();
                }
            }
        }
        scanner.close();
        return slots;
    }

    // parseLabSlots - Parses input file for lab slots
    // INPUT: None
    // RETURNS: An arraylist of all lab slots
    private static ArrayList<LabSlot> parseLabSlots() throws Exception {
        Scanner scanner = createScanner();
        String lineStr;
        ArrayList<LabSlot> slots = new ArrayList<LabSlot>();
        while (scanner.hasNextLine()) {

            if (scanner.nextLine().equals("Lab slots:")) {

                lineStr = scanner.nextLine();
                while (lineStr.equals("") == false) {
                    lineStr = lineStr.replaceAll("\\s", "");
                    String[] parts = lineStr.split(",");
                    if (parts.length != 4) {
                        System.out.println("ERROR: Lab slot with invalid number of parameters: " + "<" + lineStr + ">" + "\nExiting program...");
                        System.exit(1);
                    }

                    LabSlot slot = new LabSlot(getDay(parts[0]),
                            LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m")), Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]));

                    if (ValidateInput.labSlot(slot)) {
                        slots.add(slot);
                    }
                    else {
                    	System.out.println("ERROR: Invalid lab slot: " + "<" + slot.getDay() + ", " + slot.getStartTime() + ">" + " Only lab slots from the problem description are permitted!\nExiting program...");
                    	System.exit(1);
                    }
                    lineStr = scanner.nextLine();
                }
            }
        }
        scanner.close();
        return slots;
    }

    // parseCourses - Parses input file for courses
    // INPUT: None
    // RETURNS: An arraylist of all courses
    private static ArrayList<Course> parseCourses() throws Exception {
        Scanner scanner = createScanner();
        String lineStr;
        ArrayList<Course> slots = new ArrayList<Course>();
        while (scanner.hasNextLine()) {

            if (scanner.nextLine().equals("Courses:")) {

                lineStr = scanner.nextLine();
                while (lineStr.equals("") == false) {
                    lineStr = lineStr.trim().replaceAll(" +", " ");
                    String[] parts = lineStr.split(" ");
                    if (parts.length != 4) {
                    	System.out.println("ERROR: Course with invalid number of parameters: " + "<" + lineStr + ">" + "\nExiting program...");
                    	System.exit(1);
                    }
                    slots.add(new Course(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[3])));
                    lineStr = scanner.nextLine();
                }
            }
        }
        scanner.close();
        return slots;
    }

    // parseLabs - Parses input file for labs
    // INPUT: None
    // RETURNS: An arraylist of all labs
    private static ArrayList<Lab> parseLabs() throws Exception {
        Scanner scanner = createScanner();
        String lineStr;
        ArrayList<Lab> slots = new ArrayList<Lab>();
        while (scanner.hasNextLine()) {

            if (scanner.nextLine().equals("Labs:")) {

                lineStr = scanner.nextLine();
                while (lineStr.equals("") == false) {
                    lineStr = lineStr.trim().replaceAll(" +", " ");
                    String[] parts = lineStr.split(" ");
                    // Lab specific to a lecture section
                    if (parts.length == 6) {
                        Course course = getCourse(parts[0] + " " + parts[1] + " " + Integer.parseInt(parts[3]), true);
                        slots.add(new Lab(course, Integer.parseInt(parts[5]), false));
                    }
                    // Lab open to all lecture sections
                    else if (parts.length == 4) {
                        Course course = getCourse(parts[0] + " " + parts[1] + " " + 1, true);
                        slots.add(new Lab(course, Integer.parseInt(parts[3]), true));
                    } else {
                    	System.out.println("ERROR: Lab with invalid number of parameters: " + "<" + lineStr + ">" + "\nExiting program...");
                    	System.exit(1);
                    }
                    lineStr = scanner.nextLine();
                }
            }
        }
        scanner.close();
        return slots;
    }

    // parsePairsOrNotCompatible - Parses input file for tuples of elements from
    // either "Not compatible:" or "Pair:"
    // INPUT: Category to parse from
    // RETURNS: An arraylist of tuples of elements
    private static ArrayList<Element[]> parsePairsOrNotCompatible(String category) throws Exception {
        Scanner scanner = createScanner();
        String lineStr;
        ArrayList<Element[]> slots = new ArrayList<Element[]>();
        while (scanner.hasNextLine()) {

            if (scanner.nextLine().equals(category)) {

                lineStr = scanner.nextLine();
                while (lineStr.equals("") == false) {
                    // Split along comma in line
                    lineStr = lineStr.trim().replaceAll(" +", " ");
                    String[] parts = lineStr.split(", ");

                    // If not exactly two comma-seperated elements were found, invalid entry
                    if (parts.length != 2) {
                    	System.out.println("ERROR: Input line: " + "<" + lineStr + ">" + " is not a tuple of elements!" + "\nExiting program...");
                    	System.exit(1);
                    }

                    ArrayList<String[]> strings = new ArrayList<String[]>();
                    strings.add(parts[0].split(" "));
                    strings.add(parts[1].split(" "));
                    String[] str;
                    Element[] tuple = { null, null };

                    // For each element (2 elements) find its corrosponding course/lab object
                    for (int i = 0; i <= 1; i++) {
                        str = strings.get(i);
                        // Element is a lab
                        if (isCourse(str) == false) {
                            if (str.length == 6) {
                                tuple[i] = getLab(str[0] + " " + str[1] + " " + Integer.parseInt(str[3]) + " TUT "
                                        + Integer.parseInt(str[5]), false);
                            } else if (str.length == 4) {
                                tuple[i] = getLab(str[0] + " " + str[1] + " " + 1 + " TUT " + Integer.parseInt(str[3]), false);
                            }
                        }
                        // Element is a course
                        else if (str.length == 4) {
                            tuple[i] = getCourse(str[0] + " " + str[1] + " " + Integer.parseInt(str[3]), false);
                        }
                    }
                    slots.add(tuple);

                    lineStr = scanner.nextLine();
                }
            }
        }
        scanner.close();
        return slots;
    }

    // parseUnwantedOrPartialAssignments - Parses input file for either unwanted or
    // partial assignments
    // INPUT: Category to parse from
    // RETURNS: An arraylist of assignments
    public static ArrayList<Assignment> parseUnwantedOrPartialAssignments(String category) throws Exception {
        Scanner scanner = createScanner();
        String lineStr;
        ArrayList<Assignment> slots = new ArrayList<Assignment>();
        while (scanner.hasNextLine()) {

            if (scanner.nextLine().equals(category)) {
                try {
                    lineStr = scanner.nextLine();

                    while (lineStr.equals("") == false) {
                        // Split along comma in line
                        lineStr = lineStr.trim().replaceAll(" +", " ");
                        String[] parts = lineStr.split(", ");

                        // If not exactly three comma-seperated values were found, invalid entry
                        if (parts.length != 3) {
                        	System.out.println("ERROR: Input line: " + "<" + lineStr + ">" + " is not a comma-separated triple!" + "\nExiting program...");
                        	System.exit(1);
                        }

                        Day day = getDay(parts[1]);
                        LocalTime time = LocalTime.parse(parts[2], DateTimeFormatter.ofPattern("H:m"));
                        String[] elementStr = parts[0].split(" ");
                        Element element;

                        // Element is a course
                        if (isCourse(elementStr)) {
                            element = getCourse(
                                    elementStr[0] + " " + elementStr[1] + " " + Integer.parseInt(elementStr[3]), true);
                            slots.add(new Assignment(element, getCourseSlot(day, time, true)));
                        }
                        // Element is a lab
                        else {
                            if (elementStr.length == 6) {
                                element = getLab(elementStr[0] + " " + elementStr[1] + " "
                                        + Integer.parseInt(elementStr[3]) + " TUT " + Integer.parseInt(elementStr[5]), true);
                            } else if (elementStr.length == 4) {
                                element = getLab(elementStr[0] + " " + elementStr[1] + " " + 1 + " TUT "
                                        + Integer.parseInt(elementStr[3]), true);
                            } else {
                            	System.out.println("ERROR: Input line: " + "<" + lineStr + ">" + " is not a comma-separated triple!" + "\nExiting program...");
                            	System.exit(1);
                            	return null;
                            }
                            slots.add(new Assignment(element, getLabSlot(day, time, true)));
                        }

                        if (scanner.hasNextLine() == false) {
                            break;
                        }
                        lineStr = scanner.nextLine();
                    }
                } catch (Exception e) {
                    return slots;
                }
            }
        }
        scanner.close();
        return slots;
    }

    // parsePreferences - Parses input file for preferences
    // INPUT: None
    // RETURNS: An arraylist of preferences
    public static ArrayList<Preference> parsePreferences() throws Exception {
        Scanner scanner = createScanner();
        String lineStr;
        ArrayList<Preference> slots = new ArrayList<Preference>();
        while (scanner.hasNextLine()) {

            if (scanner.nextLine().equals("Preferences:")) {

                lineStr = scanner.nextLine();
                while (lineStr.equals("") == false) {
                    // Split along comma in line
                    lineStr = lineStr.trim().replaceAll(" +", " ");
                    String[] parts = lineStr.split(", ");

                    // If not exactly four comma-seperated values were found, invalid entry
                    if (parts.length != 4) {
                    	System.out.println("ERROR: Input line: " + "<" + lineStr + ">" + " is not 4 comma-separated values!" + "\nExiting program...");
                    	System.exit(1);
                    }

                    Day day = getDay(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m"));
                    String[] elementStr = parts[2].split(" ");
                    Element element;

                    // Element is a course
                    if (isCourse(elementStr)) {
                        element = getCourse(
                                elementStr[0] + " " + elementStr[1] + " " + Integer.parseInt(elementStr[3]), false);
                        if (getCourseSlot(day, time, false) != null && element != null) {
                        	slots.add(new Preference(element, getCourseSlot(day, time, false), Integer.parseInt(parts[3])));
                        }
                        else {
                        	System.out.println("WARNING: Invalid preference will not be considered: " + "<" + lineStr + ">");
                        }
                    }
                    // Element is a lab
                    else {
                        if (elementStr.length == 6) {
                            element = getLab(elementStr[0] + " " + elementStr[1] + " " + Integer.parseInt(elementStr[3])
                                    + " TUT " + Integer.parseInt(elementStr[5]), false);
                        } else if (elementStr.length == 4) {
                            element = getLab(elementStr[0] + " " + elementStr[1] + " " + 1 + " TUT "
                                    + Integer.parseInt(elementStr[3]), false);
                        } else {
                        	System.out.println("ERROR: Input line: " + "<" + lineStr + ">" + " is not 4 comma-separated values!" + "\nExiting program...");
                        	System.exit(1);
                        	return null;
                        }
                        
                        if (getLabSlot(day, time, false) != null && element != null) {
                        	slots.add(new Preference(element, getLabSlot(day, time, false), Integer.parseInt(parts[3])));
                        }
                        else {
                        	System.out.println("WARNING: Invalid preference will not be considered: " + "<" + lineStr + ">");
                        }
                    
                    }

                    lineStr = scanner.nextLine();
                }
            }
        }
        scanner.close();
        return slots;
    }

    // parseFile - Main parse method. Invokes sub-parse methods to populate global
    // vars with data from input file
    // INPUT: A string representing the local path of a .txt file that should
    // contain valid input for the system
    // RETURNS: None
    public static void parseFile(String path) throws Exception {
    	System.out.println("-----------------------------------------------");
    	System.out.println("Beginning input file parsing...");
        inputFile = new File(path);
        name = parseName();
        courseSlots = parseCourseSlots();
        labSlots = parseLabSlots();
        courses = parseCourses();
        labs = parseLabs();
        notCompatible = parsePairsOrNotCompatible("Not compatible:");
        pairs = parsePairsOrNotCompatible("Pair:");
        unwanted = parseUnwantedOrPartialAssignments("Unwanted:");
        partialAssignments = parseUnwantedOrPartialAssignments("Partial assignments:");
        preferences = parsePreferences();
        
        makeSpecialCourseNotCompatibles("CPSC", 813, "CPSC", 313);
        makeSpecialCourseNotCompatibles("CPSC", 913, "CPSC", 413);
        
        System.out.println("Input file parsing complete!");
        System.out.println("-----------------------------------------------");

    }

    //
    // GETTERS
    //

    public static String getName() {
        return name;
    }

    public static ArrayList<CourseSlot> getCourseSlots() {
        return courseSlots;
    }

    public static ArrayList<LabSlot> getlabSlots() {
        return labSlots;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static ArrayList<Lab> getLabs() {
        return labs;
    }

    public static ArrayList<Element[]> getNotCompatible() {
        return notCompatible;
    }

    public static ArrayList<Element[]> getPairs() {
        return pairs;
    }

    public static ArrayList<Assignment> getUnwanted() {
        return unwanted;
    }

    public static ArrayList<Assignment> getPartialAssignments() {
        return partialAssignments;
    }

    public static ArrayList<Preference> getPreferences() {
        return preferences;
    }

    
    //
    // SPECIAL CASE HANDLERS
    //
    
    
    // getNotCompatibleWithCourse - Given a course, returns all elements that are not compatible with that course and its labs
    // INPUT: Course number (ex. 313, 413, etc)
    // RETURNS: Returns an arraylist of elements that are incompatible with the input course
    private static ArrayList<Element> getNotCompatibleWithElement(Element course) {
        ArrayList<Element> incompatibles = new ArrayList<Element>();

        for (Element[] tuple : notCompatible) {
            Element element = tuple[0];
            if (element.getName().equals(course.getName())) {
                incompatibles.add(tuple[1]);
            }
            element = tuple[1];
            if (element.getName().equals(course.getName())) {
                incompatibles.add(tuple[0]);
            }
        }
        return incompatibles;
    }

    
    //makeSpecialCourseNotCompatibles - Given a special course and other course, updates notCompatible with these two being incompatible
    	// As well as anything incompatible with other becoming incompatible with special as well
    //INPUT: special course department, special course number, other course department, other course number
    //RETURNS: None. Modifies notCompatible arraylist during execution
    private static void makeSpecialCourseNotCompatibles(String specialDepartment, int specialNum, String otherDepartment, int otherNum) {
    	boolean noteHeader = true;
    	ArrayList<Element> coursesAndLabs = new ArrayList<Element>();
    	coursesAndLabs.addAll(courses);
    	coursesAndLabs.addAll(labs);
    	for (Element special : coursesAndLabs) {
    		boolean isTarget = false;
    		//Special is a course
    		if (special.getType() == 0) {
    			Course specialC = (Course)special;
    			if(specialC.getNumber() == specialNum && specialC.getDepartment().equals(specialDepartment)) {
    				isTarget = true;
    			}
    		}
    		if (special.getType() == 1) {
    			Lab specialL = (Lab)special;
    			if(specialL.getCourse().getNumber() == specialNum && specialL.getDepartment().equals(specialDepartment)) {
    				isTarget = true;
    			}
    		}
    		//Found element that matches the special department and number
    		if(isTarget) {
    			for(Course other : courses) {
    				if(other.getNumber() == otherNum && other.getDepartment().equals(otherDepartment)) { //Find other course object
    					if(noteHeader) {
    						System.out.println("NOTE: Courses prohibited to overlap with " + specialDepartment + " " + specialNum + " found. Adding the following to \"Not compatible\": ");
    						noteHeader = false;
    					}
    					//Add all elements not compatible with other
    					for(Element e : getNotCompatibleWithElement(other)) {
    						if (!(e.getName().split(" ")[0].equals(specialDepartment) && e.getName().split(" ")[1].equals(String.valueOf(specialNum)))) {
	    						Element[] tuple1 = {special, e};
	    						notCompatible.add(tuple1);
	    						System.out.println("- (" + tuple1[0].getName() + ", " + tuple1[1].getName() + ")");
    						}
    					}
    					
    					//Add special and any labs of other to notCompatible
    					for(Lab l : labs) {
    						if(l.getDepartment().equals(other.getDepartment()) && l.getCourse().getNumber() == other.getNumber() && l.getCourse().getSection() == other.getSection()) {
    							Element[] tuple2 = {special, l};
    							notCompatible.add(tuple2);
    							System.out.println("- (" + tuple2[0].getName() + ", " + tuple2[1].getName() + ")");
    							//Add all elements not compatible with lab of other
    	    					for(Element e : getNotCompatibleWithElement(l)) {
    	    						if (!(e.getName().split(" ")[0].equals(specialDepartment) && e.getName().split(" ")[1].equals(String.valueOf(specialNum)))) {
        	    						Element[] tuple1 = {special, e};
        	    						notCompatible.add(tuple1);
        	    						System.out.println("- (" + tuple1[0].getName() + ", " + tuple1[1].getName() + ")");
    	    						}
    	    					}
    						}
    					}
    					
    					//Add special and other to notCompatible (done after everything else is added)
    					Element[] tuple3 = {special, other};
    					System.out.println("- (" + tuple3[0].getName() + ", " + tuple3[1].getName() + ")");
    					notCompatible.add(tuple3);
    				}
    			}
    		}
    	}
    }
    
    
    //
    //DEBUG/DISPLAY METHODS
    //
    
    // printParsedInput - Loosely echos the input file using the data stored by
    // Parser
    // INPUT: None
    // RETURNS: None
    public static void printParsedInput() {
        // Name
        System.out.println("Name: ");
        System.out.println(getName());

        // Course Slots
        System.out.println("\nCourse slots: ");
        for (CourseSlot e : getCourseSlots()) {
            System.out.println(e.getInfo() + ", " + e.getMax() + ", " + e.getMin());
        }

        // Lab Slots
        System.out.println("\nLab slots: ");
        for (LabSlot e : getlabSlots()) {
            System.out.println(e.getInfo() + ", " + e.getMax() + ", " + e.getMin());
        }

        // Courses
        System.out.println("\nCourses: ");
        for (Course e : getCourses()) {
            System.out.println(e.getName());
        }

        // Labs
        System.out.println("\nLabs: ");
        for (Lab e : getLabs()) {
            System.out.println(e.getName());
        }

        // Not compatible
        System.out.println("\nNot compatible: ");
        for (Element[] e : getNotCompatible()) {
            System.out.println(e[0].getName() + ", " + e[1].getName());
        }

        // Unwanted
        System.out.println("\nUnwanted: ");
        for (Assignment e : getUnwanted()) {
            System.out.println(e.getElement().getName() + ", " + e.getSlot().getInfo());
        }

        // Preferences
        System.out.println("\nPreferences: ");
        for (Preference e : getPreferences()) {
            System.out.println(e.getElement().getName() + ", " + e.getSlot().getInfo() + ", " + e.getWeight());
        }

        // Pair
        System.out.println("\nPair: ");
        for (Element[] e : getPairs()) {
            System.out.println(e[0].getName() + ", " + e[1].getName());
        }

        // Partial Assignments
        System.out.println("\nPartial Assignment: ");
        for (Assignment e : getPartialAssignments()) {
            System.out.println(e.getElement().getName() + ", " + e.getSlot().getInfo());
        }

        System.out.println("-----------------------------------------------");
    }
}