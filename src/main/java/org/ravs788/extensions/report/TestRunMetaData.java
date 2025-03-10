package org.ravs788.extensions.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.ravs788.config.TestEnvFactory;
import org.ravs788.extensions.TimingExtension;


import java.security.cert.Extension;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestRunMetaData {
    private static  final String PROJECT = "TestProject";
    private static  final String TEST_RUN_TIME = LocalDateTime.now(ZoneId.of("UTC")).toString();
    private static  final String TRIGGERED_BY = getTriggeredBy();

    private String project;

    @JsonProperty("test-run")
    private String testRun;

    @JsonProperty("test-class")
    private String testClass;

    @JsonProperty("test-name")
    private String testName;

    private String status;
    private String reason;

    @JsonProperty("triggered-by")
    private String triggeredBy;

    @JsonProperty("time (Sec)")
    private String duration;

    public TestRunMetaData setBody(ExtensionContext context){
        project = PROJECT;
        testRun = TEST_RUN_TIME;

        testClass = context.getTestClass().orElseThrow().getSimpleName();
        testName = context.getDisplayName();

        setDuration();

        setTestStatusAndReason(context);
        triggeredBy = TRIGGERED_BY;

        return  this;
    }

    private void setDuration() {
        if(TimingExtension.getTestExecutionTimeThread() >= 5){
            duration = TimingExtension.getTestExecutionTimeThread() + "⏰";
        }else{
            duration = String.valueOf(TimingExtension.getTestExecutionTimeThread());
        }

        log.info("duration {}", duration);
    }

    private void setTestStatusAndReason(ExtensionContext context) {
        boolean testStatus = context.getExecutionException().isPresent();
        if(testStatus) {
            status = "❌";
            reason = "🐞 " + context.getExecutionException().toString();
        } else {
            status = "✅";
            reason = "🌻";
        }
    }

    private static String getTriggeredBy(){
        Config config = TestEnvFactory.getInstance().getConfig();
        if(config.getString("TRIGGERED_BY").isEmpty())
            return System.getProperty("user.name");
        else
            return config.getString("TRIGGERED_BY");
    }
}
