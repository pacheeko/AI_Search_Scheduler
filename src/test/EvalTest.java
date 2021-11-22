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
    
    //Course Slots
	private Slot mon800 = new CourseSlot(Day.MO, LocalTime.parse("08:00"), 3, 2);
	private Slot mon900 = new CourseSlot(Day.MO, LocalTime.parse("09:00"), 3, 2);
	private Slot mon1020 = new CourseSlot(Day.MO, LocalTime.parse("10:20"), 5, 1);
	private Slot mon1120 = new CourseSlot(Day.MO, LocalTime.parse("11:20"), 5, 1);
	private Slot tues900 = new CourseSlot(Day.TU, LocalTime.parse("09:00"), 2, 1);
	private Slot tues930 = new CourseSlot(Day.TU, LocalTime.parse("09:30"), 3, 1);
	
	//Lab Slots
	private Slot mon800lab = new LabSlot(Day.MO, LocalTime.parse("08:00"), 4, 2);
	private Slot tues1000lab = new LabSlot(Day.TU, LocalTime.parse("10:00"), 2, 1);
	private Slot fri1000lab = new LabSlot(Day.FR, LocalTime.parse("10:00"), 2, 1);
	private Slot mon1220 = new LabSlot(Day.MO, LocalTime.parse("12:20"), 5, 1);
	
	//Courses
    private Element cpsc433_1 = new Course("CPSC", 433, 1);
    private Element cpsc433_2 = new Course("CPSC", 433, 2);
    private Element cpsc413_1 = new Course("CPSC", 413, 1);
    private Element cpsc422_1 = new Course("CPSC", 422, 1);
    private Element cpsc329_1 = new Course("CPSC", 329, 1);
    private Element cpsc213_1 = new Course("CPSC", 213, 1);
    private Element cpsc101_1 = new Course("CPSC", 101, 1);
    private Element seng311_1 = new Course("SENG", 311, 1);
    private Element cpsc567_1 = new Course("CPSC", 567, 1);
    
    //Labs
    private Element cpsc433_1_1 = new Lab((Course) cpsc433_1, 1, false);
    private Element cpsc433_2_2 = new Lab((Course) cpsc433_2, 2, false);
    private Element seng311_1_1 = new Lab((Course) seng311_1, 1, false);
    private Element cpsc567_1_1 = new Lab((Course) cpsc567_1, 1, false);
    
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
    	Env.setPrefWeight(0);
    	assignments.add(new Assignment(cpsc433_1, mon1020));
    	assignments.add(new Assignment(cpsc433_2, mon1020));
    	
    	assertEquals(1, eval.partialEvaluate(assignments, 0));
    	
    }
    
    @Test
    public void testSecDiffDifferentSlots() {
    	Env.setSecdiffWeight(1);
    	Env.setPrefWeight(0);
    	assignments.add(new Assignment(cpsc433_1, mon1020));
    	assignments.add(new Assignment(cpsc433_2, mon1120));
    	
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    	
    }
    
    @Test
    public void testSecDiffCourses() {
    	Env.setSecdiffWeight(1);
    	Env.setPrefWeight(0);
    	assignments.add(new Assignment(cpsc433_1, mon1020));
    	assignments.add(new Assignment(cpsc422_1, mon1020));
    	
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPairsNoPenalty1() {
    	Env.setPairWeight(1);
    	assignments.add(new Assignment(seng311_1, mon1020));
    	assignments.add(new Assignment(cpsc567_1, mon1020));
    	
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPairsNoPenalty2() {
    	Env.setPairWeight(1);
    	assignments.add(new Assignment(seng311_1, mon1020));
    	
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPairsNoPenalty3() {
    	Env.setPairWeight(1);
    	assignments.add(new Assignment(cpsc567_1, mon1020));
    	
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    }
    
    public void testPairsNoPenalty4() {
    	Env.setPairWeight(1);
    	assignments.add(new Assignment(cpsc567_1, mon1020));
    	assignments.add(new Assignment(seng311_1, mon1020));
    	
    	assertEquals(15, eval.partialEvaluate(assignments, 15));
    }
    
    @Test
    public void testPairsPenalty1() {
    	Env.setPairWeight(1);
    	assignments.add(new Assignment(cpsc567_1, mon1120));
    	assignments.add(new Assignment(seng311_1, mon1020));
    	assertEquals(1, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPairsPenalty2() {
    	Env.setPairWeight(5);
    	assignments.add(new Assignment(seng311_1, mon1020));
    	assignments.add(new Assignment(cpsc567_1, mon1120));
    	assertEquals(5, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPrefNoPenalty1() {
    	Env.setPrefWeight(1);
    	assignments.add(new Assignment(cpsc433_2, tues930));
    	assertEquals(0, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPrefNoPenalty() {
    	Env.setPrefWeight(1);
    	assignments.add(new Assignment(cpsc433_2, mon1020));
    	assignments.add(new Assignment(cpsc422_1, mon1120));
    	assertEquals(10, eval.partialEvaluate(assignments, 10));
    }
    
    @Test
    public void testPrefPenalty1() {
    	Env.setPrefWeight(1);
    	assignments.add(new Assignment(cpsc433_2, mon1020));
    	assertEquals(10, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testPrefPenalty2() {
    	Env.setPrefWeight(5);
    	assignments.add(new Assignment(cpsc433_2, mon1120));
    	assertEquals(50, eval.partialEvaluate(assignments, 0));
    }
    
    @Test
    public void testSecDiffAndPrefNoPenalty1() {
    	Env.setSecdiffWeight(2);
    	Env.setPrefWeight(3);
    	assignments.add(new Assignment(cpsc433_1, tues900));
    	assignments.add(new Assignment(cpsc433_2, tues930));
    	assertEquals(22, eval.partialEvaluate(assignments, 22));
    }
    
    @Test
    public void testSecDiffAndPrefPenalty1() {
    	Env.setSecdiffWeight(2);
    	Env.setPrefWeight(3);
    	assignments.add(new Assignment(cpsc433_2, tues930));
    	assignments.add(new Assignment(cpsc433_1, tues930));
    	assertEquals(54, eval.partialEvaluate(assignments, 22));
    }
    
    @Test
    public void testSecDiffAndPairNoPenalty1() {
    	Env.setSecdiffWeight(4);
    	Env.setPairWeight(5);
    	Env.setPrefWeight(0);
    	Element e1 = new Course("CPSC", 433, 1);
    	Element e2 = new Course("CPSC", 413, 1);
    	Element[] elements = {e1,e2};
    	Parser.getPairs().add(elements);
    	assignments.add(new Assignment(cpsc433_2, mon1120));
    	assignments.add(new Assignment(cpsc433_1, mon1020));
    	assignments.add(new Assignment(cpsc413_1, mon1020));
    	assertEquals(83, eval.partialEvaluate(assignments, 83));
    }
    
    @Test
    public void testSecDiffAndPairPenalty1() {
    	Env.setSecdiffWeight(4);
    	Env.setPairWeight(5);
    	Env.setPrefWeight(0);
    	Element e1 = new Course("CPSC", 433, 1);
    	Element e2 = new Course("CPSC", 413, 1);
    	Element[] elements = {e1,e2};
    	Parser.getPairs().add(elements);
    	assignments.add(new Assignment(cpsc433_2, mon1020));
    	assignments.add(new Assignment(cpsc413_1, mon1120));
    	assignments.add(new Assignment(cpsc433_1, mon1020));
    	assertEquals(92, eval.partialEvaluate(assignments, 83));
    }
    
    @Test
    public void testPrefAndPairNoPenalty1() {
    	Env.setSecdiffWeight(0);
    	Env.setPairWeight(5);
    	Env.setPrefWeight(9);
    	Element e1 = new Course("CPSC", 433, 1);
    	Element e2 = new Course("CPSC", 413, 1);
    	Element[] elements = {e1,e2};
    	Parser.getPairs().add(elements);
    	assignments.add(new Assignment(cpsc433_2, tues930));
    	assignments.add(new Assignment(cpsc413_1, tues900));
    	assignments.add(new Assignment(cpsc433_1, tues900));
    	assertEquals(52, eval.partialEvaluate(assignments, 52));
    }
    
    @Test
    public void testPrefAndPairPenalty1() {
    	Env.setSecdiffWeight(0);
    	Env.setPairWeight(5);
    	Env.setPrefWeight(9);
    	Element e1 = new Course("CPSC", 433, 1);
    	Element e2 = new Course("CPSC", 413, 1);
    	Element[] elements = {e1,e2};
    	Parser.getPairs().add(elements);
    	assignments.add(new Assignment(cpsc433_2, tues930));
    	assignments.add(new Assignment(cpsc413_1, tues900));
    	assignments.add(new Assignment(cpsc433_1, mon1020));
    	assertEquals(52+90+5, eval.partialEvaluate(assignments, 52));
    }
    
    @Test
    public void testAllPartialNoPenalty1() {
    	Env.setSecdiffWeight(4);
    	Env.setPairWeight(8);
    	Env.setPrefWeight(3);
    	Element e1 = new Course("CPSC", 433, 1);
    	Element e2 = new Course("CPSC", 413, 1);
    	Element[] elements = {e1,e2};
    	Parser.getPairs().add(elements);
    	assignments.add(new Assignment(cpsc433_2, tues930));
    	assignments.add(new Assignment(cpsc413_1, tues900));
    	assignments.add(new Assignment(cpsc433_1, tues900));
    	assertEquals(13, eval.partialEvaluate(assignments, 13));
    }
    
    @Test
    public void testAllPartialPenalty1() {
    	Env.setSecdiffWeight(4);
    	Env.setPairWeight(8);
    	Env.setPrefWeight(3);
    	Element e1 = new Course("CPSC", 433, 1);
    	Element e2 = new Course("CPSC", 413, 1);
    	Element[] elements = {e1,e2};
    	Parser.getPairs().add(elements);
    	assignments.add(new Assignment(cpsc433_2, tues930));
    	assignments.add(new Assignment(cpsc413_1, tues900));
    	assignments.add(new Assignment(cpsc433_1, tues930));
    	assertEquals(13+8+4+(3*10), eval.partialEvaluate(assignments, 13));
    }
    
    @Test
    public void testEvaluateNoPenalty1() {
    	Env.setMinfilledWeight(0);
    	assertEquals(13, eval.evaluate(assignments, 13));
    }
    
    @Test
    public void testEvaluateNoPenalty2() {
    	Env.setMinfilledWeight(2);
    	
    	mon800lab = Parser.getlabSlots().get(0);
    	tues1000lab = Parser.getlabSlots().get(1);
    	fri1000lab = Parser.getlabSlots().get(2);
    	mon800 = Parser.getCourseSlots().get(0);
    	mon900 = Parser.getCourseSlots().get(1);
    	tues930 = Parser.getCourseSlots().get(2);
    	tues900 = Parser.getCourseSlots().get(3);
    	
    	assignments.add(new Assignment(cpsc433_1, tues930));
    	assignments.add(new Assignment(cpsc433_2, tues900));
    	assignments.add(new Assignment(cpsc422_1, mon800));
    	assignments.add(new Assignment(cpsc329_1, mon800));
    	assignments.add(new Assignment(cpsc213_1, mon900));
    	assignments.add(new Assignment(cpsc101_1, mon900));
    	assignments.add(new Assignment(cpsc433_1_1, mon800lab));
    	assignments.add(new Assignment(cpsc433_2_2, mon800lab));
    	assignments.add(new Assignment(seng311_1_1, tues1000lab));
    	assignments.add(new Assignment(cpsc567_1_1, fri1000lab));
    	assertEquals(43, eval.evaluate(assignments, 43));
    	
    }
    
    @Test
    public void testEvaluatePenalty1() {
    	Env.setMinfilledWeight(1);
    	assertEquals(23, eval.evaluate(assignments, 13));
    }
    
    @Test
    public void testEvaluatePenalty2() {
    	Env.setMinfilledWeight(4);
    	
    	mon800lab = Parser.getlabSlots().get(0);
    	tues1000lab = Parser.getlabSlots().get(1);
    	fri1000lab = Parser.getlabSlots().get(2);
    	mon800 = Parser.getCourseSlots().get(0);
    	mon900 = Parser.getCourseSlots().get(1);
    	tues930 = Parser.getCourseSlots().get(2);
    	tues900 = Parser.getCourseSlots().get(3);
    	
    	assignments.add(new Assignment(cpsc433_1, tues930));
    	assignments.add(new Assignment(cpsc433_2, tues900));
    	assignments.add(new Assignment(cpsc422_1, mon800));
    	assignments.add(new Assignment(cpsc329_1, mon800));
    	assignments.add(new Assignment(cpsc213_1, mon900));
    	assignments.add(new Assignment(cpsc101_1, mon900));
    	assignments.add(new Assignment(cpsc433_2_2, mon800lab));
    	assignments.add(new Assignment(seng311_1_1, tues1000lab));
    	assignments.add(new Assignment(cpsc567_1_1, fri1000lab));
    	assertEquals(47, eval.evaluate(assignments, 43));
    	
    }
    
    @Test
    public void testEvaluatePenalty3() {
    	Env.setMinfilledWeight(7);
    	
    	mon800lab = Parser.getlabSlots().get(0);
    	tues1000lab = Parser.getlabSlots().get(1);
    	fri1000lab = Parser.getlabSlots().get(2);
    	mon800 = Parser.getCourseSlots().get(0);
    	mon900 = Parser.getCourseSlots().get(1);
    	tues930 = Parser.getCourseSlots().get(2);
    	tues900 = Parser.getCourseSlots().get(3);
    	
    	assignments.add(new Assignment(cpsc433_1, tues930));
    	assignments.add(new Assignment(cpsc329_1, mon800));
    	assignments.add(new Assignment(cpsc213_1, mon900));
    	assignments.add(new Assignment(cpsc101_1, mon900));
    	assignments.add(new Assignment(cpsc433_1_1, mon800lab));
    	assignments.add(new Assignment(cpsc433_2_2, mon800lab));
    	assignments.add(new Assignment(seng311_1_1, tues1000lab));
    	assertEquals(64, eval.evaluate(assignments, 43));
    	
    }
    
    @Test
    public void testComplex1() {
    	Env.setPairWeight(3);
    	Env.setPrefWeight(2);
    	Env.setSecdiffWeight(5);
    	Env.setMinfilledWeight(7);
    	int i = 0;
    	
    	mon800lab = Parser.getlabSlots().get(0);
    	tues1000lab = Parser.getlabSlots().get(1);
    	fri1000lab = Parser.getlabSlots().get(2);
    	mon800 = Parser.getCourseSlots().get(0);
    	mon900 = Parser.getCourseSlots().get(1);
    	tues930 = Parser.getCourseSlots().get(2);
    	tues900 = Parser.getCourseSlots().get(3);
    	
    	assignments.add(new Assignment(cpsc433_1, tues930));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc329_1, mon800));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc213_1, mon900));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc101_1, mon900));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc433_1_1, mon800lab));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc433_2_2, tues1000lab));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(seng311_1_1, tues1000lab));	
    	i = eval.partialEvaluate(assignments, i);
    	i = eval.evaluate(assignments, i);
    	assertEquals(20+2+28, i);
    	
    }
    
    @Test
    public void testComplex2() {
    	Env.setPairWeight(3);
    	Env.setPrefWeight(2);
    	Env.setSecdiffWeight(5);
    	Env.setMinfilledWeight(7);
    	int i = 0;
    	
    	mon800lab = Parser.getlabSlots().get(0);
    	tues1000lab = Parser.getlabSlots().get(1);
    	fri1000lab = Parser.getlabSlots().get(2);
    	mon800 = Parser.getCourseSlots().get(0);
    	mon900 = Parser.getCourseSlots().get(1);
    	tues930 = Parser.getCourseSlots().get(2);
    	tues900 = Parser.getCourseSlots().get(3);
    	
    	assignments.add(new Assignment(cpsc433_1, tues900));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc433_2, tues900));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc567_1, mon800));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(seng311_1, mon900));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc101_1, mon900));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc433_1_1, mon800lab));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(cpsc433_2_2, tues1000lab));
    	i = eval.partialEvaluate(assignments, i);
    	assignments.add(new Assignment(seng311_1_1, tues1000lab));	
    	i = eval.partialEvaluate(assignments, i);
    	i = eval.evaluate(assignments, i);
    	assertEquals(20+5+3+2+28, i);
    	
    }
    
}
