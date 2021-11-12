package problem;

import java.time.LocalTime;

public class LabSlot extends Slot {

    public LabSlot(Day day, LocalTime startTime, int min, int max) {
        super(day, startTime, min, max);
    }

    @Override
    boolean isValid() {
        
        return false;
    }

}