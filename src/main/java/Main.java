import apis.IssueAPI;
import fixtures.JiraJSONFixture;

/**
 * Created by Vasya on 10.10.2016.
 */
public class Main {

    public static void main(String[] args) {

//        JiraJSONFixture jiraJSONFixtures = new JiraJSONFixture();
//        jiraJSONFixtures.generateJSONForCommentHTTPS();



//        RequestSenderHTTPS requestSenderHTTPS = new RequestSenderHTTPS();
//        requestSenderHTTPS.authenticate();

        IssueAPI issueAPI = new IssueAPI();
//        issueAPI.searchHTTPS();

        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        System.out.println(jiraJSONFixture.generateJSONforSearchHTTPS());


    }
}
