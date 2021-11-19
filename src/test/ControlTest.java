//package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.Control;
import problem.ProblemState;
import problem.Problem;
import problem.Element;
import problem.Course;
import problem.Lab;
import problem.Slot;
import problem.CourseSlot;
import problem.LabSlot;
import problem.Day;

public class ControlTest {

    private ArrayList<Element> elements;
    private ArrayList<Slot> slots;
    private Control control;

    void populateElements() {
        elements = new ArrayList<Element>();
        populateCourses();
        populateLabs();
    }

    private void populateLabs() {
        elements.add(new Lab((Course) elements.get(0), 1, false));
        elements.add(new Lab((Course) elements.get(1), 1, false));
        elements.add(new Lab((Course) elements.get(1), 2, false));
        elements.add(new Lab((Course) elements.get(2), 1, false));
    }

    private void populateCourses() {
        elements.add(new Course("CPSC", 433, 1));
        elements.add(new Course("CPSC", 433, 2));
        elements.add(new Course("CPSC", 422, 1));
        elements.add(new Course("CPSC", 329, 1));
        elements.add(new Course("CPSC", 213, 1));
        elements.add(new Course("CPSC", 101, 1));
    }

    void populateSlots() {
        slots = new ArrayList<Slot>();
        populateCourseSlots();
        populateLabSlots();        
    }

    private void populateLabSlots() {
        slots.add(new LabSlot(Day.MO, LocalTime.parse("12:20"), 5, 1));
    }

    private void populateCourseSlots() {
        slots.add(new CourseSlot(Day.MO, LocalTime.parse("10:20"), 5, 1));
        slots.add(new CourseSlot(Day.MO, LocalTime.parse("11:20"), 5, 1));
    }

    @BeforeEach
    public void setup() {
        populateElements();
        populateSlots();
        
        Problem problem = new Problem(elements);
        ProblemState root = new ProblemState(problem, null);

        control = new Control(root, slots);        
    }

    @Test
    public void testInitial() {           
        assertEquals(1, control.getLeafs().size());
    }

    @Test
    public void testAfterOneRun() {
        control.next();        
        assertEquals(control.getRoot().getChildren(), control.getLeafs());
    }

    @Test
    public void testAfterTwoRuns() {
        control.next();        
        control.next();
        assertEquals(control.getRoot().getChildren(), control.getLeafs());
    }
}