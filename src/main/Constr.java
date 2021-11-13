package main;

import problem.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;

public class Constr {

    private boolean DEBUG = true;


    private boolean constraintsMet;
    private int numberOfChecks = 4;
    private ArrayList<Assignment> myAssignments;

    //avoid checking constr of a slot multiple times
    private ArrayList<Assignment> checkedCourseMin;
    private ArrayList<Assignment> checkedCourseMax;
    private ArrayList<Assignment> checkedLabMin;
    private ArrayList<Assignment> checkedLabMax;
    private ArrayList<Assignment> checkedConflicts;
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
            case 2:{
                if(DEBUG)
                    output.println("Checking course and lab conflicts for " + slot.getInfo() + "...");
                constraintsMet = checkConflictingCourseAndLabs(myAssignments);
            }
            break;
//            case 3:{
//                constraintsMet = true;
//            }
//            break;
            default: break;
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


    private boolean checkConflictingCourseAndLabs(ArrayList<Assignment> assignments){

        for(Assignment courseAssign : assignments){
            if(!checkedConflicts.contains(courseAssign)) {
                Slot courseSlot = courseAssign.getSlot();
                if(courseSlot.getType() == 0){
                    checkedConflicts.add(courseAssign);
                    Course myCourse = (Course) courseAssign.getElement();
                    Day courseDay = courseSlot.getDay();
                    LocalTime courseStart = courseSlot.getStartTime();
                    LocalTime courseEnd = courseSlot.getEndTime();
//                    System.out.print("Course: ");
//                    System.out.print(myCourse.getName());
//                    System.out.print(" on ");
//                    System.out.println(course.getSlot().getInfo());
                    for(Assignment labAssign : assignments){
                        if(!checkedConflicts.contains(labAssign)) {
                            Slot labSlot = labAssign.getSlot();
                            if(labSlot.getType() == 1){
                                Lab myLab = (Lab)labAssign.getElement();
//                                System.out.print("Lab at: ");
//                                System.out.print(lab.getSlot().getInfo());
//                                System.out.print(" of ");
//                                System.out.println(myLab.getCourse().getName());

                                if(myCourse.equals(myLab.getCourse())){
                                    checkedConflicts.add(labAssign);
                                    Day labDay = labSlot.getDay();
                                    if(courseDay == labDay){
                                        LocalTime labStart = labSlot.getStartTime();
                                        LocalTime labEnd = labSlot.getEndTime();
                                        if(labStart.equals(courseStart) || (labStart.isAfter(courseStart) && labStart.isBefore(courseEnd))
                                                || (labEnd.isAfter(courseStart) && labEnd.isBefore(courseEnd))){
                                            if(DEBUG){
                                                output.println("Constraint Not Met: Lab slot overlapping with slot of its course");
                                                output.println("    The course " + myCourse.getName() + " is scheduled on " + courseDay + " from " + courseStart + " to " + courseEnd);
                                                output.println("    but the lab " + myLab.getName() + " is scheduled on "  + labDay + " from " + labStart + " to " + labEnd);
                                            }
                                            return false;
                                        }
                                    }
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

}