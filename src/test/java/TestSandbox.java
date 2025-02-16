import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
    void CreateAFlakyTest(){
        long currentTimeStamp = System.currentTimeMillis();
        System.out.println(currentTimeStamp);
        if(currentTimeStamp%2==0){
            assertTrue(true, "time is even");
        }
        else {
            assertTrue(false, "time is odd");
        }
    }
}
