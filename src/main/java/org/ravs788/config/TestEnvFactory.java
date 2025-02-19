package org.ravs788.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

@Slf4j
public class TestEnvFactory {
    private static final TestEnvFactory UNIQUE_INSTANCE = new TestEnvFactory();
    private Config config;

    private TestEnvFactory() {
        config = setConfig();
    }

    public Config getConfig(){
        return config;
    }

    public static TestEnvFactory getInstance(){
        return UNIQUE_INSTANCE;
    }

    private Config setConfig(){
        log.info("setConfig called");
        // As per https://github.com/lightbend/config?tab=readme-ov-file#standard-behavior
        config = ConfigFactory.load();

        TestEnv testEnv = config.getEnum(TestEnv.class, "TEST_ENV");
        String testEnvName =  testEnv.toString().toLowerCase();
        String testEnvDirPath = String.format("src/main/resources/%s", testEnvName);
        File testEnvDir = new File(testEnvDirPath);
        for (File file: Objects.requireNonNull(testEnvDir.listFiles())){
            Config childConf = ConfigFactory.load(String.format("%s/%s", testEnvName, file.getName()));
            config = config.withFallback(childConf);
        }

        return config;
    }

}
