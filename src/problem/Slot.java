package problem;

import java.time.LocalTime;

public abstract class Slot {
    Day day;
    LocalTime startTime;
    int min;
    int max;

    public Slot(Day day, LocalTime startTime, int max, int min) {
        this.day = day;
        this.startTime = startTime;
        this.min = min;
        this.max = max;
    }
    
    abstract boolean isValid();

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    
    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public Day getDay() {
        return this.day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime time) {
        this.startTime = time;
    }

    public String getInfo(){
        String info = "(";
        info += day.toString() + " at ";
        info += startTime.toString();
        return info + ")";
    }

    public abstract LocalTime getEndTime();
}


