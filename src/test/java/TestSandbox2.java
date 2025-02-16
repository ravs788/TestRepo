import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSandbox2 {
    @Test
    void assertThatTrueIsTrue(){
        assertTrue(true, "true is true");
    }

    @Test
    void assertThatDayIsDay(){
        assertEquals("day","day","true is true");
    }
}
