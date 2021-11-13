import main.Constr;
import main.Eval;
import problem.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class constrTest {

    public static void main(String[] args){

        Constr myConstr = new Constr();
        Eval myEval = new Eval();

        Course course_1 = new Course("CPSC", 433, 01);
        Course course_2 = new Course("CPSC", 433, 02);
        Course course_3 = new Course("SENG", 311, 01);
        Course course_4 = new Course("CPSC", 567, 01);

        Lab lab_11 = new Lab(course_1, 01, true);

        CourseSlot myCourseSlot_1 =  new CourseSlot(Day.MO, LocalTime.of(8, 0), 3, 2);
        CourseSlot myCourseSlot_2 =  new CourseSlot(Day.TU, LocalTime.of(9, 0), 3, 2);
        CourseSlot myCourseSlot_3 =  new CourseSlot(Day.FR, LocalTime.of(9, 30), 2, 1);

        LabSlot myLabSot = new LabSlot(Day.TU, LocalTime.of(10, 0), 2, 1);

        Assignment assign_1 = new Assignment(course_1, myCourseSlot_1);
        Assignment assign_2 = new Assignment(course_2, myCourseSlot_1);
        Assignment assign_3 = new Assignment(course_3, myCourseSlot_1);
        Assignment assign_4 = new Assignment(course_4, myCourseSlot_2);
        Assignment assign_5 = new Assignment(course_4, myCourseSlot_1);


        Assignment assign_6 = new Assignment(lab_11, myLabSot);
        Assignment assign_7 = new Assignment(course_1, myCourseSlot_2);
        Assignment assign_8 = new Assignment(course_4, myCourseSlot_2);

        ArrayList<Assignment> myAssignments_coursemin = new ArrayList<>(Arrays.asList(assign_1, assign_2, assign_4));
        ArrayList<Assignment> myAssignments_coursemax = new ArrayList<>(Arrays.asList(assign_1, assign_2, assign_3, assign_4, assign_5));


        ArrayList<Assignment> myAssignments_labmin = new ArrayList<>(Arrays.asList(assign_1, assign_2, assign_6));
        ArrayList<Assignment> myAssignments_labmax = new ArrayList<>(Arrays.asList(assign_1, assign_2, assign_3, assign_4, assign_5));

        ArrayList<Assignment> myAssignments_courselabconflict = new ArrayList<>(Arrays.asList(assign_8, assign_7, assign_2, assign_6));


        myConstr.checkConstraints(myAssignments_coursemin, 1);
        myConstr.checkConstraints(myAssignments_coursemax, 2);
        myConstr.checkConstraints(myAssignments_labmin, 3);
        myConstr.checkConstraints(myAssignments_labmax, 4);
        myConstr.checkConstraints(myAssignments_courselabconflict, 5);

    }
}
