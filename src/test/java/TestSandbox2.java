import org.junit.jupiter.api.Test;
import org.ravs788.config.annotations.SmokeTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSandbox2 {
    @SmokeTest
    void assertThatTrueIsTrue() throws InterruptedException{
        assertTrue(true, "true is true");
        Thread.sleep(1000);
    }

    @SmokeTest
    void assertThatDayIsDay() throws InterruptedException{
        assertEquals("day","day","true is true");
        Thread.sleep(1000);
    }
}
