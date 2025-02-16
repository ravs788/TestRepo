import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSandbox {
    @Test
    void assertThatTrueIsTrue() throws InterruptedException{
        assertTrue(true, "true is true");
        Thread.sleep(1000);
    }

    @Tag("failing")
    @Test
    void assertThatDayIsDay() throws InterruptedException{
        assertEquals("day","night","true is true");
        Thread.sleep(1000);
    }

    @Tag("flaky")
    @Test
    void createAFlakyTest(){
        Random random = new Random();
        int value = random.nextInt(10000);
        if(value%2==0){
            assertTrue(true,"value is even");
        }
        else{
            assertTrue(false, "value is odd");
        }
    }
}
