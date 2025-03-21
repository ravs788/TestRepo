package org.ravs788.extensions;

import java.io.IOException;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.ravs788.extensions.report.PublishResults;
import org.ravs788.extensions.report.TestRunMetaData;

public class ReportingExtension implements AfterEachCallback {

  public void afterEach(ExtensionContext context) throws IOException {
    TestRunMetaData testRunMetaData = new TestRunMetaData().setBody(context);
    PublishResults.toElastic(testRunMetaData);
  }
}
