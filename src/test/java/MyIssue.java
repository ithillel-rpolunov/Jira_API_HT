import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class MyIssue {


    String sessionID = "";
    String key = "";
    Response response;

    List<String> stringList = null;

    @BeforeTest
    public void login(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        String body1 = "{\n" +
                "    \"username\": \"r.polunov\",\n" +
                "    \"password\": \"docent05041983\"\n" +
                "}";
//        sessionID = given().
//                contentType("application/json").
//                body(body1).
//                when().
//                post("/rest/auth/1/session").
//                then().
//                statusCode(200).
//                extract().
//                path("session.value");

//        System.out.println(sessionID);

        Response response = given().
                contentType("application/json").
                body(body1).
                when().
                post("/rest/auth/1/session");

        sessionID = response.getSessionId();

        assertTrue(response.getStatusCode() == 200);
        assertTrue(response.path("session") != null);
        assertNotNull(response.path("session"));
    }

    @Test
    public void createIssuePositive201(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        String body1 = " {\n" +
                "\n" +
                " \"fields\": {\n" +
                "  \"project\": {\n" +
                "   \"id\": \"10315\"\n" +
                "  },\n" +
                "  \"summary\": \"rest_test\",\n" +
                "  \"issuetype\": {\n" +
                "   \"id\": \"10004\"\n" +
                "  },\n" +
                "  \"assignee\": {\n" +
                "   \"name\": \"r.polunov\"\n" +
                "  },\n" +
                "  \"reporter\": {\n" +
                "   \"name\": \"r.polunov\"\n" +
                "  }\n" +
                " }\n" +
                "}";


//        String response = given().
//                contentType("application/json").
//                cookie("JSESSIONID="+sessionID).
//                body(body1).
//                when().
//                post("/rest/api/2/issue").
//                then().
//                statusCode(201).
//                extract().
//                path("key");

        Response response = given().
                contentType("application/json").
                cookie("JSESSIONID=" + sessionID).
                body(body1).
                when().
                post("/rest/api/2/issue");

        key = response.getBody().jsonPath().get("key");

        assertTrue(response.getStatusCode() == 201);
        assertTrue(response.getBody().jsonPath().get("key").toString().contains("QAAUT-"));
        assertTrue(response.getBody().jsonPath().get("self").toString().contains("http://soft.it-hillel.com.ua:8080"));

    }

    @Test
    public void createIssueNegative400(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        String body1 = " {\n" +
                "\n" +
                " \"fields\": {\n" +
                "  \"project\": {\n" +
                "   \"id\": \"103151\"\n" +
                "  },\n" +
                "  \"summary\": \"rest_test\",\n" +
                "  \"issuetype\": {\n" +
                "   \"id\": \"10004\"\n" +
                "  },\n" +
                "  \"assignee\": {\n" +
                "   \"name\": \"r.polunov111\"\n" +
                "  },\n" +
                "  \"reporter\": {\n" +
                "   \"name\": \"r.polunov\"\n" +
                "  }\n" +
                " }\n" +
                "}";

        given().
                contentType("application/json").
                cookie("JSESSIONID="+sessionID).
                body(body1).
                when().
                post("/rest/api/2/issue").
                then().
                statusCode(400);
    }


    @Test
    public void deleteIssuePositive204(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";
        System.out.println(key + " first");

            createIssuePositive201();

        System.out.println(key + " second");

        System.out.println("key=" +  key);
        given().
                contentType("application/json").
                cookie("JSESSIONID=" + sessionID).
                when().
//                delete("/rest/api/2/issue/QAAUT-145").
                delete("/rest/api/2/issue/" + key).
                then().
                statusCode(204);
    }


    @Test
    public void deleteGroupIssuePositive204(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

            FilterIssue();

        System.out.println(stringList);
        for (String s : stringList) {
            System.out.println("key=" +  s);
            given().
                    contentType("application/json").
                    cookie("JSESSIONID=" + sessionID).
                    when().
//                delete("/rest/api/2/issue/QAAUT-145").
        delete("/rest/api/2/issue/"+s).
                    then().
                    statusCode(204);
        }
        }


    @Test
    public void deleteIssueNegative401(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        given().
                contentType("application/json").
                cookie("JSESSIONID=" + "1").
                when().
                delete("/rest/api/2/issue/").
                then().
                statusCode(401);
    }

    @Test
    public void deleteIssueNegative404(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        given().
                contentType("application/json").
                cookie("JSESSIONID=" + sessionID).
                when().
                delete("/rest/api/2/issue/QAAUT-145").
                then().
                statusCode(404);
    }



    @Test
    public void FilterIssue(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        Response response = given().
                contentType("application/json").
                cookie("JSESSIONID=" + sessionID).
                body("{\n" +
                        "    \"jql\": \"project = QAAUT and reporter = r.polunov\",\n" +
                        "    \"startAt\": 0,\n" +
                        "    \"maxResults\": 3,\n" +
                        "    \"fields\": [\n" +
                        "        \"key\" \n" +
                        "    ]\n" +
                        "}").
                post("/rest/api/2/search");

        String rez = response.asString();

        System.out.println(rez);


//        List<String> stringList = response.path("key");
        stringList = from(rez).getList("issues.key");

//        Person[] persons = given().when().get("person/").as(Person[].class);

        for (String s2 : stringList) {
            System.out.println(s2);
        }
    }


    @Test
    public void taskToSubTask(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        Response response =
        given().
                contentType("application/json").
                cookie("JSESSIONID=" + sessionID).
                body("{\"fields\":\n" +
                        "   {\"parent\":{\"id\":\"QAAUT-133\"},\n" +
                        "   \"project\":{\"id\":\"10315\"},\n" +
                        "   \"issuetype\":{\"id\":\"10003\"}}\n" +
                        "}").
                put("/rest/api/2/issue/QAAUT-255");

        System.out.println(response.asString());
        assertTrue(response.getStatusCode() == 204);

    }


    @Test
    public void addVote(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-138";

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        post("/rest/api/2/issue/" + key + "/votes");

        System.out.println(response.asString());
        assertTrue(response.getStatusCode() == 204);

    }


    @Test
    public void getVote(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-138";

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        get("/rest/api/2/issue/" + key + "/votes");

        System.out.println(response.asString());
        assertTrue(response.getStatusCode() == 200);
        assertTrue(response.getBody().jsonPath().get("voters.displayName").toString().equals("[r.polunov]"));
    }

    @Test
    public void remoteVote(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-138";

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        delete("/rest/api/2/issue/" + key + "/votes");

        System.out.println(response.asString());
        assertTrue(response.getStatusCode() == 204);
    }

    @Test
    public void addComment201(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-1148";

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        body("{\n" +
                                "    \"body\": \"new comment via API\"\n" +
                                "}").
                        post("/rest/api/2/issue/" + key + "/comment");

        System.out.println(response.asString());
        assertTrue(response.getStatusCode() == 201);
    }

    @Test
    public void addComment400(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-1148";

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        body("{\n" +
                                "    \"body1\": \"new comment via API\"\n" +
                                "}").
                        post("/rest/api/2/issue/" + key + "/comment");

        System.out.println(response.asString());
        assertTrue(response.getStatusCode() == 400);
    }

    @Test
    public void getComment200(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-1148";

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        get("/rest/api/2/issue/" + key + "/comment");



        stringList = from(response.asString()).getList("comments.id");

        System.out.println(stringList.get(stringList.size()-1));

        System.out.println(response.asString());
        System.out.println(response.getBody().jsonPath().get("comments.body").toString());
        System.out.println(response.getBody().jsonPath().get("comments.id").toString());
        assertTrue(response.getStatusCode() == 200);
    }

    @Test
    public void getComment404(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-9999999";

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        get("/rest/api/2/issue/" + key + "/comment");


        System.out.println(response.asString());
        assertTrue(response.getStatusCode() == 404);
        assertTrue(response.getBody().jsonPath().get("errorMessages").toString().contains("Issue Does Not Exist"));
    }

    @Test
    public void deleteComment204(){
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";

        key = "QA-1148";
        getComment200();

        Response response =
                given().
                        contentType("application/json").
                        cookie("JSESSIONID=" + sessionID).
                        delete("/rest/api/2/issue/" + key + "/comment/" + stringList.get(stringList.size()-1));


//        System.out.println(response.getBody().jsonPath().get("comments.body").toString());
        assertTrue(response.getStatusCode() == 204);
    }

//    @Test
//    public void taskToSubTask2(){
//        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080";
//
//        Response response =
//        given().
//                contentType("application/json").
//                cookie("JSESSIONID=" + sessionID).
//                body("{\"fields\":\n" +
//                        "   {\"parent\":{\"id\":\"QAAUT-133\"},\n" +
//                        "   \"project\":{\"id\":\"10315\"},\n" +
//                        "   \"issuetype\":{\"id\":\"10002\"}}\n" +
//                        "}").
//                get("/rest/api/2/issue/QAAUT-256/editmeta");
//
//        System.out.println(response.asString());
//
//    }
}
