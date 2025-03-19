import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ravs788.config.TestEnvFactory;
import org.ravs788.annotations.FailingTest;
import org.ravs788.annotations.FlakyTest;
import org.ravs788.annotations.SmokeTest;

import setup.TestSetup;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
public class TestSandbox extends TestSetup {

    @SmokeTest
    void assertThatTrueIsTrue() throws InterruptedException{
        assertTrue(true, "true is true");
        Thread.sleep(1000);
    }

    @FailingTest
    void assertThatDayIsDay() throws InterruptedException{
        assertEquals("day","night","true is true");
        Thread.sleep(1000);
    }

    @FlakyTest
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

    @FailingTest
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

        assertAll("Config Test",
                () -> assertEquals("DEVELOP",CONFIG.getString("TEST_ENV"),"TEST_ENV"),
                () -> assertEquals("/employee/create",CONFIG.getString("CREATE_EMPLOYEE_ENDPOINT"),"CREATE_EMPLOYEE_ENDPOINT"),
                () -> assertEquals("develop-admin",CONFIG.getString("ADMIN_LOGIN"),"ADMIN_LOGIN"),
                () -> assertEquals("develop",CONFIG.getString("ADMIN_NAME"),"ADMIN_NAME"),
                () -> assertFalse(CONFIG.getBoolean("TOGGLE"), "TOGGLE"),
                () -> assertEquals(10,CONFIG.getInt("NO_OF_USERS"),"NO_OF_USERS")
                );

        log.info(CONFIG.getString("TEST_ENV"));
        log.info(CONFIG.getString("CREATE_EMPLOYEE_ENDPOINT"));
        log.info(CONFIG.getString("ADMIN_LOGIN"));
        log.info(CONFIG.getString("ADMIN_NAME"));
        log.info(String.valueOf(CONFIG.getBoolean("TOGGLE")));
        log.info(String.valueOf(CONFIG.getInt("NO_OF_USERS")));
    }
}
