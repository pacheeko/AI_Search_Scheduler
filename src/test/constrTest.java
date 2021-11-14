import main.Constr;
import problem.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class constrTest {

    public static void main(String[] args){

        Constr myConstr = new Constr();

        Course course_1 = new Course("CPSC", 433, 01);
        Course course_2 = new Course("CPSC", 433, 02);
        Course course_3 = new Course("SENG", 311, 01);
        Course course_4 = new Course("CPSC", 567, 01);

        Lab lab_11 = new Lab(course_1, 01, false);
        Lab lab_22 = new Lab(course_2, 02, false);
        Lab lab_31 = new Lab(course_3, 01, false);
        Lab lab_41 = new Lab(course_4, 01, true);

        CourseSlot myCourseSlot_1 =  new CourseSlot(Day.MO, LocalTime.of(8, 0), 3, 2);
        CourseSlot myCourseSlot_2 =  new CourseSlot(Day.TU, LocalTime.of(9, 0), 3, 2);
        CourseSlot myCourseSlot_3 =  new CourseSlot(Day.FR, LocalTime.of(9, 30), 2, 1);

        LabSlot myLabSot_1 = new LabSlot(Day.TU, LocalTime.of(10, 0), 2, 1);
        LabSlot myLabSot_2 = new LabSlot(Day.MO, LocalTime.of(8, 0), 4, 2);
        LabSlot myLabSot_3 = new LabSlot(Day.FR, LocalTime.of(10, 0), 2, 1);

        Assignment assign_1 = new Assignment(course_1, myCourseSlot_1);
        Assignment assign_2 = new Assignment(course_2, myCourseSlot_1);
        Assignment assign_3 = new Assignment(course_3, myCourseSlot_1);
        Assignment assign_4 = new Assignment(course_4, myCourseSlot_2);
        Assignment assign_5 = new Assignment(course_4, myCourseSlot_1);



        Assignment assign_6 = new Assignment(course_2, myCourseSlot_2);
        Assignment assign_7 = new Assignment(course_4, myCourseSlot_3);

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

        ArrayList<Assignment> myAssignments_partialmet = new ArrayList<>(Arrays.asList(assign_1, assign_6, assign_3
                ,assign_7 ,lab_assign_6, lab_assign_7));

        ArrayList<Assignment> myAssignments_allmet = new ArrayList<>(Arrays.asList(assign_1, assign_6, assign_3
                ,assign_7 ,lab_assign_6, lab_assign_7));


        ArrayList<Element> allElements =  new ArrayList<>(Arrays.asList(course_1, course_2,course_3,course_4, lab_11, lab_22, lab_31,lab_41));

        myConstr.checkPartialConstraints(myAssignments_coursemax, 1, false);
        myConstr.checkPartialConstraints(myAssignments_labmax, 2, false);
        myConstr.checkPartialConstraints(myAssignments_courselabconflict, 3, false);
        myConstr.checkPartialConstraints(myAssignments_partialmet, 4, false);

        myConstr.checkConstraints(myAssignments_allmet, 5, allElements);

    }
}
