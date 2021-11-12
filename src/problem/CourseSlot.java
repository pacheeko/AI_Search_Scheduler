package problem;

import java.time.LocalTime;

public class CourseSlot extends Slot {

    public CourseSlot(Day day, LocalTime startTime, int max, int min) {
        super(day, startTime, max, min);
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