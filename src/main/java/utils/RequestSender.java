package utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import fixtures.JiraJSONFixture;

import static com.jayway.restassured.RestAssured.given;

public class RequestSender {

    public static String JSESSIONID = null;
    public static String ATLASSIAN_TOKEN = null;
    public static String STUDIO_TOKEN = null;

    public final static ContentType CONTENT_TYPE = ContentType.JSON;
    public static RequestSpecification requestSpecification = null;
    public static Response response = null;



    public void authenticate() {
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";
//        RestAssured.baseURI = "https://forapitest.atlassian.net";

        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String credentials = jiraJSONFixture.generateJSONForLogin();

        createRequest(credentials)
                .post(apis.ApiUrls.LOGIN.getUri());

        this.JSESSIONID = response.then().extract().path("session.value");
        this.ATLASSIAN_TOKEN = response.then().extract().cookie("atlassian.xsrf.token");
        this.STUDIO_TOKEN = response.then().extract().cookie("studio.crowd.tokenkey");

        System.out.println(JSESSIONID);
        System.out.println(ATLASSIAN_TOKEN);
        System.out.println(STUDIO_TOKEN);
    }

    public RequestSender createRequestold(String body) {
        this.createRequestSpecification()
                .addHeader("Content-Type", CONTENT_TYPE.toString())
                .addHeader("Cookie", "JSESSIONID=" + RequestSender.JSESSIONID)
                .addBody(body);
//        System.out.println(this);
        return this;
    }

    public RequestSender createRequest(String body) {
//        authenticate();
        this.createRequestSpecification()
                .addHeader("Content-Type", CONTENT_TYPE.toString())
                .addHeader("Cookie", "JSESSIONID=" + RequestSender.JSESSIONID)
                .addBody(body);
//        System.out.println(this);
        return this;
    }

    public RequestSender createRequestHTTPS(String body) {
        this.createRequestSpecification()
                .addHeader("Content-Type", CONTENT_TYPE.toString())
                .addHeader("Cookie", "JSESSIONID=" + RequestSender.JSESSIONID)
                .addHeader("Cookie", "atlassian.xsrf.token="+RequestSender.ATLASSIAN_TOKEN)
                .addHeader("Cookie", "studio.crowd.tokenkey="+RequestSender.STUDIO_TOKEN)
                .addBody(body);
//        System.out.println(this);
        return this;
    }

    public RequestSender createRequestissue(String body) {
//        authenticate();
        this.createRequestSpecification()
                .addHeader("Content-Type", CONTENT_TYPE.toString())
                .addHeader("Cookie", "JSESSIONID=" + RequestSender.JSESSIONID)
                .addBody("{\n" +
                        "\"fields\": {\n" +
                        "   \"project\":\n" +
                        "   { \n" +
                        "      \"key\": \"TES\"\n" +
                        "   },\n" +
                        "   \"summary\": \"REST EXAMPLE\",\n" +
                        "   \"description\": \"Creating an issue via REST API\",\n" +
                        "   \"issuetype\": {\n" +
                        "      \"name\": \"Bug\"\n" +
                        "   }\n" +
                        "  }\n" +
                        "}");
        System.out.println(this);
        return this;
    }


    public RequestSender createRequestSpecification() {
        requestSpecification = given().
                when();
        return this;
    }

    public RequestSender addHeader(String headerName, String headerValue) {
        requestSpecification.header(headerName, headerValue);
        return this;
    }

    public String extractResponseByPath(String path){
        System.out.println(response.asString());
        return response.then().extract().path(path);
    }

    public RequestSender post(String uri) {
        response = requestSpecification.post(uri);
        return this;
    }

    public RequestSender addBody(String body) {
        requestSpecification.body(body);
        return this;
    }
}
