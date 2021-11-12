package main;

import problem.Assignment;
import problem.Slot;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Constr {

    private boolean constraintsMet;
    private int numberOfChecks = 4;
    private ArrayList<Assignment> myAssignments;

    //avoid checking constr of a slot multiple times
    private ArrayList<Assignment> checkedCourseMin;
    private ArrayList<Assignment> checkedCourseMax;
    private ArrayList<Assignment> checkedLabMin;
    private ArrayList<Assignment> checkedLabMax;
    PrintStream output;

    public Constr() {}

    public boolean checkConstraints(ArrayList<Assignment> assignments, int assignmentNumber) {

        try{
            output = new PrintStream("constr_log_" + assignmentNumber +".txt");

            checkedCourseMin = new ArrayList<>();
            checkedCourseMax = new ArrayList<>();
            checkedLabMin = new ArrayList<>();
            checkedLabMax = new ArrayList<>();

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
            output.println("All Hard Constraints Have Been Met");
            output.flush();

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
                        output.println("Checking Course Min for " + name + "...");
                        constraintsMet = meetsCourseMin(assign);
                        checkedCourseMin.add(assign);
                    }
                }else{
                    if(!checkedLabMin.contains(assign)){
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
                       output.println("Checking Course Max for" + name + "...");
                       constraintsMet = meetsCourseMax(assign);
                       checkedCourseMax.add(assign);
                   }
               }else{
                   if(!checkedLabMax.contains(assign)){
                       output.println("Checking Lab Max for" + name + "...");
                       constraintsMet = meetsLabMax(assign);
                       checkedLabMax.add(assign);
                   }
               }
            }
            break;
//            case 2:{
//                constraintsMet = false;
//            }
//            break;
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
            output.println("Constraint Not Met: Course Min");
            output.println("    Course min of slot" + slot.getInfo() + " is "
                    + slot.getMin()+ ", but the number of courses assigned is " + courseAccumulator);
            return false;
        }else{
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
            output.println("Constraint Not Met: Course Max");
            output.println("    Course max of slot" + slot.getInfo() + " is "
                    + slot.getMax()+ ", but the number of courses assigned is " + courseAccumulator);
            return false;
        }else{
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
            output.println("Constraint Not Met: Lab Min");
            output.println("    Lab min of slot" + slot.getInfo() + " is "
                    + slot.getMin()+ ", but the number of lab assigned is " + labAccumulator);
            return false;
        }else{
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
            output.println("Constraint Not Met: Lab Max");
            output.println("    Lab max of slot" + slot.getInfo() + " is "
                    + slot.getMax()+ ", but the number of labs assigned is " + labAccumulator);
            return false;
        }else{
            output.println("Constraint Met");
            return true;
        }
    }

}