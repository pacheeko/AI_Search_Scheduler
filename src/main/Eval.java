package main;

import problem.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Eval {


    private boolean DEBUG = true;
    ArrayList<Assignment> myAssignments;
    private ArrayList<Assignment> checkedCourseMin;
    private ArrayList<Assignment> checkedLabMin;
    PrintStream output;

    private final int weight_minFill;
    private final int weight_pref;
    private final int weight_pair;
    private final int weight_secDiff;

    public Eval (int w_minFill, int w_pref, int w_pair, int w_secDiff){
        weight_minFill = w_minFill;
        weight_pref = w_pref;
        weight_pair = w_pair;
        weight_secDiff = w_secDiff;
    }

    public int evaluate(ArrayList<Assignment> assignments, int assignmentNumber) {

        int score = 0;
        try {
            if (DEBUG)
                output = new PrintStream("eval_log_" + assignmentNumber + ".txt");

            myAssignments = assignments;
            checkedCourseMin = new ArrayList<>();
            checkedLabMin = new ArrayList<>();

            for(Assignment a : myAssignments){
                score += minFilled(a);
                score += pref(a);
                score += pair(a);
                score += secDiff(a);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return score;
    }

    public int partialEvaluate(ArrayList<Assignment> assignments) {
        return 0;
    }

    private int minFilled(Assignment assign){

        int penalty = 0;

        if(assign.getSlot().getType() == 0){
            if(!checkedCourseMin.contains(assign)){
                penalty = meetsCourseMin(assign);
            }
        }else{
            if(!checkedLabMin.contains(assign)){
                penalty = meetsLabMin(assign);
            }
        }

        return penalty * weight_minFill;
    }

    private int pref(Assignment assign){
        int penalty = 0;

        return penalty * weight_pref;
    }

    private int pair(Assignment assign){
        int penalty = 0;

        return penalty * weight_pair;
    }

    private int secDiff(Assignment assign){
        int penalty = 0;

        return penalty * weight_secDiff;
    }


    private int meetsCourseMin(Assignment assign){

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
                output.println("    Constraint Not Met: Course Min");
                output.println("    Course min of slot" + slot.getInfo() + " is "
                        + slot.getMin() + ", but the number of courses assigned is " + courseAccumulator);
            }
            return courseMin - courseAccumulator;
        }else{
            if(DEBUG)
                output.println("    Constraint Met\n");
            return 0;
        }
    }

    private int meetsLabMin(Assignment assign){

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
                output.println("    Constraint Not Met: Lab Min");
                output.println("    Lab min of slot" + slot.getInfo() + " is "
                        + slot.getMin() + ", but the number of lab assigned is " + labAccumulator);
            }
            return labMin - labAccumulator;
        }else{
            if(DEBUG)
                output.println("    Constraint Met\n");
            return 0;
        }
    }
}