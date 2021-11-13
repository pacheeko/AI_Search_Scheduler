package problem;

import java.time.LocalTime;

public abstract class Slot {
    Day day;
    LocalTime startTime;
    int min;
    int max;

    //Course = 0, Lab = 1
    int type;

    public Slot(Day day, LocalTime startTime, int max, int min, int type) {
        this.day = day;
        this.startTime = startTime;
        this.min = min;
        this.max = max;
        this.type = type;
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

    public int getType(){
        return type;
    }

    public LocalTime getEndTime(){
        LocalTime endTime = startTime.plusHours(1);

        switch(day){
            case TU:{
                if(type == 0) {
                    endTime = startTime.plusHours(1).plusMinutes(30);
                }
            }
            break;
            case FR:{
                if(type == 1){
                    endTime = startTime.plusHours(2);
                }
            }
            break;
        }
        return endTime;
    }


}


