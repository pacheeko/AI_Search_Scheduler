package main;

import problem.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.*;
import java.util.Scanner;

public class Parser {
	//IDEA: have functions to return each type of data present in the input file. ie. slots, course identifiers, pairs, etc.
	//Each return should be of a data type that coincides with the object being represented.
	
	//
	//GLOBAL VARIABLES
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
	//UTILITY METHODS
	//
	
	//createScanner - Creates a new scanner to parse an input file
	//INPUT: None
	//RETURNS: A scanner object
	private static Scanner createScanner() throws Exception {
	    Scanner scanner = new Scanner(inputFile);
	    scanner.useDelimiter(",");
		return scanner;
	}
	
	//getDay - Returns the enum type of a day, given a matching string type
	//INPUT: String representing abbreviated day
	//RETURNS: A Day enum type
	private static Day getDay(String string) throws Exception{
		if (string.equals("MO")) {
			return Day.MO;
		}
		if (string.equals("TU")) {
			return Day.TU;
		}
		if (string.equals("FR")) {
			return Day.FR;
		}
		throw new Exception("Invalid day in input file!");
	}
	
	//getCourse - Returns a course object contained in the courses arraylist with the same name as the input string
	//INPUT: Course name string of form "<department> <number> <section>"
	//RETURNS: A course object if one is found
	private static Course getCourse(String string) throws Exception {
		for (Course course : courses) {
			if(course.getName().equals(string)){
				return course;
			}
		}
		throw new Exception("Invalid course in input file!");
	}
	
	//getLab - Returns a lab object contained in the labs arraylist with the same name as the input string
	//INPUT: Lab name string of form "<course.getName()> TUT <number>"
	//RETURNS: A lab object if one is found
	private static Lab getLab(String string) throws Exception {
		for (Lab lab : labs) {
			if(lab.getName().equals(string)){
				return lab;
			}
		}
		throw new Exception("Invalid lab in input file!");
	}
	
	//getCourseSlot - Returns a slot object contained in the courseSlots arraylist with the same properties as the inputs
	//INPUT: the slot's day and time
	//RETURNS: A slot object from the courseSlots arraylists, if one is found
	private static Slot getCourseSlot(Day day, LocalTime time) throws Exception {
		for (Slot slot : courseSlots) {
			if(slot.getDay().equals(day) && slot.getStartTime().equals(time)){
				return slot;
			}
		}
		throw new Exception("Invalid course slot in input file!");
	}
	
	//getLabSlot - Returns a slot object contained in the labSlots arraylist with the same properties as the inputs
	//INPUT: the slot's day and time
	//RETURNS: A slot object from the labSlots arraylists, if one is found
	private static Slot getLabSlot(Day day, LocalTime time) throws Exception {
		for (Slot slot : labSlots) {
			if(slot.getDay().equals(day) && slot.getStartTime().equals(time)){
				return slot;
			}
		}
		throw new Exception("Invalid lab slot in input file!");
	}
	
	//isCourse - Returns if a given input string represents a course
	//INPUT: Element name string
	//RETURNS: true if Course, false if Lab or otherwise
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
	//PARSE METHODS
	//
	
	//parseName - Parses input file for the name of the example
	//INPUT: None
	//RETURNS: A string representing the name of the example
	private static String parseName() throws Exception {
		Scanner scanner = createScanner();
		String name = "";
		while (scanner.hasNextLine()) {
			if (scanner.nextLine().equals("Name:") && scanner.hasNextLine()) {
				name = scanner.nextLine();
				break;
			}
		}
		if (name.equals("")) {
			throw new Exception("ERROR: No name specified!");
		}
		scanner.close();
		return name;
	}
	
