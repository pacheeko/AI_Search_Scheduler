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

    @Override
    public LocalTime getEndTime() {
        {
            LocalTime endTime = startTime.plusHours(1);
            switch (day) {
            case MO:
            case FR:
                endTime = startTime.plusHours(1);
                break;
            case TU:
                endTime = startTime.plusHours(1).plusMinutes(30);                
                break;
            }
            return endTime;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == this) return true;
    	else if (!(obj instanceof CourseSlot)) return false;
    	else {
    		CourseSlot slot = (CourseSlot) obj;
    		return (slot.day.equals(this.day) && slot.getStartTime().equals(this.getStartTime()));
    	}
    }

}