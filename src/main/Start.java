package main;

import java.io.FileNotFoundException;

class Start {
    public static void main(String args[]) throws Exception {
    	if (args.length == 0) {
    		throw new FileNotFoundException("Please provide the path of a valid input file.");
    	}
    	Parser parser = new Parser(args[0]);
    	parser.parseName();
    }
}