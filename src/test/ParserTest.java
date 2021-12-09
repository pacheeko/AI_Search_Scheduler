package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Parser;

public class ParserTest {

    @BeforeEach
    public void init() {
        try {
        	Parser.parseFile("test_files/Inputs/junitTestExample.txt");
        } catch (Exception e) {
            //fail();
        }
    }

    @Test
    public void testParseName() {
        assertEquals("ShortExample", Parser.getName());
    }

    @Test
    public void testParseCourses() {
        assertEquals(9, Parser.getCourses().size());
        assertEquals("CPSC", Parser.getCourses().get(0).getDepartment());
        assertEquals(433, Parser.getCourses().get(0).getNumber());
        assertEquals(1, Parser.getCourses().get(0).getSection());
        assertEquals("CPSC", Parser.getCourses().get(1).getDepartment());
        assertEquals(433, Parser.getCourses().get(1).getNumber());
        assertEquals(2, Parser.getCourses().get(1).getSection());
    }


    @Test
    public void testParseLabs() {
        assertEquals(4, Parser.getLabs().size());
        assertEquals(Parser.getCourses().get(0), Parser.getLabs().get(0).getCourse());
        assertEquals(1, Parser.getLabs().get(0).getNumber());
    }
}
