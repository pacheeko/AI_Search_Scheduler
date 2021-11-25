package main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import problem.CourseSlot;
import problem.Day;
import problem.LabSlot;

public class ValidateInput {
	
	private static DateTimeFormatter format = DateTimeFormatter.ofPattern("H:m");

    private static LocalTime[] course_start_times_monday = { LocalTime.parse("8:00", format), LocalTime.parse("9:00", format),
            LocalTime.parse("10:00", format), LocalTime.parse("11:00", format), LocalTime.parse("12:00", format), LocalTime.parse("13:00", format),
            LocalTime.parse("14:00", format), LocalTime.parse("15:00", format), LocalTime.parse("16:00", format), LocalTime.parse("18:00", format),
            LocalTime.parse("19:00", format), LocalTime.parse("20:00", format), };

    private static LocalTime[] course_start_times_tuesday = { LocalTime.parse("8:00", format), LocalTime.parse("9:30", format),
            LocalTime.parse("11:00", format), LocalTime.parse("12:30", format), LocalTime.parse("14:00", format), LocalTime.parse("15:30", format),
            LocalTime.parse("17:00", format), LocalTime.parse("18:30", format), };

    private static LocalTime[] lab_start_times_monday = { LocalTime.parse("8:00", format), LocalTime.parse("9:00", format),
            LocalTime.parse("10:00", format), LocalTime.parse("11:00", format), LocalTime.parse("12:00", format), LocalTime.parse("13:00", format),
            LocalTime.parse("14:00", format), LocalTime.parse("15:00", format), LocalTime.parse("16:00", format), LocalTime.parse("17:00", format),
            LocalTime.parse("18:00", format), LocalTime.parse("19:00", format), LocalTime.parse("20:00", format), };

    private static LocalTime[] lab_start_times_tuesday = lab_start_times_monday;

    private static LocalTime[] lab_start_times_friday = { LocalTime.parse("8:00", format), LocalTime.parse("10:00", format),
            LocalTime.parse("12:00", format), LocalTime.parse("14:00", format), LocalTime.parse("16:00", format), LocalTime.parse("18:00", format), };

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
