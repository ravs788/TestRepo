package org.ravs788.extensions.report;

import co.elastic.clients.transport.TransportUtils;
import com.typesafe.config.Config;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.ravs788.config.TestEnvFactory;

@Slf4j
public class ElasticLowLevelRestClientFactory {
  private static final Config CONFIG = TestEnvFactory.getInstance().getConfig();

  public static RestClient getRestClient(ElasticServerChoices ELASTIC_SERVER) {
    log.info("Getting client for ELASTIC_SERVER: {}", ELASTIC_SERVER);
    switch (ELASTIC_SERVER) {
      case ON_CLOUD:
        return getRestClientForCloud();
      case ON_LOCALHOST_SECURE:
        return getRestClientForLocalhostHTTPS();
      case ON_LOCALHOST_INSECURE:
        return getRestClientLocalhostHTTP();
      default:
        throw new IllegalStateException(
            String.format(
                "%s is not a valid HOST choice. Pick your HOST from %s.",
                ELASTIC_SERVER, java.util.Arrays.asList(ElasticServerChoices.class)));
    }
  }

  private static RestClient getRestClientLocalhostHTTP() {
    final String ELASTIC_HOST = CONFIG.getString("ON_LOCALHOST_INSECURE.ELASTIC_HOST");
    final int ELASTIC_PORT = CONFIG.getInt("ON_LOCALHOST_INSECURE.ELASTIC_PORT");

    return RestClient.builder(new HttpHost(ELASTIC_HOST, ELASTIC_PORT, "http")).build();
  }

  private static RestClient getRestClientForCloud() {
    final String ELASTIC_HOST = CONFIG.getString("ON_CLOUD.ELASTIC_HOST");
    final int ELASTIC_PORT = CONFIG.getInt("ON_CLOUD.ELASTIC_PORT");
    final String ELASTIC_API_KEY = CONFIG.getString("ON_CLOUD.ELASTIC_API_KEY");

    Header[] headers =
        new Header[] {
          new BasicHeader("Accept", "application/json"),
          new BasicHeader("Authorization", "ApiKey " + ELASTIC_API_KEY)
        };

    return RestClient.builder(new HttpHost(ELASTIC_HOST, ELASTIC_PORT, "https"))
        .setDefaultHeaders(headers)
        .build();
  }

  private static RestClient getRestClientForLocalhostHTTPS() {
    final String ELASTIC_HOST = CONFIG.getString("ON_LOCALHOST_SECURE.ELASTIC_HOST");
    final int ELASTIC_PORT = CONFIG.getInt("ON_LOCALHOST_SECURE.ELASTIC_PORT");
    final String ELASTIC_FINGERPRINT = CONFIG.getString("ON_LOCALHOST_SECURE.ELASTIC_FINGERPRINT");
    SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(ELASTIC_FINGERPRINT);

    final String ELASTIC_LOGIN = CONFIG.getString("ON_LOCALHOST_SECURE.ELASTIC_LOGIN");
    final String ELASTIC_PASSWORD = CONFIG.getString("ON_LOCALHOST_SECURE.ELASTIC_PASSWORD");

    BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
    credsProv.setCredentials(
        AuthScope.ANY, new UsernamePasswordCredentials(ELASTIC_LOGIN, ELASTIC_PASSWORD));

    return RestClient.builder(new HttpHost(ELASTIC_HOST, ELASTIC_PORT, "https"))
        .setHttpClientConfigCallback(
            hc -> hc.setSSLContext(sslContext).setDefaultCredentialsProvider(credsProv))
        .build();
  }
}
