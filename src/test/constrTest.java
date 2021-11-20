//package test;
import main.Constr;
import main.Parser;
import problem.*;

import java.util.ArrayList;
import java.util.Arrays;

public class constrTest {

    public static void main(String[] args){

        Parser parser = new Parser();
        try {
            parser.parseFile("tests/constr_test_input");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Constr myConstr = new Constr(parser);

        ArrayList<Course> myCourses =  parser.getCourses();
        ArrayList<CourseSlot> myCourseSlots =  parser.getCourseSlots();

        ArrayList<Lab> myLabs =  parser.getLabs();
        ArrayList<LabSlot> myLabSlots =  parser.getlabSlots();

        Course course_1 = myCourses.get(0);
        Course course_2 = myCourses.get(1);
        Course course_3 = myCourses.get(2);
        Course course_4 = myCourses.get(3);

        Lab lab_11 = myLabs.get(0);
        Lab lab_22 = myLabs.get(1);
        Lab lab_31 = myLabs.get(2);
        Lab lab_41 = myLabs.get(3);

        CourseSlot myCourseSlot_1 =  myCourseSlots.get(0);
        CourseSlot myCourseSlot_2 =   myCourseSlots.get(1);
        CourseSlot myCourseSlot_3 =   myCourseSlots.get(2);

        LabSlot myLabSot_1 =  myLabSlots.get(0);
        LabSlot myLabSot_2 =  myLabSlots.get(1);
        LabSlot myLabSot_3 =  myLabSlots.get(2);

        Assignment assign_1 = new Assignment(course_1, myCourseSlot_3);
        Assignment assign_2 = new Assignment(course_2, myCourseSlot_3);
        Assignment assign_3 = new Assignment(course_3, myCourseSlot_3);
        Assignment assign_4 = new Assignment(course_4, myCourseSlot_2);
        Assignment assign_5 = new Assignment(course_4, myCourseSlot_1);
        Assignment assign_6 = new Assignment(course_2, myCourseSlot_2);
        Assignment assign_7 = new Assignment(course_4, myCourseSlot_3);
        Assignment assign_8 = new Assignment(course_1, myCourseSlot_2);
        Assignment assign_9 = new Assignment(course_2, myCourseSlot_3);

        Assignment lab_assign_1 = new Assignment(lab_11, myLabSot_2);
        Assignment lab_assign_2 = new Assignment(lab_11, myLabSot_1);
        Assignment lab_assign_3 = new Assignment(lab_31, myLabSot_1);
        Assignment lab_assign_4 = new Assignment(lab_41, myLabSot_2);
        Assignment lab_assign_5 = new Assignment(lab_22, myLabSot_1);
        Assignment lab_assign_6 = new Assignment(lab_11, myLabSot_3);
        Assignment lab_assign_7 = new Assignment(lab_31, myLabSot_3);

        ArrayList<Assignment> myAssignments_coursemax = new ArrayList<>(Arrays.asList(assign_1, assign_2, assign_3, assign_4, assign_5));
        ArrayList<Assignment> myAssignments_labmax = new ArrayList<>(Arrays.asList(lab_assign_2, lab_assign_3, lab_assign_5));
        ArrayList<Assignment> myAssignments_courselabconflict = new ArrayList<>(Arrays.asList(assign_1, assign_2, lab_assign_1, lab_assign_4, assign_5));

        ArrayList<Assignment> myAssignments_partialmet = new ArrayList<>(Arrays.asList(assign_6, assign_3
                ,assign_7 ,lab_assign_6, lab_assign_7));

        ArrayList<Assignment> myAssignments_allmet = new ArrayList<>(Arrays.asList(assign_6, assign_3
                ,assign_7 ,lab_assign_6, lab_assign_7));

        ArrayList<Assignment> unwanted = new ArrayList<>(Arrays.asList(assign_8));

        ArrayList<Assignment> non_compatible = new ArrayList<>(Arrays.asList(assign_7, assign_9));

        myConstr.checkPartialConstraints(myAssignments_coursemax, 1, false);
        myConstr.checkPartialConstraints(myAssignments_labmax, 2, false);
        myConstr.checkPartialConstraints(myAssignments_courselabconflict, 3, false);
        myConstr.checkPartialConstraints(myAssignments_partialmet, 4, false);

        myConstr.checkConstraints(myAssignments_allmet, 5);
        myConstr.checkPartialConstraints(unwanted, 6, false);
        myConstr.checkPartialConstraints(non_compatible, 7, false);

    }
}