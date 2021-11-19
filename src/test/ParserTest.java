//package test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Parser;

public class ParserTest {
    
    private Parser parser;
    
    @BeforeEach
    public void init() {
        parser = new Parser();
        try {
            parser.parseFile("C:\\\\Users\\\\mrnoo\\\\Desktop\\\\test_example.txt");
        } catch (Exception e) {
            fail();
        }        
    }
    
    @Test
    public void testParseName() {
        assertEquals("ShortExample", parser.getName());
    }

    @Test
    public void testParseCourses() {
        assertEquals(4, parser.getCourses().size());
        assertEquals("CPSC", parser.getCourses().get(0).getDepartment());
        assertEquals(433, parser.getCourses().get(0).getNumber());
        assertEquals(1, parser.getCourses().get(0).getSection());
        assertEquals("CPSC", parser.getCourses().get(1).getDepartment());
        assertEquals(433, parser.getCourses().get(1).getNumber());
        assertEquals(2, parser.getCourses().get(1).getSection());        
    }


    @Test
    public void testParseLabs() {
        assertEquals(4, parser.getLabs().size());        
        assertEquals(parser.getCourses().get(0), parser.getLabs().get(0).getCourse());
        assertEquals(1, parser.getLabs().get(0).getNumber());
    }
}
