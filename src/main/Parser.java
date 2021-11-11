package main;

import java.util.ArrayList;
import main.problem.*;

public class Parser {
	//IDEA: have functions to return each type of data present in the input file. ie. slots, course identifiers, pairs, etc.
	//Each return should be of a data type that coincides with the object being represented.
	
	public ArrayList<String> parseName() {
		return null;
	}
	
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
