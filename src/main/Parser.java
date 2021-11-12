package main;

import problem.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
	//IDEA: have functions to return each type of data present in the input file. ie. slots, course identifiers, pairs, etc.
	//Each return should be of a data type that coincides with the object being represented.
	
	//Define global variables
	File input;
	Scanner scanner;
	
	//Parser - Constructor for a new Parser
	//INPUT: inputFile: represents the local path to a .txt file containing the example input
	//RETURNS: None
	public Parser(String inputFile) throws Exception {
		input = new File(inputFile);
	    scanner = new Scanner(input);
	    scanner.useDelimiter(",");
	}
	
	//parseName - Parses input file for the name of the example
	//INPUT: None
	//RETURNS: A string representing the name of the example
	public String parseName() throws Exception {
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
		return name;
	}
	
	//parseCourseSlots - Parses input file for course slots
	//INPUT: None
	//RETURNS: An arraylist of all course slots
	public ArrayList<CourseSlot> parseCourseSlots() {
		return null;
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
