package main;

import problem.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;

public class Constr {

    private boolean DEBUG = true;

    private boolean constraintsMet;
    private ArrayList<Assignment> myAssignments;
    private ArrayList<Element> allElements = new ArrayList<>();
    private ArrayList<Element> elementCounter;

    //avoid checking constr of assignment multiple times
    private ArrayList<Assignment> checkedCourseMax;
    private ArrayList<Assignment> checkedLabMax;
    private ArrayList<Assignment> checkedConflicts;
    private ArrayList<Assignment> checkedNumber;
    private boolean tuesdayCheck;
    private PrintStream output;

    //for testing only
    //should get this from parser object
    private ArrayList<Assignment> unwanted;
    private ArrayList<Element[]> notCompatible;

    public Constr(Parser p) {
        unwanted = p.getUnwanted();
        notCompatible = p.getNotCompatible();
        allElements.addAll(p.getCourses());
        allElements.addAll(p.getLabs());
    }

    public boolean checkConstraints(ArrayList<Assignment> assignments, int assignmentNumber) {

        elementCounter = new ArrayList<>();

        try{
            if (DEBUG)
                output = new PrintStream("constr_log_" + assignmentNumber +".txt");
            constraintsMet = checkPartialConstraints(assignments, assignmentNumber, true);

            if(constraintsMet){
                if(DEBUG)
                    output.println("Checking if all courses and labs have been assigned...");
                for(Element e : allElements) {
                    if(!elementCounter.contains(e)){
                        if(DEBUG) {
                            output.println("    Constraint Not Met: All courses and labs have not been assigned\n");
                            output.flush();
                        }
                        return false;
                    }
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        if(DEBUG && constraintsMet) {
            output.println("All Hard Constraints Have Been Met\n");
            output.flush();
        }

        return constraintsMet;
    }

    public boolean checkPartialConstraints(ArrayList<Assignment> assignments, int assignmentNumber, boolean count) {

        try{
            if(DEBUG){
                if(!count)
                    output = new PrintStream("constr_log_" + assignmentNumber +".txt");
            }

            constraintsMet = true;
            tuesdayCheck = false;
            checkedCourseMax = new ArrayList<>();
            checkedLabMax = new ArrayList<>();
            checkedConflicts = new ArrayList<>();
            checkedNumber = new ArrayList<>();
            myAssignments = assignments;

            for(Assignment a : myAssignments){
                if(count){
                    Element myElement = a.getElement();
                    if(!elementCounter.contains(myElement)){
                        elementCounter.add(myElement);
                    }
                }
                int numberOfChecks = 4;
                for(int i = 0; i < numberOfChecks; i++){
                    for(Assignment u : unwanted){
                        if(a.getElement().equals(u.getElement()) && a.getSlot().equals(u.getSlot())){
                            if(DEBUG){
                                output.println("    Constraint Not Met: Unwanted Assignment");
                                output.println("    The element " + a.getElement().getName() + " is assigned to the slot "
                                        + a.getSlot().getInfo() + ", but this assignment is in the unwanted list");
                                output.flush();
                                return false;
                            }
                        }
                    }
                    performCheck(a, i);
                    if(!constraintsMet){
                        if(DEBUG)
                            output.flush();
                        return false;
                    }
                }
            }
            if(DEBUG) {
                output.println("All Partial Hard Constraints Have Been Met\n");
                if(!count)
                    output.flush();
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        checkedCourseMax = null;
        checkedLabMax = null;
        checkedConflicts = null;
        checkedNumber = null;

        return constraintsMet;
    }

    private void performCheck(Assignment assign, int check){

        Slot slot = assign.getSlot();
        String name = slot.getInfo();
        int slotType = slot.getType();

        switch(check){
            //courses on tuesday at 11
            case 0:{
                if(!tuesdayCheck) {
                    if (slotType == 0) {
                        if (DEBUG)
                            output.println("Checking for courses scheduled on Tuesday at 11:00...");
                        constraintsMet = checkTuesdayCourses(assign);
                        tuesdayCheck = true;
                    }
                }
            }
            break;
            //course max
            case 1:{
                if(slotType == 0){
                    if(!checkedCourseMax.contains(assign)){
                        if(DEBUG)
                            output.println("Checking Course Max for" + name + "...");
                        constraintsMet = meetsCourseMax(assign);
                        checkedCourseMax.add(assign);
                    }
                }else{
                    if(!checkedLabMax.contains(assign)){
                        if(DEBUG)
                            output.println("Checking Lab Max for" + name + "...");
                        constraintsMet = meetsLabMax(assign);
                        checkedLabMax.add(assign);
                    }
                }
            }
            break;
            //labs and courses in same slot
            case 2:{
                if (slotType == 0) {
                    if(!checkedConflicts.contains(assign)){
                        Course myCourse = (Course) assign.getElement();
                        if(DEBUG)
                            output.println("Checking course and lab conflicts for " + myCourse.getName() + " in " + slot.getInfo() + "...");
                        checkedConflicts.add(assign);
                        constraintsMet = checkConflictingCourseAndLabs(assign);
                    }

                }
            }
            break;
            //course number related constraints (ie. 500-level, 900-level)
            case 3:{
                if (slotType == 0) {
                    if(!checkedNumber.contains(assign)) {
                        if (DEBUG)
                            output.println("Checking course number constraints...");
                        checkedNumber.add(assign);
                        constraintsMet = checkCourseNumberRequirements(assign);
                    }
                }
            }
            break;
        }

    }

    private boolean meetsCourseMax(Assignment assign) {
        Slot slot = assign.getSlot();

        int courseMax = slot.getMax();
        int courseAccumulator = 1;

        for(Assignment a : myAssignments){
            if (!a.equals(assign) && !checkedCourseMax.contains(a)) {
                if (slot.equals(a.getSlot())){
                    if(!isCompatible(assign.getElement(), a.getElement())){
                        if(DEBUG) {
                            output.println("    Constraint Not Met: Non-Compatible Elements");
                            output.println("    The elements " + assign.getElement().getName() + " and "
                                    + a.getElement().getName() +" are both in the slot " + slot.getInfo()
                                    + ", but they are in the list of non-compatible elements");
                        }
                        return false;
                    }
                    checkedCourseMax.add(a);
                    courseAccumulator++;
                }
            }
        }

        if(courseMax < courseAccumulator){
            if(DEBUG) {
                output.println("    Constraint Not Met: Course Max");
                output.println("    Course max of slot" + slot.getInfo() + " is "
                        + slot.getMax() + ", but the number of courses assigned is " + courseAccumulator);
            }
            return false;
        }else{
            if(DEBUG)
                output.println("    Constraint Met\n");
            return true;
        }
    }

    private boolean meetsLabMax(Assignment assign) {
        Slot slot = assign.getSlot();

        int labMax = slot.getMax();
        int labAccumulator = 1;

        for(Assignment a : myAssignments){
            if (!a.equals(assign) && !checkedLabMax.contains(a)) {
                if (slot.equals(a.getSlot())){
                    checkedLabMax.add(a);
                    labAccumulator++;
                }
            }
        }

        if(labMax < labAccumulator){
            if(DEBUG) {
                output.println("    Constraint Not Met: Lab Max");
                output.println("    Lab max of slot" + slot.getInfo() + " is "
                        + slot.getMax() + ", but the number of labs assigned is " + labAccumulator);
            }
            return false;
        }else{
            if(DEBUG)
                output.println("    Constraint Met\n");
            return true;
        }
    }


    private boolean checkConflictingCourseAndLabs(Assignment assign){

        Slot courseSlot = assign.getSlot();
        Course myCourse = (Course) assign.getElement();
        Day courseDay = courseSlot.getDay();
        LocalTime courseStart = courseSlot.getStartTime();
        LocalTime courseEnd = courseSlot.getEndTime();

        for (Assignment labAssign : myAssignments) {
            if (!checkedConflicts.contains(labAssign)) {
                Slot labSlot = labAssign.getSlot();
                if (labSlot.getType() == 1) {
                    Lab myLab = (Lab) labAssign.getElement();
                    if (myCourse.equals(myLab.getCourse())) {
                        checkedConflicts.add(labAssign);
                        Day labDay = labSlot.getDay();
                        if (slotsOverlap(courseSlot, labSlot)) {
                            LocalTime labStart = labSlot.getStartTime();
                            LocalTime labEnd = labSlot.getEndTime();
                            if (DEBUG) {
                                output.println("    Constraint Not Met: Lab slot overlapping with slot of its course");
                                output.println("    The course " + myCourse.getName() + " is scheduled on " + courseDay + " from " + courseStart + " to " + courseEnd);
                                output.println("    but the lab " + myLab.getName() + " is scheduled on " + labDay + " from " + labStart + " to " + labEnd);
                            }
                            return false;
                        }
                    }
                }
            }
        }
        if(DEBUG)
            output.println("    Constraint Met\n");
        return true;
    }

    private boolean checkTuesdayCourses(Assignment a){
        Slot slot = a.getSlot();

        if(slot.getDay() == Day.TU && slot.getStartTime().equals(LocalTime.of(11, 0)))
            return false;

        if(DEBUG)
            output.println("    Constraint Met\n");
        return true;
    }

    private boolean checkCourseNumberRequirements(Assignment assign){

        Slot slot = assign.getSlot();
        if(slot.getType() == 0 && !checkedNumber.contains(assign)) {
            checkedNumber.add(assign);
            Course course = (Course) assign.getElement();
            int firstDigit = getFirstDigit(course.getNumber());

            switch (firstDigit) {
                case 9: {
                    if (DEBUG)
                        output.println("Checking for evening courses...");
                    if (slot.getStartTime().isBefore(LocalTime.of(18, 0))) {
                        if (DEBUG){
                            output.println("    Constraint Not Met: 900-level in evening slot");
                            output.println("    The course " + course.getName() + " is scheduled at " + slot.getStartTime() +
                                    ", but 900-level courses must be scheduled at 18:00 or later");
                        }
                        return false;
                    }
                }
                break;
                case 5: {
                    if (DEBUG)
                        output.println("Checking for overlapping 500-level courses...");

                    for(Assignment assn : myAssignments){
                        Slot tempSlot = assn.getSlot();
                        if(tempSlot.getType() ==0 && !checkedNumber.contains(assn)){
                            checkedNumber.add(assn);
                            Course tempCourse = (Course) assn.getElement();
                            int tempFirstDigit = getFirstDigit(tempCourse.getNumber());
                            if(tempFirstDigit == 5){
                                if(slotsOverlap(slot, tempSlot)){
                                    if (DEBUG){
                                        output.println("    Constraint Not Met: Overlapping 500-level courses");
                                        output.println("    " + course.getName() + " is scheduled at " + slot.getInfo() +
                                                ", and " + tempCourse.getName() + " is scheduled at " + tempSlot.getInfo() + ", but 500level courses must be scheduled in different slots");
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        if(DEBUG)
            output.println("    Constraint Met\n");
        return true;
    }

    private int getFirstDigit(int courseNumber){
        int firstDigit = courseNumber;

        while (firstDigit > 9) {
            firstDigit /= 10;
        }

        return firstDigit;
    }


    private boolean slotsOverlap(Slot one, Slot two){

        Day dayOne = one.getDay();
        Day dayTwo = two.getDay();

        if(dayOne == dayTwo || (dayOne == Day.MO && dayTwo == Day.FR)
            || (dayTwo == Day.MO && dayOne == Day.FR)){

            LocalTime startOne = one.getStartTime();
            LocalTime startTwo = two.getStartTime();
            if(startOne.equals(startTwo)){
                return true;
            }
            LocalTime endOne = one.getEndTime();
            LocalTime endTwo = two.getEndTime();
            if(endOne.equals(endTwo)){
                return true;
            }
            if(startTwo.isAfter(startOne) && startTwo.isBefore(endOne)
                || endTwo.isAfter(startOne) && endTwo.isBefore(endOne)){
                return true;
            }
        }
        return false;
    }

    private boolean isCompatible(Element e1, Element e2){
        for(Element[] elem : notCompatible){
            if(e1.equals(elem[0]) && e2.equals(elem[1])
                    || e2.equals(elem[0]) && e1.equals(elem[1])){
                return false;
            }
        }

        return true;
    }
}