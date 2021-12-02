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
        if (obj == this)
            return true;
        else if (!(obj instanceof CourseSlot))
            return false;
        else {
            CourseSlot slot = (CourseSlot) obj;
            Boolean result = slot.day.equals(this.day);
            result = result && slot.getStartTime().equals(this.getStartTime());
            result = result && slot.min == this.min;
            result = result && slot.max == this.max;
            return result;
        }
    }

    @Override
    public int hashCode() {
        int result = (int) (min ^ (min >>> 32));
        result = 37 * result + (max ^ (max >>> 32));
        result = 37 * result + day.hashCode();
        result = 37 * result + startTime.hashCode();
        return result;
    }

    @Override
    protected CourseSlot clone() {
        CourseSlot clone = (CourseSlot) super.clone();    
        return clone;
    }
}