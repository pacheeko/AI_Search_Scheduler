//package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.SearchModel;
import problem.Problem;
import problem.Element;
import problem.Course;
import problem.Lab;
import problem.Slot;
import problem.CourseSlot;
import problem.LabSlot;
import problem.Day;

public class SearchModelTest {
    private ArrayList<Element> elements;
    private ArrayList<Slot> slots;
    private Problem problem;

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
        problem = new Problem(elements);
    }

    @Test
    public void testDivSubProblems() {
        ArrayList<Problem> subProblems = SearchModel.Div(problem, slots);
        assertEquals(slots.size(), subProblems.size());
    }

    @Test
    public void testDivAssignments() {
        ArrayList<Problem> subProblems = SearchModel.Div(problem, slots);
        assertEquals(1, subProblems.get(0).getAssignments().size());
    }
}