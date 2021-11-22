import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.Control;
import main.Env;
import main.Eval;
import main.Parser;
import problem.Assignment;
import problem.Course;
import problem.CourseSlot;
import problem.Day;
import problem.Element;
import problem.Lab;
import problem.LabSlot;
import problem.Problem;
import problem.ProblemState;
import problem.Slot;

class EvalTest {

    private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
	private Slot mon1020 = new CourseSlot(Day.MO, LocalTime.parse("10:20"), 5, 1);
	private Slot mon1120 = new CourseSlot(Day.MO, LocalTime.parse("11:20"), 5, 1);
	private Slot mon1220 = new LabSlot(Day.MO, LocalTime.parse("12:20"), 5, 1);
    private Element course1 = new Course("CPSC", 433, 1);
    private Element course2 = new Course("CPSC", 433, 2);
    private Element course3 = new Course("CPSC", 422, 1);
    private Element course4 = new Course("CPSC", 329, 1);
    private Element course5 = new Course("CPSC", 213, 1);
    private Element course6 = new Course("CPSC", 101, 1);
    private ProblemState state;
    private Problem problem;
    private Eval eval = new Eval();

    @BeforeEach
    public void setup() {
    	try {
			Parser.parseFile("inputExample.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testSecDiffPenalty() {
    	Env.setSecdiffWeight(1);
    	assignments.add(new Assignment(course1, mon1020));
    	assignments.add(new Assignment(course2, mon1020));
    	
    	assertEquals(1, eval.partialEvaluate(assignments, 0));
    	
    }
    
    @Test
    public void testSecDiffDifferentSlots() {
    	Env.setSecdiffWeight(1);
    	assignments.add(new Assignment(course1, mon1020));
    	assignments.add(new Assignment(course2, mon1120));
    	
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    	
    }
    
    @Test
    public void testSecDiffCourses() {
    	Env.setSecdiffWeight(1);
    	assignments.add(new Assignment(course1, mon1020));
    	assignments.add(new Assignment(course3, mon1020));
    	
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPairsNoPenalty() {
    	
    }
}
