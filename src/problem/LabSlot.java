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

}