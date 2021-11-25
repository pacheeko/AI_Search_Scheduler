package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Constr;
import main.Eval;
import main.Parser;
import problem.Assignment;
import problem.Course;
import problem.CourseSlot;
import problem.Day;
import problem.Element;
import problem.Lab;
import problem.LabSlot;
import problem.Slot;

public class constrTest {
    
    private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
    
    //Course Slots
	private Slot mon800 = new CourseSlot(Day.MO, LocalTime.parse("08:00"), 3, 2);
	private Slot mon900 = new CourseSlot(Day.MO, LocalTime.parse("09:00"), 3, 2);
	private Slot mon1020 = new CourseSlot(Day.MO, LocalTime.parse("10:20"), 5, 1);
	private Slot mon1120 = new CourseSlot(Day.MO, LocalTime.parse("11:20"), 5, 1);
	private Slot tues1400 = new CourseSlot(Day.TU, LocalTime.parse("14:00"), 2, 1);
	private Slot tues930 = new CourseSlot(Day.TU, LocalTime.parse("09:30"), 3, 1);
	private Slot mon1900 = new CourseSlot(Day.MO, LocalTime.parse("19:00"), 2, 1);
	private Slot tues1100 = new CourseSlot(Day.TU, LocalTime.parse("11:00"), 2, 1);
	private Slot tues1800 = new CourseSlot(Day.TU, LocalTime.parse("18:00"), 3,1);
	
	//Lab Slots
	private Slot mon800lab = new LabSlot(Day.MO, LocalTime.parse("08:00"), 4, 2);
	private Slot mon900lab = new LabSlot(Day.MO, LocalTime.parse("09:00"), 2, 1);
	private Slot tues1000lab = new LabSlot(Day.TU, LocalTime.parse("10:00"), 2, 1);
	private Slot fri1000lab = new LabSlot(Day.FR, LocalTime.parse("10:00"), 2, 1);
	private Slot mon1220lab = new LabSlot(Day.MO, LocalTime.parse("12:20"), 5, 1);
	private Slot tues1800lab = new LabSlot(Day.TU, LocalTime.parse("18:00"), 3, 1);
	
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
    private Element cpsc392_9 = new Course("CPSC", 392, 9);
    private Element cpsc813_9 = new Course("CPSC", 813, 9);
    private Element cpsc913_9 = new Course("CPSC", 913, 9);
    private Element cpsc313_1 = new Course("CPSC", 313, 1);
    private Element cpsc571_1 = new Course("CPSC", 571, 1);
    
    
    //Labs
    private Element cpsc433_1_1 = new Lab((Course) cpsc433_1, 1, false);
    private Element cpsc433_2_2 = new Lab((Course) cpsc433_2, 2, false);
    private Element seng311_1_1 = new Lab((Course) seng311_1, 1, false);
    private Element cpsc567_1_1 = new Lab((Course) cpsc567_1, 1, false);
    private Element cpsc413_1_1 = new Lab((Course) cpsc413_1, 1, false);
    private Element cpsc313_1_1 = new Lab((Course) cpsc313_1, 1, false);
    
    private Constr constr = new Constr();

