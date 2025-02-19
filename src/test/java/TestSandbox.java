import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.ravs788.config.TestEnvFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
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

   @Test
    // @RepeatedTest(10)
    void assertConfigUsage(){
        final Config CONFIG = TestEnvFactory.getInstance().getConfig();

        log.info(CONFIG.getString("TEST_ENV"));
        log.info(CONFIG.getString("CREATE_EMPLOYEE_ENDPOINT"));
        log.info(CONFIG.getString("ADMIN_LOGIN"));
        log.info(CONFIG.getString("ADMIN_NAME"));
    }
}
