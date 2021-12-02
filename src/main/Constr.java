package main;

import problem.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Constr {

    private Hashtable<Slot, Integer> courses_in_slot;
    private Hashtable<Slot, Integer> labs_in_slot;

    private final boolean DEBUG = false;

    public Constr() {
        courses_in_slot = new Hashtable<Slot, Integer>();
        labs_in_slot = new Hashtable<Slot, Integer>();
    }

    // Copy constructor
    public Constr(Constr toCopy) {
        courses_in_slot = new Hashtable<Slot, Integer>();
        labs_in_slot = new Hashtable<Slot, Integer>();

        Set<Slot> courses_in_slots_keys = toCopy.get_course_slot_list().keySet();
        for (Slot slot : courses_in_slots_keys) {
            this.courses_in_slot.put(slot, toCopy.get_course_slot_list().get(slot));
        }

        Set<Slot> labs_in_slots_keys = toCopy.get_lab_slot_list().keySet();
        for (Slot slot : labs_in_slots_keys) {
            this.labs_in_slot.put(slot, toCopy.get_lab_slot_list().get(slot));
        }
    }

    public boolean checkConstraints(ArrayList<Assignment> assignments) {
        if (assignments == null || assignments.size() == 0)
            return true;
        int last_index = assignments.size() - 1;

        Assignment new_addition = assignments.get(last_index);
        if (assignmentFails(new_addition))
            return false;

        for (Assignment assignment : assignments.subList(0, last_index)) {

            if (conflictingCourseWithLab(new_addition, assignment)) {
                if (DEBUG)
                    System.out.println("conflict course with lab");
                return false;
            }

            if (failsCompatability(new_addition, assignment)) {
                if (DEBUG)
                    System.out.println("conflict compatability");
                return false;
            }

            if (collisionOn500LevelCourses(new_addition, assignment)) {
                if (DEBUG)
                    System.out.println("conflict 500 level course");
                return false;
            }

        }

        return true;
    }

    private boolean assignmentFails(Assignment assignment) {

        if (failsCorrectSlotType(assignment)) {
            if (DEBUG)
                System.out.println("failed correct slot type");
            return true;
        }
        if (failsMaxElements(assignment)) {
            if (DEBUG)
                System.out.println("failed max elements");
            return true;
        }
        if (failsUnwanted(assignment)) {
            if (DEBUG)
                System.out.println("failed unwanted");
            return true;
        }
        if (failsEveningSlot(assignment)) {
            if (DEBUG)
                System.out.println("failed evening slot");
            return true;
        }
        if (failsTuesdayCourseRestriction(assignment)) {
            if (DEBUG)
                System.out.println("failed tuesday course restriction");
            return true;
        }
        if (failsPlacementOf813And913(assignment)) {
            if (DEBUG)
                System.out.println("failed 813/913");
            return true;
        }
        return false;
    }

    private boolean failsCorrectSlotType(Assignment assignment) {
        Slot slot = assignment.getSlot();
        Element element = assignment.getElement();

        if (element instanceof Course) {
            Course course = (Course) element;
            if (course.getDepartment() == "CPSC" &&
                    ( course.getNumber() == 913 || course.getNumber() == 813 ))
                return slot instanceof CourseSlot;
        }

        if (slot instanceof LabSlot)
            return !(element instanceof Lab);
        return !(element instanceof Course);
    }

    private boolean failsMaxElements(Assignment assignment) {
        Slot slot = assignment.getSlot();
        if (slot instanceof CourseSlot) {
            incrementElementCount(courses_in_slot, slot);
            if (courses_in_slot.get(slot) > slot.getMax()) {
                return true;
            }
        } else {
            incrementElementCount(labs_in_slot, slot);
            if (labs_in_slot.get(slot) > slot.getMax()) {
                return true;
            }
        }
        return false;
    }

    private void incrementElementCount(Hashtable<Slot, Integer> elements_in_slot, Slot slot) {
        if (elements_in_slot.containsKey(slot)) {
            elements_in_slot.put(slot, elements_in_slot.get(slot) + 1);
        } else {
            elements_in_slot.put(slot, 1);
        }
    }

    private boolean failsUnwanted(Assignment assignment) {

        ArrayList<Assignment> unwanted_assignments = Parser.getUnwanted();
        for (Assignment unwanted : unwanted_assignments) {
            if (unwanted.equals(assignment))
                return true;
        }
        return false;
    }

    private boolean failsEveningSlot(Assignment assignment) {

        Slot slot = assignment.getSlot();
        Element element = assignment.getElement();

        if (!(element instanceof Course))
            return false;

        Course course = (Course) element;

        int firstDigit = getFirstDigit(course.getSection());
        if (firstDigit != 9)
            return false;

        return slot.getStartTime().isBefore(LocalTime.of(18, 0));
    }

    private int getFirstDigit(int courseNumber) {
        int firstDigit = courseNumber;

        while (firstDigit > 9) {
            firstDigit /= 10;
        }

        return firstDigit;
    }

    private boolean failsTuesdayCourseRestriction(Assignment a) {
        if (a.getElement() instanceof Lab)
            return false;

        Course course = (Course) a.getElement();
        if (!course.getDepartment().equals("CPSC"))
            return false;

        Slot slot = a.getSlot();
        if (slot.getDay() != Day.TU)
            return false;

        LocalTime eleven = LocalTime.of(11, 00);
        return slot.getStartTime().compareTo(eleven) == 0;

    }

    private boolean failsPlacementOf813And913(Assignment assignment) {

        Element element = assignment.getElement();

        if (!(element instanceof Course))
            return false;

        Course course = (Course) element;
        int course_number = course.getNumber();

        if (course.getDepartment() != "CPSC" || (course_number != 813 && course_number != 913))
            return false;

        Slot slot = assignment.getSlot();

        return slot.getDay() != Day.TU || slot.getStartTime() != LocalTime.parse("18:00");
    }

    private boolean conflictingCourseWithLab(Assignment first, Assignment second) {

        Element firstElement = first.getElement();
        Element secondElement = second.getElement();

        if (firstElement.getClass().equals(secondElement.getClass()))
            return false;

        Slot firstSlot = first.getSlot();
        Slot secondSlot = second.getSlot();

        Lab lab;
        Course course;
        if (firstElement instanceof Lab) {
            lab = (Lab) firstElement;
            course = (Course) secondElement;
        } else {
            lab = (Lab) secondElement;
            course = (Course) firstElement;
        }

        Course lab_course = lab.getCourse();

        if (course.equals(lab_course) && slotsOverlap(firstSlot, secondSlot))
            return true;

        return false;
    }

    private boolean slotsOverlap(Slot one, Slot two) {

        Day dayOne = one.getDay();
        Day dayTwo = two.getDay();

        if (dayOne == dayTwo || (dayOne == Day.MO && dayTwo == Day.FR)
                || (dayTwo == Day.MO && dayOne == Day.FR)) {
            LocalTime startOne = one.getStartTime();
            LocalTime startTwo = two.getStartTime();
            if (startOne.equals(startTwo)) {
                return true;
            }
            LocalTime endOne = one.getEndTime();
            LocalTime endTwo = two.getEndTime();
            if (endOne.equals(endTwo)) {
                return true;
            }
            if (startTwo.isAfter(startOne) && startTwo.isBefore(endOne)
                    || endTwo.isAfter(startOne) && endTwo.isBefore(endOne)) {
                return true;
            }
        }
        return false;
    }

    private boolean failsCompatability(Assignment first, Assignment second) {
        Element firstElement = first.getElement();
        Element secondElement = second.getElement();

        ArrayList<Element[]> notCompatible = Parser.getNotCompatible();

        for (Element[] elem : notCompatible) {
            if ((firstElement.equals(elem[0]) && secondElement.equals(elem[1])
                    && slotsOverlap(first.getSlot(), second.getSlot()))
                    || (secondElement.equals(elem[0]) && firstElement.equals(elem[1])
                    && slotsOverlap(first.getSlot(), second.getSlot()))) {
                return true;
            }
        }

        return false;
    }

    private boolean collisionOn500LevelCourses(Assignment first, Assignment second) {
        Element firstElement = first.getElement();
        Element secondElement = second.getElement();

        if (!(firstElement instanceof Course) || !(secondElement instanceof Course))
            return false;

        Course firstCourse = (Course) firstElement;
        Course secondCourse = (Course) secondElement;

        int firstCourseLevel = getFirstDigit(firstCourse.getNumber());
        int secondCourseLevel = getFirstDigit(secondCourse.getNumber());
        if (firstCourseLevel != 5 || secondCourseLevel != 5)
            return false;

        Slot firstSlot = first.getSlot();
        Slot secondSlot = second.getSlot();

        return slotsOverlap(firstSlot, secondSlot);
    }

    public Hashtable<Slot, Integer> get_course_slot_list() {
        return courses_in_slot;
    }

    public Hashtable<Slot, Integer> get_lab_slot_list() {
        return labs_in_slot;
    }

}