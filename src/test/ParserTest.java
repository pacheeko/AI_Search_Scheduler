package test;

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
            parser.parseFile("/home/jp/School/CPSC433/CPSC433_PROJECT/tests/test_input");
        } catch (Exception e) {
            fail();
        }        
    }
    
    @Test
    public void testParser() {
        assertEquals("ShortExample", parser.getName());
    }
}
