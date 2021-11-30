package problem;

import java.time.LocalTime;

public class LabSlot extends Slot {

    public LabSlot(Day day, LocalTime startTime, int max, int min) {
        super(day, startTime, max, min);
    }

    @Override
    boolean isValid() {
        return false;
    }

    @Override
    public LocalTime getEndTime() {
        {
            LocalTime endTime = startTime.plusHours(1);

            switch (day) {
            case MO:
            case TU:
                endTime = startTime.plusHours(1);                     
                break;
            case FR:
                endTime = startTime.plusHours(2);
                break;
            }
            return endTime;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == this) return true;
    	else if (!(obj instanceof LabSlot)) return false;
    	else {
    		LabSlot slot = (LabSlot) obj;
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
        result = 31 * result + (max ^ (max >>> 32));
        result = 31 * result + day.hashCode();
        result = 31 * result + startTime.hashCode();
        return result;
    }

    @Override
    public LabSlot clone() {        
        LabSlot clone = (LabSlot) super.clone();
        return (LabSlot) clone;
    }

}