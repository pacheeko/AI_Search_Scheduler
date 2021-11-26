package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import problem.CourseSlot;
import problem.Day;
import problem.LabSlot;
import main.ValidateInput;

public class ValidateInputTest {

    @Test
    public void testAllValidCourseSlotsMonday() {
        LocalTime times[] = {
            LocalTime.parse("08:00"),
            LocalTime.parse("09:00"),
            LocalTime.parse("10:00"),
            LocalTime.parse("11:00"),
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"),
            LocalTime.parse("16:00"),
            LocalTime.parse("17:00"),
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00"),
        };

        for(LocalTime time : times) {
            CourseSlot slot = new CourseSlot(Day.MO, time, 0, 0);
            assertTrue(ValidateInput.courseSlot(slot));
        }        
    }

    @Test
    public void testAllValidCourseSlotsTuesday() {
        LocalTime times[] = {
            LocalTime.parse("08:00"),
            LocalTime.parse("09:30"),
            LocalTime.parse("11:00"),
            LocalTime.parse("12:30"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:30"),
            LocalTime.parse("17:00"),
            LocalTime.parse("18:30"),
        };

        for(LocalTime time : times) {
            CourseSlot slot = new CourseSlot(Day.TU, time, 0, 0);
            assertTrue(ValidateInput.courseSlot(slot));
        }        
    }

    @Test
    public void testAllValidLabSlotsMonday() {
        LocalTime times[] = {
            LocalTime.parse("08:00"),
            LocalTime.parse("09:00"),
            LocalTime.parse("10:00"),
            LocalTime.parse("11:00"),
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"),
            LocalTime.parse("16:00"),
            LocalTime.parse("17:00"),
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00"),
        };

        for(LocalTime time : times) {
            LabSlot slot = new LabSlot(Day.MO, time, 0, 0);
            assertTrue(ValidateInput.labSlot(slot));
        }        
    }


    @Test
    public void testAllValidLabSlotsTuesday() {
        LocalTime times[] = {
            LocalTime.parse("08:00"),
            LocalTime.parse("09:00"),
            LocalTime.parse("10:00"),
            LocalTime.parse("11:00"),
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"),
            LocalTime.parse("16:00"),
            LocalTime.parse("17:00"),
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00"),
        };

        for(LocalTime time : times) {
            LabSlot slot = new LabSlot(Day.TU, time, 0, 0);
            assertTrue(ValidateInput.labSlot(slot));
        }        
    }


    @Test
    public void testAllValidLabSlotsFriday() {
        LocalTime times[] = {
            LocalTime.parse("08:00"),
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"),
            LocalTime.parse("14:00"),
            LocalTime.parse("16:00"),
            LocalTime.parse("18:00"),
        };

        for(LocalTime time : times) {
            LabSlot slot = new LabSlot(Day.FR, time, 0, 0);
            assertTrue(ValidateInput.labSlot(slot));
        }        
    }


    @Test
    public void testInvalidCourseSlotsMonday() {
        LocalTime times[] = {
            LocalTime.parse("06:00"),
            LocalTime.parse("07:00"),
            LocalTime.parse("08:19"),
            LocalTime.parse("09:02"),
            LocalTime.parse("10:10"),
            LocalTime.parse("11:12"),
            LocalTime.parse("12:59"),
            LocalTime.parse("13:01"),
            LocalTime.parse("14:15"),
            LocalTime.parse("15:22"),
            LocalTime.parse("16:03"),
            LocalTime.parse("17:30"),
            LocalTime.parse("18:02"),
            LocalTime.parse("19:01"),
            LocalTime.parse("20:10"),
            LocalTime.parse("21:00"),
            LocalTime.parse("22:00"),
        };

        for(LocalTime time : times) {
            CourseSlot slot = new CourseSlot(Day.MO, time, 0, 0);
            assertFalse(ValidateInput.courseSlot(slot));
        }        
    }

    @Test
    public void testInvalidCourseSlotsTuesday() {
        LocalTime times[] = {
            LocalTime.parse("07:30"),
            LocalTime.parse("08:30"),
            LocalTime.parse("09:00"),
            LocalTime.parse("11:30"),
            LocalTime.parse("12:00"),
            LocalTime.parse("14:30"),
            LocalTime.parse("15:00"),
            LocalTime.parse("17:30"),
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00"),
        };

        for(LocalTime time : times) {
            CourseSlot slot = new CourseSlot(Day.TU, time, 0, 0);
            assertFalse(ValidateInput.courseSlot(slot));            
        }        
    }

    @Test
    public void testInvalidCourseSlotsFriday() {
        LocalTime times[] = {
            LocalTime.parse("08:00"),
            LocalTime.parse("09:00"),
            LocalTime.parse("10:00"),
            LocalTime.parse("11:00"),
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"),
            LocalTime.parse("16:00"),
            LocalTime.parse("17:00"),
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00"),
            LocalTime.parse("07:30"),
            LocalTime.parse("08:30"),
            LocalTime.parse("09:00"),
            LocalTime.parse("11:30"),
            LocalTime.parse("12:00"),
            LocalTime.parse("14:30"),
            LocalTime.parse("15:00"),
            LocalTime.parse("17:30"),
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00"),
            LocalTime.parse("08:00"),
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"),
            LocalTime.parse("14:00"),
            LocalTime.parse("16:00"),
            LocalTime.parse("18:00"),
        };

        for(LocalTime time : times) {
            CourseSlot slot = new CourseSlot(Day.FR, time, 0, 0);
            assertFalse(ValidateInput.courseSlot(slot));            
        }        
    }


    @Test
    public void testInvalidLabSlotsMonday() {
        LocalTime times[] = {
            LocalTime.parse("08:30"),
            LocalTime.parse("09:04"),
            LocalTime.parse("10:20"),
            LocalTime.parse("11:40"),
            LocalTime.parse("12:02"),
            LocalTime.parse("13:34"),
            LocalTime.parse("14:12"),
            LocalTime.parse("15:03"),
            LocalTime.parse("16:59"),
            LocalTime.parse("17:40"),
            LocalTime.parse("18:10"),
            LocalTime.parse("19:40"),
            LocalTime.parse("20:50"),
            LocalTime.parse("21:06"),
        };

        for(LocalTime time : times) {
            LabSlot slot = new LabSlot(Day.MO, time, 0, 0);
            assertFalse(ValidateInput.labSlot(slot));            
        }        
    }


    @Test
    public void testInvalidLabSlotsTuesday() {
        LocalTime times[] = {
            LocalTime.parse("08:30"),
            LocalTime.parse("09:04"),
            LocalTime.parse("10:20"),
            LocalTime.parse("11:40"),
            LocalTime.parse("12:02"),
            LocalTime.parse("13:34"),
            LocalTime.parse("14:12"),
            LocalTime.parse("15:03"),
            LocalTime.parse("16:59"),
            LocalTime.parse("17:40"),
            LocalTime.parse("18:10"),
            LocalTime.parse("19:40"),
            LocalTime.parse("20:50"),
            LocalTime.parse("21:06"),
        };

        for(LocalTime time : times) {
            LabSlot slot = new LabSlot(Day.TU, time, 0, 0);
            assertFalse(ValidateInput.labSlot(slot));            
        }        
    }


    @Test
    public void testInvalidLabSlotsFriday() {
        LocalTime times[] = {
            LocalTime.parse("07:00"),
            LocalTime.parse("08:10"),
            LocalTime.parse("09:00"),
            LocalTime.parse("10:34"),
            LocalTime.parse("11:00"),
            LocalTime.parse("12:22"),
            LocalTime.parse("13:00"),
            LocalTime.parse("14:20"),
            LocalTime.parse("15:12"),
            LocalTime.parse("16:14"),
            LocalTime.parse("17:00"),
            LocalTime.parse("18:10"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00"),
        };

        for(LocalTime time : times) {
            LabSlot slot = new LabSlot(Day.FR, time, 0, 0);
            assertFalse(ValidateInput.labSlot(slot));            
        }        
    }
}
