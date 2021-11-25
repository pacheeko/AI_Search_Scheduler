package main;

import java.time.LocalTime;

import problem.CourseSlot;
import problem.Day;
import problem.LabSlot;

public class ValidateInput {

    private static LocalTime[] course_start_times_monday = { LocalTime.parse("8:00"), LocalTime.parse("9:00"),
            LocalTime.parse("10:00"), LocalTime.parse("11:00"), LocalTime.parse("12:00"), LocalTime.parse("13:00"),
            LocalTime.parse("14:00"), LocalTime.parse("15:00"), LocalTime.parse("16:00"), LocalTime.parse("18:00"),
            LocalTime.parse("19:00"), LocalTime.parse("20:00"), };

    private static LocalTime[] course_start_times_tuesday = { LocalTime.parse("8:00"), LocalTime.parse("9:30"),
            LocalTime.parse("11:00"), LocalTime.parse("12:30"), LocalTime.parse("14:00"), LocalTime.parse("15:30"),
            LocalTime.parse("17:00"), LocalTime.parse("18:30"), };

    private static LocalTime[] lab_start_times_monday = { LocalTime.parse("8:00"), LocalTime.parse("9:00"),
            LocalTime.parse("10:00"), LocalTime.parse("11:00"), LocalTime.parse("12:00"), LocalTime.parse("13:00"),
            LocalTime.parse("14:00"), LocalTime.parse("15:00"), LocalTime.parse("16:00"), LocalTime.parse("17:00"),
            LocalTime.parse("18:00"), LocalTime.parse("19:00"), LocalTime.parse("20:00"), };

    private static LocalTime[] lab_start_times_tuesday = lab_start_times_monday;

    private static LocalTime[] lab_start_times_friday = { LocalTime.parse("8:00"), LocalTime.parse("10:00"),
            LocalTime.parse("12:00"), LocalTime.parse("14:00"), LocalTime.parse("16:00"), LocalTime.parse("18:00"), };

    // courseSlot - Checks the validity of the Course Slot slot
    //
    // slot - The slot in question
    public static boolean courseSlot(CourseSlot slot) {

        Day day = slot.getDay();
        LocalTime start_time = slot.getStartTime();
        switch (day) {
        case MO:
            return validStartTime(start_time, course_start_times_monday);
        case TU:
            return validStartTime(start_time, course_start_times_tuesday);
        case FR:
            return false;
        default:
            return false;
        }
    }

    // labSlot - Checks the validity of the Lab Slot slot
    //
    // slot - The slot in question
    public static boolean labSlot(LabSlot slot) {
        Day day = slot.getDay();
        LocalTime start_time = slot.getStartTime();
        switch (day) {
        case MO:
            return validStartTime(start_time, lab_start_times_monday);
        case TU:
            return validStartTime(start_time, lab_start_times_tuesday);
        case FR:
            return validStartTime(start_time, lab_start_times_friday);
        default:
            return false;
        }
    }

    // validStartTime - checks if the startTime is in the list validTimes
    private static boolean validStartTime(LocalTime startTime, LocalTime[] validTimes) {
        for (LocalTime valid_start_time : validTimes) {
            if (startTime.equals(valid_start_time))
                return true;
        }
        return false;
    }
}
