import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.ravs788.config.TestEnvFactory;
import org.ravs788.config.annotations.SmokeTest;

import static org.junit.jupiter.api.Assertions.*;

@Tag("service-A")
@Slf4j
public class TestServiceA {

    @SmokeTest
    void assertThatTrueIsTrue(){
        assertTrue(true, "true is true");
    }

    @SmokeTest
    void assertThatTestForChosenEnvRuns(){
        Config CONFIG = TestEnvFactory.getInstance().getConfig();
        String expectedEnv = CONFIG.getString("TEST_ENV");
        log.info("expectedEnv is {}", expectedEnv);
        assertEquals(expectedEnv, CONFIG.getString("TEST_ENV"), "TEST_ENV");
    }

}
