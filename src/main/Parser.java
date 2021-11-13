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
	private File inputFile;
	private String name;
	private ArrayList<CourseSlot> courseSlots;
	private ArrayList<LabSlot> labSlots;
	private ArrayList<Course> courses;
	private ArrayList<Lab> labs;
	
	
	//
	//UTILITY METHODS
	//
	
	//createScanner - Creates a new scanner to parse an input file
	//INPUT: None
	//RETURNS: A scanner object
	private Scanner createScanner() throws Exception {
	    Scanner scanner = new Scanner(inputFile);
	    scanner.useDelimiter(",");
		return scanner;
	}
	
	//getDay - Returns the enum type of a day, given a matching string type
	//INPUT: String representing abbreviated day
	//RETURNS: A Day enum type
	private Day getDay(String string) throws Exception{
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
	private Course getCourse(String string) throws Exception {
		for (Course course : courses) {
			if(course.getName().equals(string)){
				return course;
			}
		}
		throw new Exception("Invalid course in input file!");
	}
	
	
	//
	//PARSE METHODS
	//
	
	//parseName - Parses input file for the name of the example
	//INPUT: None
	//RETURNS: A string representing the name of the example
	private String parseName() throws Exception {
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
	private ArrayList<CourseSlot> parseCourseSlots() throws Exception {
		Scanner scanner = createScanner();
		String slotString;
		ArrayList<CourseSlot> slots = new ArrayList<CourseSlot>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Course slots:")) {
				
				slotString = scanner.nextLine();
				while(slotString.equals("") == false) {
					slotString = slotString.replaceAll("\\s","");
					String[] parts = slotString.split(",");
					if(parts.length != 4) {
						throw new Exception("Invalid course slot in input file!");
					}
					slots.add(new CourseSlot(getDay(parts[0]), LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m")), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
					slotString = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	//parseLabSlots - Parses input file for lab slots
	//INPUT: None
	//RETURNS: An arraylist of all lab slots
	private ArrayList<LabSlot> parseLabSlots() throws Exception {
		Scanner scanner = createScanner();
		String slotString;
		ArrayList<LabSlot> slots = new ArrayList<LabSlot>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Lab slots:")) {
				
				slotString = scanner.nextLine();
				while(slotString.equals("") == false) {
					slotString = slotString.replaceAll("\\s","");
					String[] parts = slotString.split(",");
					if(parts.length != 4) {
						throw new Exception("Invalid lab slot in input file!");
					}
					slots.add(new LabSlot(getDay(parts[0]), LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:m")), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
					slotString = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	//parseCourses - Parses input file for courses
	//INPUT: None
	//RETURNS: An arraylist of all courses
	private ArrayList<Course> parseCourses() throws Exception {
		Scanner scanner = createScanner();
		String slotString;
		ArrayList<Course> slots = new ArrayList<Course>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Courses:")) {
				
				slotString = scanner.nextLine();
				while(slotString.equals("") == false) {
					slotString = slotString.trim().replaceAll(" +", " ");
					String[] parts = slotString.split(" ");
					if(parts.length != 4) {
						throw new Exception("Invalid course in input file!");
					}
					slots.add(new Course(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[3])));
					slotString = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	//parseCourses - Parses input file for labs
	//INPUT: None
	//RETURNS: An arraylist of all labs
	private ArrayList<Lab> parseLabs() throws Exception {
		Scanner scanner = createScanner();
		String slotString;
		ArrayList<Lab> slots = new ArrayList<Lab>();
		while(scanner.hasNextLine()) {
			
			if(scanner.nextLine().equals("Labs:")) {
				
				slotString = scanner.nextLine();
				while(slotString.equals("") == false) {
					slotString = slotString.trim().replaceAll(" +", " ");
					String[] parts = slotString.split(" ");
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
					slotString = scanner.nextLine();
				}
			}
		}
		scanner.close();
		return slots;
	}
	
	private ArrayList<Element[]> parseNotCompatible(){
		return null;
	}
	
	/*
	public (Element, Slot) parseUnwanted(){
		return null;
	}
	*/
	
	/*
	public (Slot, Element, int) parsePreferences(){
		return null;
	}
	*/
	
	
	private ArrayList<Element[]> parsePair(){
		return null;
	}
	
	/*
	public (Element, Slot) parsePartialAssignments(){
		return null;
	}
	*/
	
	//parseFile - Main parse method. Invokes sub-parse methods to populate global vars with data from input file
	//INPUT: A string representing the local path of a .txt file that should contain valid input for the system
	//RETURNS: None
	public void parseFile(String path) throws Exception {
		inputFile = new File(path);
    	name = parseName();
    	courseSlots = parseCourseSlots();
    	labSlots = parseLabSlots();
    	courses = parseCourses();
    	labs = parseLabs();
	}
	
	
	//
	//GETTERS
	//
	
	public String getName() {
		return name;
	}
	
	public ArrayList<CourseSlot> getCourseSlots(){
		return courseSlots;
	}
	
	public ArrayList<LabSlot> getlabSlots(){
		return labSlots;
	}
	
	public ArrayList<Course> getCourses(){
		return courses;
	}
	
	public ArrayList<Lab> getLabs(){
		return labs;
	}
}
