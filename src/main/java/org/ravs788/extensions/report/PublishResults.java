package org.ravs788.extensions.report;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PublishResults {
    public static void toElastic(TestRunMetaData testRunMetaData) {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log().ifValidationFails()
                .baseUri("http://localhost:9200/")
                .body(testRunMetaData)
                .when()
                .post("testproject/_doc")
                .then().log().ifError();
    }
}
