import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestSandbox {
    private static final Logger log = LoggerFactory.getLogger(TestSandbox.class);

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
        log.info("This is the random number:- %d".formatted(value));
        if(value%2==0){
            assertTrue(true,"value is even");
        }
        else{
            assertTrue(false, "value is odd");
        }
    }

    @Test
    void testLogging(){
        log.info("this is an information");
        log.debug("this is a debug statement");
        log.error("this is an error");
        log.warn("this is a warning");
        log.trace("this is a trace statement");
    }
}
