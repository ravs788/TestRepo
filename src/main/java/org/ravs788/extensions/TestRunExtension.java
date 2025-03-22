package org.ravs788.extensions;

import static org.ravs788.utils.DateUtils.getDateAsString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class TestRunExtension
    implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
  AtomicBoolean isRunStarted = new AtomicBoolean(false);

  Path testRunReportsPath =
      Paths.get("target", "test-run-reports", getDateAsString("yyyy-MM-dd/HH-mm"));

  @Override
  public void beforeAll(ExtensionContext context) {
    try {
      if (isRunStarted.compareAndSet(false, true)) {
        log.info("Run this section only once at the beginning of the test run");
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put("TestRunExtension", this);

        Files.createDirectories(testRunReportsPath);
      }
    } catch (Exception e) {
      log.error("Exception in TestRunExtension: ", e);
      log.error(
          "🚨Terminating test run since tests depend on setup done in TestRunExtension class");
      System.exit(1);
    }
  }

  @Override
  public void close() throws Throwable {
    log.info("Run this section only once at the end of the test run");
  }
}
