package main.problem;

import java.time.LocalTime;

public class CourseSlot extends Slot {

    public CourseSlot(Day day, LocalTime startTime, int min, int max) {
        super(day, startTime, min, max);
    }

    @Override
    boolean isValid() {        
        return isDayValid() && isStartTimeValid();
    }

    private boolean isDayValid() {
        return day != Day.FR;
    }

    private boolean isStartTimeValid() {
        return true;
    }

}