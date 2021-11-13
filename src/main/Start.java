package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import problem.*;

class Start {
    public static void main(String args[]) throws Exception {
    	//Check if file path was passed
    	if (args.length == 0) {
    		throw new FileNotFoundException("Please provide the path of a valid input file.");
    	}
    	//Instantiate Parser
    	Parser parser = new Parser(args[0]);
    	
    	//Parse and store all information from input file
    	String name = parser.parseName();
    	ArrayList<CourseSlot> courseSlots = parser.parseCourseSlots();
    }
}