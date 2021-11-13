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
    	//Instantiate Parser, parse file passed in arg[0]
    	Parser parser = new Parser();
    	parser.parseFile(args[0]);
    	
    }
}