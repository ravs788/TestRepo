package org.ravs788.extensions;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
  private static ThreadLocal<Double> testExecutionTimeThread = new ThreadLocal<>();
  private long startTimeMethod;

  public static Double getTestExecutionTimeThread() {
    return testExecutionTimeThread.get();
  }

  public static void removeTestExecutionTimeThread() {
    testExecutionTimeThread.remove();
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) {
    startTimeMethod = System.currentTimeMillis();
  }

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
    double duration = (System.currentTimeMillis() - startTimeMethod) / 1000.0;
    log.info("Test {} took {} Seconds.", context.getRequiredTestMethod().getName(), duration);

    testExecutionTimeThread.set(duration);
  }
}