    @BeforeEach
    public void setup() {
    	try {
			Parser.parseFile("test_files/Inputs/junitTestExample.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mon800lab = Parser.getlabSlots().get(0);
    	tues1000lab = Parser.getlabSlots().get(1);
    	fri1000lab = Parser.getlabSlots().get(2);
    	mon800 = Parser.getCourseSlots().get(0);
    	mon900 = Parser.getCourseSlots().get(1);
    	tues930 = Parser.getCourseSlots().get(2);
    	tues1400 = Parser.getCourseSlots().get(3);
    	mon1900 = Parser.getCourseSlots().get(4);
    	
    	//Set partial assignments
    	assignments.add(new Assignment(seng311_1, mon800));
    	assignments.add(new Assignment(seng311_1_1, fri1000lab));
    	
    }

    @Test
    public void correctCourseSlot() {
    	assignments.add(new Assignment(cpsc213_1, tues1400));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void correctLabSLot() {
    	assignments.add(new Assignment(cpsc433_2_2, mon800lab));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void wrongCourseSlot() {
    	assignments.add(new Assignment(cpsc213_1, tues1000lab));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void wrongLabSlot() {
    	assignments.add(new Assignment(cpsc433_2_2, mon900));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void passesMaxElementsCourse() {
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc413_1, mon900));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc422_1, mon900));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void passesMaxElementsLab() {
    	assignments.add(new Assignment(seng311_1_1, fri1000lab));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc567_1_1, fri1000lab));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void failsMaxElementsCourse() {
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc413_1, mon900));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc213_1, mon900));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc422_1, mon900));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void failsMaxElementsLab() {
    	assignments.add(new Assignment(seng311_1_1, fri1000lab));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc567_1_1, fri1000lab));
    	constr.checkConstraints(assignments);
    	assignments.add(new Assignment(cpsc433_2_2, fri1000lab));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void unwantedMeetsConstraints() {
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void unwantedDoesNotMeetConstraits() {
    	assignments.add(new Assignment(cpsc433_1, mon800));
    	assertFalse(constr.checkConstraints(assignments));
    }
 
    @Test
    public void meetsEveningCourse() {
    	assignments.add(new Assignment(cpsc392_9, mon1900));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void failsEveningCourse() {
    	assignments.add(new Assignment(cpsc392_9, mon800));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meetsTuesdayCourseRestriction() {
    	Parser.getCourseSlots().add((CourseSlot) tues1100);
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void failsTuesdayCourseRestriction() {
    	Parser.getCourseSlots().add((CourseSlot) tues1100);
    	assignments.add(new Assignment(cpsc433_1, tues1100));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meets813CorrectSlot() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	assignments.add(new Assignment(cpsc813_9, tues1800));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails813CorrectSlot() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	assignments.add(new Assignment(cpsc813_9, tues930));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meets913CorrectSlot() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	assignments.add(new Assignment(cpsc913_9, tues1800));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails913CorrectSlot() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	assignments.add(new Assignment(cpsc913_9, tues930));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meets813And313Constraint1() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc313_1, cpsc813_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc313_1, mon800));
    	assignments.add(new Assignment(cpsc813_9, tues1800));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meets813And313Constraint2() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc313_1, cpsc813_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc813_9, tues1800));
    	assignments.add(new Assignment(cpsc313_1, mon800));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails813And313Constraint1() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc313_1, cpsc813_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc313_1, tues1800));
    	assignments.add(new Assignment(cpsc813_9, tues1800));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails813And313Constraint2() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc313_1, cpsc813_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc813_9, tues1800));
    	assignments.add(new Assignment(cpsc313_1, tues1800));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails813And313LabConstraint() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc313_1_1, cpsc813_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc813_9, tues1800));
    	assignments.add(new Assignment(cpsc313_1_1, tues1800lab));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meets913And413Constraint1() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc413_1, cpsc913_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc413_1, mon800));
    	assignments.add(new Assignment(cpsc913_9, tues1800));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meets913And413Constraint2() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc413_1, cpsc913_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc913_9, tues1800));
    	assignments.add(new Assignment(cpsc413_1, mon800));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails913And413Constraint1() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc413_1, cpsc913_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc413_1, tues1800));
    	assignments.add(new Assignment(cpsc913_9, tues1800));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails913And413Constraint2() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc413_1, cpsc913_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc913_9, tues1800));
    	assignments.add(new Assignment(cpsc413_1, tues1800));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails913And413LabConstraint() {
    	Parser.getCourseSlots().add((CourseSlot) tues1800);
    	Element[] pair = {cpsc413_1_1, cpsc913_9};
    	Parser.getNotCompatible().add(pair);
    	assignments.add(new Assignment(cpsc913_9, tues1800));
    	assignments.add(new Assignment(cpsc413_1_1, tues1800lab));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    /*
     * TODO: Not sure if these tests are necessary. Can a lab and course be scheduled at 
     * the same time if they are in different slots?
     * 
     */
    @Test
    public void meetsCourseConflictLabConstraint1() {
    	Parser.getlabSlots().add((LabSlot) mon900lab);    	
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assignments.add(new Assignment(cpsc433_1_1, mon800lab));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meetsCourseConflictLabConstraint2() {
    	Parser.getlabSlots().add((LabSlot) mon900lab);   
    	assignments.add(new Assignment(cpsc433_1_1, mon800lab));
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void failsCourseConflictLabConstraint1() {
    	Parser.getlabSlots().add((LabSlot) mon900lab);    	
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assignments.add(new Assignment(cpsc433_1_1, mon900lab));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void failsCourseConflictLabConstraint2() {
    	Parser.getlabSlots().add((LabSlot) mon900lab);    
    	assignments.add(new Assignment(cpsc433_1_1, mon900lab));
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test
    public void meetsCompatibility1() {
    	assignments.add(new Assignment(cpsc313_1, mon800));
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test 
    public void meetsCompatibility2() {
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assignments.add(new Assignment(cpsc313_1, mon800));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void failsCompatibility1() {
    	assignments.add(new Assignment(cpsc313_1, mon900));
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test 
    public void failsCompatibility2() {
    	assignments.add(new Assignment(cpsc433_1, mon900));
    	assignments.add(new Assignment(cpsc313_1, mon900));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    @Test 
    public void meets500Collision() {
    	assignments.add(new Assignment(cpsc567_1, mon800));
    	assignments.add(new Assignment(cpsc571_1, mon900));
    	assertTrue(constr.checkConstraints(assignments));
    }
    
    @Test
    public void fails500Collision() {
    	assignments.add(new Assignment(cpsc567_1, mon800));
    	assignments.add(new Assignment(cpsc571_1, mon800));
    	assertFalse(constr.checkConstraints(assignments));
    }
    
    
}
