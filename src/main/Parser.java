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
	
	//Define global variables
	private File inputFile;
	
	//Parser - Constructor for a new Parser
	//INPUT: input: The file to parse
	//RETURNS: None
	public Parser(String input) throws Exception {
		inputFile = new File(input);
	}
	
	//createScanner - Creates a new scanner to parse an input file
	//INPUT: None
	//RETURNS: A scanner object
	private Scanner createScanner() throws Exception {
	    Scanner scanner = new Scanner(inputFile);
	    scanner.useDelimiter(",");
		return scanner;
	}
	
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
	
	//parseName - Parses input file for the name of the example
	//INPUT: None
	//RETURNS: A string representing the name of the example
	public String parseName() throws Exception {
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
	public ArrayList<CourseSlot> parseCourseSlots() throws Exception {
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
		return slots;
	}
	
	public ArrayList<LabSlot> parseLabSlots() {
		return null;
	}
	
	public ArrayList<Course> parseCourses() {
		return null;
	}
	
	public ArrayList<Lab> parseLabs() {
		return null;
	}
	
	public ArrayList<Element[]> parseNotCompatible(){
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
	
	
	public ArrayList<Element[]> parsePair(){
		return null;
	}
	
	/*
	public (Element, Slot) parsePartialAssignments(){
		return null;
	}
	*/
	
}