	//parseCourseSlots - Parses input file for course slots
	//INPUT: None
	//RETURNS: An arraylist of all course slots
	private static ArrayList<CourseSlot> parseCourseSlots() throws Exception {
		Scanner scanner = createScanner();
		String lineStr;
		ArrayList<CourseSlot> slots = new ArrayList<CourseSlot>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Course slots:")) {
				
				lineStr = scanner.nextLine();
				while(lineStr.equals("") == false) {
					lineStr = lineStr.replaceAll("\\s","");
					String[] parts = lineStr.split(",");
					if(parts.length != 4) {
						throw new Exception("Invalid course slot in input file!");
					}
					slots.add(new CourseSlot(getDay(parts[0]), LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m")), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
					lineStr = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	//parseLabSlots - Parses input file for lab slots
	//INPUT: None
	//RETURNS: An arraylist of all lab slots
	private static ArrayList<LabSlot> parseLabSlots() throws Exception {
		Scanner scanner = createScanner();
		String lineStr;
		ArrayList<LabSlot> slots = new ArrayList<LabSlot>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Lab slots:")) {
				
				lineStr = scanner.nextLine();
				while(lineStr.equals("") == false) {
					lineStr = lineStr.replaceAll("\\s","");
					String[] parts = lineStr.split(",");
					if(parts.length != 4) {
						throw new Exception("Invalid lab slot in input file!");
					}
					slots.add(new LabSlot(getDay(parts[0]), LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m")), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
					lineStr = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	//parseCourses - Parses input file for courses
	//INPUT: None
	//RETURNS: An arraylist of all courses
	private static ArrayList<Course> parseCourses() throws Exception {
		Scanner scanner = createScanner();
		String lineStr;
		ArrayList<Course> slots = new ArrayList<Course>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Courses:")) {
				
				lineStr = scanner.nextLine();
				while(lineStr.equals("") == false) {
					lineStr = lineStr.trim().replaceAll(" +", " ");
					String[] parts = lineStr.split(" ");
					if(parts.length != 4) {
						throw new Exception("Invalid course in input file!");
					}
					slots.add(new Course(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[3])));
					lineStr = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	//parseLabs - Parses input file for labs
	//INPUT: None
	//RETURNS: An arraylist of all labs
	private static ArrayList<Lab> parseLabs() throws Exception {
		Scanner scanner = createScanner();
		String lineStr;
		ArrayList<Lab> slots = new ArrayList<Lab>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Labs:")) {
				
				lineStr = scanner.nextLine();
				while(lineStr.equals("") == false) {
					lineStr = lineStr.trim().replaceAll(" +", " ");
					String[] parts = lineStr.split(" ");
					//Lab specific to a lecture section
					if(parts.length == 6) {
						Course course = getCourse(parts[0] + " " + parts[1] + " " + Integer.parseInt(parts[3]));
						slots.add(new Lab(course, Integer.parseInt(parts[5]), false));
					}
					//Lab open to all lecture sections
					else if (parts.length == 4){
						Course course = getCourse(parts[0] + " " + parts[1] + " " + 1);
						slots.add(new Lab(course, Integer.parseInt(parts[3]), true));
					}
					else {
						throw new Exception("Invalid lab in input file!");
					}
					lineStr = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	//parsePairsOrNotCompatible - Parses input file for tuples of elements from either "Not compatible:" or "Pair:"
	//INPUT: Category to parse from
	//RETURNS: An arraylist of tuples of elements
	private static ArrayList<Element[]> parsePairsOrNotCompatible(String category) throws Exception{
		Scanner scanner = createScanner();
		String lineStr;
		ArrayList<Element[]> slots = new ArrayList<Element[]>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals(category)) {
				
				lineStr = scanner.nextLine();
				while(lineStr.equals("") == false) {
					//Split along comma in line
					lineStr = lineStr.trim().replaceAll(" +", " ");
					String[] parts = lineStr.split(", ");
					
					//If not exactly two comma-seperated elements were found, invalid entry
					if (parts.length != 2) {
						throw new Exception("Invalid \"not compatible\" or \"pair\" in input file!");
					}
					
					ArrayList<String[]> strings = new ArrayList<String[]>();
					strings.add(parts[0].split(" "));
					strings.add(parts[1].split(" "));
					String[] str;
					Element[] tuple = {null, null};
					
					//For each element (2 elements) find its corrosponding course/lab object
					for (int i = 0; i <= 1; i++) {
						str = strings.get(i);
						//Element is a lab
						if (isCourse(str) == false) {
							if (str.length == 6) {
								tuple[i] = getLab(str[0] + " " + str[1] + " " + Integer.parseInt(str[3])  + " TUT " + Integer.parseInt(str[5]));
							}
							else if (str.length == 4) {
								tuple[i] = getLab(str[0] + " " + str[1] + " " + 1 + " TUT " + Integer.parseInt(str[3]));
							}
						}
						//Element is a course
						else if (str.length == 4) {
							tuple[i] = getCourse(str[0] + " " + str[1] + " " + Integer.parseInt(str[3]));
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
	
	//parseUnwantedOrPartialAssignments - Parses input file for either unwanted or partial assignments
	//INPUT: Category to parse from
	//RETURNS: An arraylist of assignments
	public static ArrayList<Assignment> parseUnwantedOrPartialAssignments(String category) throws Exception{
		Scanner scanner = createScanner();
		String lineStr;
		ArrayList<Assignment> slots = new ArrayList<Assignment>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals(category)) {
				
				lineStr = scanner.nextLine();
				while(lineStr.equals("") == false) {
					//Split along comma in line
					lineStr = lineStr.trim().replaceAll(" +", " ");
					String[] parts = lineStr.split(", ");
					
					//If not exactly three comma-seperated values were found, invalid entry
					if (parts.length != 3) {
						throw new Exception("Invalid unwanted or partial assignment in input file!");
					}
					
					Day day = getDay(parts[1]);
					LocalTime time = LocalTime.parse(parts[2], DateTimeFormatter.ofPattern("H:m"));
					String[] elementStr = parts[0].split(" ");
					Element element;
					
					//Element is a course
					if (isCourse(elementStr)){
						element = getCourse(elementStr[0] + " " + elementStr[1] + " " + Integer.parseInt(elementStr[3]));
						slots.add(new Assignment(element, getCourseSlot(day, time)));
					}
					//Element is a lab
					else {
						if (elementStr.length == 6) {
							element = getLab(elementStr[0] + " " + elementStr[1] + " " + Integer.parseInt(elementStr[3])  + " TUT " + Integer.parseInt(elementStr[5]));
						}
						else if (elementStr.length == 4) {
							element = getLab(elementStr[0] + " " + elementStr[1] + " " + 1 + " TUT " + Integer.parseInt(elementStr[3]));
						}
						else {
							throw new Exception("Invalid unwanted or partial assignment in input file!");
						}
						slots.add(new Assignment(element, getLabSlot(day, time)));
					}
					
					if(scanner.hasNextLine() == false) {
						break;
					}
					lineStr = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	
	
	//parsePreferences - Parses input file for preferences
	//INPUT: None
	//RETURNS: An arraylist of preferences
	public static ArrayList<Preference> parsePreferences() throws Exception{
		Scanner scanner = createScanner();
		String lineStr;
		ArrayList<Preference> slots = new ArrayList<Preference>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Preferences:")) {
				
				lineStr = scanner.nextLine();
				while(lineStr.equals("") == false) {
					//Split along comma in line
					lineStr = lineStr.trim().replaceAll(" +", " ");
					String[] parts = lineStr.split(", ");
					
					//If not exactly three comma-seperated values were found, invalid entry
					if (parts.length != 4) {
						throw new Exception("Invalid preference in input file!");
					}
					
					Day day = getDay(parts[0]);
					LocalTime time = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m"));
					String[] elementStr = parts[2].split(" ");
					Element element;
					
					//Element is a course
					if (isCourse(elementStr)){
						element = getCourse(elementStr[0] + " " + elementStr[1] + " " + Integer.parseInt(elementStr[3]));
						try {
							slots.add(new Preference(element, getCourseSlot(day, time), Integer.parseInt(parts[3])));
						}
						catch(Exception e) {
							System.out.println("WARNING: Preference with invalid course slot: " + day + " at " + time + " will not be considered!");
						}
					}
					//Element is a lab 
					else {
						if (elementStr.length == 6) {
							element = getLab(elementStr[0] + " " + elementStr[1] + " " + Integer.parseInt(elementStr[3])  + " TUT " + Integer.parseInt(elementStr[5]));
						}
						else if (elementStr.length == 4) {
							element = getLab(elementStr[0] + " " + elementStr[1] + " " + 1 + " TUT " + Integer.parseInt(elementStr[3]));
						}
						else {
							throw new Exception("Invalid preference in input file!");
						}
						try {
							slots.add(new Preference(element, getLabSlot(day, time), Integer.parseInt(parts[3])));
						}
						catch(Exception e) {
							System.out.println("WARNING: Preference with invalid lab slot: " + day + " at " + time + " will not be considered!");
						}
					}

					lineStr = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	
	
	//parseFile - Main parse method. Invokes sub-parse methods to populate global vars with data from input file
	//INPUT: A string representing the local path of a .txt file that should contain valid input for the system
	//RETURNS: None
	public static void parseFile(String path) throws Exception {
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
    	
	}
	
	
	//
	//GETTERS
	//
	
	public static String getName() {
		return name;
	}
	
	public static ArrayList<CourseSlot> getCourseSlots(){
		return courseSlots;
	}
	
	public static ArrayList<LabSlot> getlabSlots(){
		return labSlots;
	}
	
	public static ArrayList<Course> getCourses(){
		return courses;
	}
	
	public static ArrayList<Lab> getLabs(){
		return labs;
	}
	
	public static ArrayList<Element[]> getNotCompatible(){
		return notCompatible;
	}
	
	public static ArrayList<Element[]> getPairs(){
		return pairs;
	}
	
	public static ArrayList<Assignment> getUnwanted(){
		return unwanted;
	}
	
	public static ArrayList<Assignment> getPartialAssignments(){
		return partialAssignments;
	}
	
	public static ArrayList<Preference> getPreferences(){
		return preferences;
	}
	
	//getNotCompatibleWithCourse - Given a course number, returns all elements that are not compatible with this course and its labs
	//INPUT: Course number (ex. 313, 413, etc)
	//RETURNS: Returns an arraylist of elements that are incompatible with the input course
	public static ArrayList<Element> getNotCompatibleWithCourse(String course){
		ArrayList<Element> incompatibles = new ArrayList<Element>();
		
		for (Element[] tuple : notCompatible) {
			String courseNum = tuple[0].getName().split(" ")[1];
			if(courseNum.equals(course)) {
				incompatibles.add(tuple[1]);
			}
			courseNum = tuple[1].getName().split(" ")[1];
			if(courseNum.equals(course)) {
				incompatibles.add(tuple[0]);
			}
		}
		return incompatibles;
	}
}
