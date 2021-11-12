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

}