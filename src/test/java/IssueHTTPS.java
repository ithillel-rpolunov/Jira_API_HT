import apis.IssueAPI;
import fixtures.JiraJSONFixture;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static io.restassured.path.json.JsonPath.from;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class IssueHTTPS {


    public static String sessionID;
    public static String ATLASSIAN_TOKEN;
    public static String STUDIO_TOKEN;


    @BeforeTest
    public void login(){
        utils.RequestSenderHTTPS requestSenderHTTPS = new utils.RequestSenderHTTPS();
        requestSenderHTTPS.authenticate();
        sessionID = requestSenderHTTPS.extractResponseByPath("session.value");
        ATLASSIAN_TOKEN = requestSenderHTTPS.response.getCookie("atlassian.xsrf.token");
        STUDIO_TOKEN = requestSenderHTTPS.response.getCookie("studio.crowd.tokenkey");

        //check
        assertNotNull(sessionID);
        assertNotNull(ATLASSIAN_TOKEN);
        assertNotNull(STUDIO_TOKEN);

        assertTrue(String.valueOf(requestSenderHTTPS.response.getStatusCode()).equals("200"));
    }

    @Test (groups = {"Issue"})
    public void createIssuePositive201HTTPS(){

        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String issue = jiraJSONFixture.generateJSONForSampleIssueHTTPS();

        IssueAPI issueAPI = new IssueAPI();
        issueAPI.createIsueHTTPS(issue);

        assertEquals(issueAPI.response.getStatusCode(),201);
        assertTrue(issueAPI.response.getBody().jsonPath().get("key").toString().contains("TES-"));
        assertTrue(issueAPI.response.getBody().jsonPath().get("self").toString().contains("https://forapitest.atlassian.net"));
    }

    @Test (groups = {"comment"})
    public void CommentHTTPS(){

        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String comment = jiraJSONFixture.generateJSONForCommentHTTPS();

        IssueAPI issueAPI = new IssueAPI();
        issueAPI.addCommentHTTPS(comment);

        Assert.assertEquals(issueAPI.response.getStatusCode(), 201);
        assertTrue(issueAPI.response.getBody().jsonPath().get("body").toString().equals( "REST API comment via RESTASSURED"));
    }


    @Test (groups = {"search"})
    public void SearchIssueHTTPS(){
        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String searchIssue = jiraJSONFixture.generateJSONforSearchHTTPS();

        IssueAPI issueAPI = new IssueAPI();
        issueAPI.searchHTTPS(searchIssue);

        System.out.println(issueAPI.response.getStatusCode());
        System.out.println(issueAPI.response.getBody().jsonPath());

        System.out.println(issueAPI.response.asString());

        List<String> stringList = null;
        String rez = issueAPI.response.asString();

        stringList = from(rez).getList("issues.key");

        for (String s2 : stringList) {
            System.out.println(s2);
        }

        Assert.assertEquals(issueAPI.response.getStatusCode(),200);
//        Assert.assertEquals(issueAPI.response.getBody().jsonPath().get("key").toString(),"TES-");
        Assert.assertTrue(issueAPI.response.getBody().jsonPath().get("issues.key").toString().contains("TES-"));

    }


    @Test (groups = "Issue")
    public void deleteIssue(){
        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String issue = jiraJSONFixture.generateJSONForSampleIssueHTTPS();

        IssueAPI issueAPI = new IssueAPI();
        issueAPI.createIsueHTTPS(issue);

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        String key = IssueAPI.response.getBody().jsonPath().get("key").toString();
        System.out.println(key);

        issueAPI.deleteIssueHTTPS(key);

        Assert.assertEquals(issueAPI.response.getStatusCode(), 204);
    }



}
