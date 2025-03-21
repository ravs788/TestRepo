package org.ravs788.extensions.report;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.typesafe.config.Config;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClient;
import org.ravs788.config.TestEnvFactory;

@Slf4j
public class PublishResults {
  private static final Config CONFIG = TestEnvFactory.getInstance().getConfig();
  private static final ElasticServerChoices ELASTIC_SERVER =
      CONFIG.getEnum(ElasticServerChoices.class, "ELASTIC_SERVER");

  private static final ElasticsearchClient elasticsearchClient = getElasticHighLevelRestAPIClient();

  public static void toElastic(TestRunMetaData testRunMetaData) throws IOException {

    IndexResponse response =
        elasticsearchClient.index(i -> i.index("search-testproject").document(testRunMetaData));

    log.info("Indexed with version " + response.version());
  }

  public static ElasticsearchClient getElasticHighLevelRestAPIClient() {
    // Create the low-level client
    RestClient restClient = ElasticLowLevelRestClientFactory.getRestClient(ELASTIC_SERVER);

    // Create the transport with a Jackson mapper
    ElasticsearchTransport transport =
        new RestClientTransport(restClient, new JacksonJsonpMapper());

    // And create the API client
    return new ElasticsearchClient(transport);
  }
}
