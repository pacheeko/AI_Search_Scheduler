package main;

import problem.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;

public class Constr {

    private boolean DEBUG = true;


    private boolean constraintsMet;
    private int numberOfChecks = 5;
    private ArrayList<Assignment> myAssignments;

    //avoid checking constr of a slot multiple times
    private ArrayList<Assignment> checkedCourseMin;
    private ArrayList<Assignment> checkedCourseMax;
    private ArrayList<Assignment> checkedLabMin;
    private ArrayList<Assignment> checkedLabMax;
    private ArrayList<Assignment> checkedConflicts;
    private ArrayList<Assignment> checkedNumber;
    PrintStream output;

    public Constr() {}

    public boolean checkConstraints(ArrayList<Assignment> assignments, int assignmentNumber) {

        try{
            if(DEBUG)
                output = new PrintStream("constr_log_" + assignmentNumber +".txt");

            checkedCourseMin = new ArrayList<>();
            checkedCourseMax = new ArrayList<>();
            checkedLabMin = new ArrayList<>();
            checkedLabMax = new ArrayList<>();
            checkedConflicts = new ArrayList<>();
            checkedNumber = new ArrayList<>();

            constraintsMet = true;
            myAssignments = assignments;

            for(Assignment a : myAssignments){
                for(int i = 0;i < numberOfChecks;i++){
                    performCheck(a, i);
                    if(!constraintsMet){
                        return false;
                    }
                }
            }
            if(DEBUG) {
                output.println("All Hard Constraints Have Been Met");
                output.flush();
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return constraintsMet;
    }

    public boolean checkPartialConstraints(ArrayList<Assignment> assignments) {

        constraintsMet = true;


        return constraintsMet;
    }

    private void performCheck(Assignment assign, int check){

        Slot slot = assign.getSlot();
        String name = slot.getInfo();
        int slotType = slot.getType();

        switch(check){
            //course min
            case 0:{
                if(slotType == 0){
                    if(!checkedCourseMin.contains(assign)){
                        if(DEBUG)
                            output.println("Checking Course Min for " + name + "...");
                        constraintsMet = meetsCourseMin(assign);
                        checkedCourseMin.add(assign);
                    }
                }else{
                    if(!checkedLabMin.contains(assign)){
                        if(DEBUG)
                            output.println("Checking Lab Min for " + name + "...");
                        constraintsMet = meetsLabMin(assign);
                        checkedLabMin.add(assign);
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
                if(DEBUG)
                    output.println("Checking course and lab conflicts for " + slot.getInfo() + "...");
                constraintsMet = checkConflictingCourseAndLabs(assign);
            }
            break;
            //courses on tuesday at 11
            case 3:{
                if(slotType==0){
                    if(DEBUG)
                        output.println("Checking for courses scheduled on Tuesday at 11:00...");
                    constraintsMet = checkTuesdayCourses(assign);
                }
            }
            break;
            case 4:{
                if(slotType == 0) {
                    constraintsMet = checkCourseNumberRequirements(assign);
                }
            }
            break;
        }

    }

    private boolean meetsCourseMin(Assignment assign){

        Slot slot = assign.getSlot();

        int courseMin = slot.getMin();
        int courseAccumulator = 1;

        for(Assignment a : myAssignments){
            if (!a.equals(assign) && !checkedCourseMin.contains(a)) {
                if (slot.equals(a.getSlot())){
                    courseAccumulator++;
                    checkedCourseMin.add(a);
                }
            }
        }

        if(courseMin > courseAccumulator){
            if(DEBUG) {
                output.println("Constraint Not Met: Course Min");
                output.println("    Course min of slot" + slot.getInfo() + " is "
                        + slot.getMin() + ", but the number of courses assigned is " + courseAccumulator);
            }
            return false;
        }else{
            if(DEBUG)
                output.println("Constraint Met");
            return true;
        }
    }


    private boolean meetsCourseMax(Assignment assign) {
        Slot slot = assign.getSlot();

        int courseMax = slot.getMax();
        int courseAccumulator = 1;

        for(Assignment a : myAssignments){
            if (!a.equals(assign) && !checkedCourseMax.contains(a)) {
                if (slot.equals(a.getSlot())){
                    checkedCourseMax.add(a);
                    courseAccumulator++;
                }
            }
        }

        if(courseMax < courseAccumulator){
            if(DEBUG) {
                output.println("Constraint Not Met: Course Max");
                output.println("    Course max of slot" + slot.getInfo() + " is "
                        + slot.getMax() + ", but the number of courses assigned is " + courseAccumulator);
            }
            return false;
        }else{
            if(DEBUG)
                output.println("Constraint Met");
            return true;
        }
    }


    private boolean meetsLabMin(Assignment assign){

        Slot slot = assign.getSlot();

        int labMin = slot.getMin();
        int labAccumulator = 1;

        for(Assignment a : myAssignments){
            if (!a.equals(assign) && !checkedLabMin.contains(a)) {
                if (slot.equals(a.getSlot())){
                    labAccumulator++;
                    checkedLabMin.add(a);
                }
            }
        }

        if(labMin > labAccumulator){
            if(DEBUG) {
                output.println("Constraint Not Met: Lab Min");
                output.println("    Lab min of slot" + slot.getInfo() + " is "
                        + slot.getMin() + ", but the number of lab assigned is " + labAccumulator);
            }
            return false;
        }else{
            if(DEBUG)
                output.println("Constraint Met");
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
                output.println("Constraint Not Met: Lab Max");
                output.println("    Lab max of slot" + slot.getInfo() + " is "
                        + slot.getMax() + ", but the number of labs assigned is " + labAccumulator);
            }
            return false;
        }else{
            if(DEBUG)
                output.println("Constraint Met");
            return true;
        }
    }


    private boolean checkConflictingCourseAndLabs(Assignment assign){

        if(!checkedConflicts.contains(assign)) {
            Slot courseSlot = assign.getSlot();
            if (courseSlot.getType() == 0) {
                checkedConflicts.add(assign);
                Course myCourse = (Course) assign.getElement();
                Day courseDay = courseSlot.getDay();
                LocalTime courseStart = courseSlot.getStartTime();
                LocalTime courseEnd = courseSlot.getEndTime();
    //                    System.out.print("Course: ");
    //                    System.out.print(myCourse.getName());
    //                    System.out.print(" on ");
    //                    System.out.println(course.getSlot().getInfo());
                for (Assignment labAssign : myAssignments) {
                    if (!checkedConflicts.contains(labAssign)) {
                        Slot labSlot = labAssign.getSlot();
                        if (labSlot.getType() == 1) {
                            Lab myLab = (Lab) labAssign.getElement();
    //                                System.out.print("Lab at: ");
    //                                System.out.print(lab.getSlot().getInfo());
    //                                System.out.print(" of ");
    //                                System.out.println(myLab.getCourse().getName());

                            if (myCourse.equals(myLab.getCourse())) {
                                checkedConflicts.add(labAssign);
                                Day labDay = labSlot.getDay();
                                if (slotsOverlap(courseSlot, labSlot)) {
                                    LocalTime labStart = labSlot.getStartTime();
                                    LocalTime labEnd = labSlot.getEndTime();
                                    if (DEBUG) {
                                        output.println("Constraint Not Met: Lab slot overlapping with slot of its course");
                                        output.println("    The course " + myCourse.getName() + " is scheduled on " + courseDay + " from " + courseStart + " to " + courseEnd);
                                        output.println("    but the lab " + myLab.getName() + " is scheduled on " + labDay + " from " + labStart + " to " + labEnd);
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(DEBUG)
            output.println("Constraint Met");
        return true;
    }

    private boolean checkTuesdayCourses(Assignment a){
        Slot slot = a.getSlot();

        if(slot.getDay() == Day.TU && slot.getStartTime().equals(LocalTime.of(11, 0)))
            return false;

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
                        output.println("Checking 900-level courses...");
                    if (slot.getStartTime().isBefore(LocalTime.of(18, 0))) {
                        //must be evening course
                        return false;
                    } else {
                        //check for 913 scheduled at same time of 413 + its overlap exclusions
                    }
                }
                break;
                case 8: {
                    if (DEBUG)
                        output.println("Checking overlapping courses with CPSC 813...");
                    //check for 813 scheduled at same time of 313 + its overlap exclusions
                }
                break;
                case 5: {
                    if (DEBUG)
                        output.println("Checking for overlapping 500-level courses...");
                    //no 500-level courses scheduled at same time
                    for(Assignment assn : myAssignments){
                        Slot tempSlot = assn.getSlot();
                        if(tempSlot.getType() ==0 && !checkedNumber.contains(assn)){
                            checkedNumber.add(assn);
                            Course tempCourse = (Course) assn.getElement();
                            int tempFirstDigit = getFirstDigit(tempCourse.getNumber());

                            if(tempFirstDigit == 5){
                                if(slot.equals(tempSlot)){
                                    return false;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
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

        if(dayOne == dayTwo){
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
}