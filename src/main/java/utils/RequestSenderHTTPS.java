package utils;

import apis.ApiUrls;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import fixtures.JiraJSONFixture;

import static com.jayway.restassured.RestAssured.given;

/**
 * import com.jayway.restassured.RestAssured;
 * import com.jayway.restassured.http.ContentType;
 * import com.jayway.restassured.response.Response;
 * import com.jayway.restassured.specification.RequestSpecification;
 * import fixtures.JiraJSONFixture;
 * import static com.jayway.restassured.RestAssured.given;
 */


public class RequestSenderHTTPS {

    public static String JSESSIONID = null;
    public static String ATLASSIAN_TOKEN = null;
    public static String STUDIO_TOKEN = null;

    public final static ContentType CONTENT_TYPE = ContentType.JSON;
    public static RequestSpecification requestSpecification = null;
    public static Response response = null;

    public void authenticate(){
        RestAssured.baseURI = "https://forapitest.atlassian.net";

        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String credentials = jiraJSONFixture.generateJSONForLoginHTTPS();

        this.createRequestHTTPS(credentials)
                .post(ApiUrls.LOGIN.getUri());

        this.JSESSIONID = extractResponseByPath("session.value");
        this.ATLASSIAN_TOKEN = response.getCookie("atlassian.xsrf.token");
        this.STUDIO_TOKEN = response.getCookie("studio.crowd.tokenkey");
    }


    public RequestSenderHTTPS createRequestSpecification() {
        requestSpecification = given().
                when();
        return this;
    }

    public RequestSenderHTTPS createRequestHTTPS(String body) {
        this.createRequestSpecification()
                .addHeader("Content-Type", CONTENT_TYPE.toString())
                .addHeader("Cookie", "JSESSIONID=" + RequestSenderHTTPS.JSESSIONID)
                .addHeader("Cookie", "atlassian.xsrf.token="+RequestSenderHTTPS.ATLASSIAN_TOKEN)
                .addHeader("Cookie", "studio.crowd.tokenkey="+RequestSenderHTTPS.STUDIO_TOKEN)
                .addBody(body);

        return this;
    }

    public RequestSenderHTTPS createRequestWithoutBodyHTTPS() {
        this.createRequestSpecification()
                .addHeader("Content-Type", CONTENT_TYPE.toString())
                .addHeader("Cookie", "JSESSIONID=" + RequestSenderHTTPS.JSESSIONID)
                .addHeader("Cookie", "atlassian.xsrf.token="+RequestSenderHTTPS.ATLASSIAN_TOKEN)
                .addHeader("Cookie", "studio.crowd.tokenkey="+RequestSenderHTTPS.STUDIO_TOKEN)
                .delete("");

        return this;
    }

    public RequestSenderHTTPS delete(String uri){
        response = requestSpecification.delete(uri);
        return this;
    }


    public RequestSenderHTTPS addHeader(String headerName, String headerValue) {
    requestSpecification.header(headerName, headerValue);
    return this;
}

    public RequestSenderHTTPS post(String uri) {
//    requestSpecification.post(uri);
    response = requestSpecification.post(uri);
    return this;
}

    public RequestSenderHTTPS addBody(String body) {
    requestSpecification.body(body);
    return this;
}

    public String extractResponseByPath(String path) {
        return response.then().extract().path(path);
    }


}
