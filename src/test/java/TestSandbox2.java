import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSandbox2 {
    @Test
    void assertThatTrueIsTrue() throws InterruptedException{
        assertTrue(true, "true is true");
        Thread.sleep(1000);
    }

    @Test
    void assertThatDayIsDay() throws InterruptedException{
        assertEquals("day","day","true is true");
        Thread.sleep(1000);
    }
}
