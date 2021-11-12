package main;

import problem.Assignment;
import problem.Element;
import problem.Slot;
import java.util.ArrayList;

public class Constr {

    private boolean constraintsMet;
    Element myElem;
    Slot mySlot;
    ArrayList<Assignment> myAssign = new ArrayList<Assignment>();

    //for local testing
    public static void main(String[] args){

    }

    public Constr(){

    }

    public boolean checkConstraints(ArrayList<Assignment> assignments) {

        constraintsMet = false;

        for(Assignment a : assignments){
            a.getSlot();
        }

        return constraintsMet;
    }

    public boolean checkPartialConstraints(ArrayList<Assignment> assignments) {

        constraintsMet = false;


        return constraintsMet;
    }
}