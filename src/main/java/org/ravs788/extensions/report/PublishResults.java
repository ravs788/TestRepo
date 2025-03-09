package org.ravs788.extensions.report;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

@Slf4j
public class PublishResults {
    private static final ElasticsearchClient elasticsearchClient = getElasticHighLevelRestAPIClient();

    public static void toElastic(TestRunMetaData testRunMetaData) throws IOException {

        IndexResponse response = elasticsearchClient.index(i -> i
                .index("testproject")
                .document(testRunMetaData)
        );
        
        log.info("Indexed with version "+response.version());
    }

    public static ElasticsearchClient getElasticHighLevelRestAPIClient(){
        // Create the low-level client
        RestClient restClient = getRestClientForLocalHost();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        return new ElasticsearchClient(transport);
    }

    private static RestClient getRestClientForLocalHost() {
        return RestClient.builder(
                new HttpHost("localhost", 9200)).build();
    }

    private static RestClient getRestClientForElasticCloud(){
        Header[] defaultHeaders =
                new Header[]{
                        new BasicHeader("Accept", "application/json"),
                        new BasicHeader("Authorization", "ApiKey "),
                };

        return RestClient.builder(
                new HttpHost("",443,"https"))
                .setDefaultHeaders(defaultHeaders)
                .build();
    }
}
