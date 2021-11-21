package main;

import problem.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;

public class Constr {

    Hashtable<Slot, Integer> courses_in_slot;
    Hashtable<Slot, Integer> labs_in_slot;

    public Constr() {
        courses_in_slot = new Hashtable<Slot, Integer>();
        labs_in_slot = new Hashtable<Slot, Integer>();
    }

    public boolean checkConstraints(ArrayList<Assignment> assignments) {

        int last_index = assignments.size() - 1;
        int second_to_last_index = last_index - 1;

        Assignment new_addition = assignments.get(last_index);

        if (assignmentFails(new_addition))
            return false;

        for (Assignment assignment : assignments.subList(0, second_to_last_index)) {

            if (conflictingCourseWithLab(new_addition, assignment))
                return false;

            if (failsCompatability(new_addition, assignment))
                return false;

            if (collisionOn500LevelCourses(new_addition, assignment))
                return false;

        }

        return true;
    }

    private boolean assignmentFails(Assignment assignment) {

        if (failsCorrectSlotType(assignment))
            return true;
        if (failsMaxElements(assignment))
            return true;
        if (faislUnwanted(assignment))
            return true;
        if (failsEveningSlot(assignment))
            return true;
        if (failsTuesdayCourseRestriction(assignment))
            return true;
        if (failsPlacementOf813And913(assignment))
            return true;

        return false;
    }

    private boolean failsCorrectSlotType(Assignment assignment) {
        Slot slot = assignment.getSlot();
        Element element = assignment.getElement();

        if (slot instanceof LabSlot)
            return !(element instanceof Lab);
        return !(element instanceof Course);
    }

    private boolean failsMaxElements(Assignment assignment) {
        Slot slot = assignment.getSlot();
        if (slot instanceof CourseSlot) {
            incrementElemenentCount(courses_in_slot, slot);
            if (courses_in_slot.get(slot) > slot.getMax())
                return false;
        } else {
            incrementElemenentCount(labs_in_slot, slot);
            if (labs_in_slot.get(slot) > slot.getMax())
                return false;
        }
        return true;
    }

    private void incrementElemenentCount(Hashtable<Slot, Integer> elements_in_slot, Slot slot) {
        if (elements_in_slot.containsKey(slot))
            elements_in_slot.put(slot, elements_in_slot.get(slot) + 1);
        else
            elements_in_slot.put(slot, 1);
    }

    private boolean faislUnwanted(Assignment assignment) {

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

        int firstDigit = getFirstDigit(course.getNumber());
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
        Slot slot = a.getSlot();

        if (slot.getDay() != Day.TU)
            return false;

        LocalTime ten59 = LocalTime.of(10, 59);
        LocalTime twelve31 = LocalTime.of(12, 31);

        boolean start_between = slot.getStartTime().isAfter(ten59) && slot.getStartTime().isBefore(twelve31);

        if (start_between)
            return true;

        boolean end_between = slot.getEndTime().isAfter(ten59) && slot.getEndTime().isBefore(twelve31);

        return end_between;
    }

    private boolean failsPlacementOf813And913(Assignment assignment) {

        Element element = assignment.getElement();

        if (!(element instanceof Course))
            return false;

        Course course = (Course) element;
        int course_number = course.getNumber();

        if (course_number != 813 && course_number != 913)
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

        if (dayOne == dayTwo) {
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
            if (firstElement.equals(elem[0]) && secondElement.equals(elem[1])
                    || (secondElement.equals(elem[0]) && firstElement.equals(elem[1]))) {
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
}