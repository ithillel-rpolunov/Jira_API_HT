package fixtures;

import org.json.simple.JSONObject;


public class JiraJSONFixture {

    public String generateJSONForLogin() {
        JSONObject credentials = new JSONObject();
        credentials.put("username", "r.polunov");
        credentials.put("password", "docent05041983");

        return credentials.toJSONString();
    }

    public String generateJSONForLoginHTTPS() {
        JSONObject credentials = new JSONObject();
        credentials.put("username", "admin");
        credentials.put("password", "forapitest");

        return credentials.toJSONString();
    }

    public static String generateJSONForSampleIssueHTTPS() {
        JSONObject issue = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject project = new JSONObject();
        JSONObject issuetype = new JSONObject();
        JSONObject assignee = new JSONObject();
        JSONObject reporter = new JSONObject();

        issue.put("fields", fields);
        fields.put("project", project);
        fields.put("issuetype", issuetype);
        fields.put("assignee", assignee);
        fields.put("reporter", reporter);
        fields.put("summary", "rest_test");

        project.put("id", "10000");
        issuetype.put("id", "10001");
        assignee.put("name", "admin");
        reporter.put("name", "admin");
        return issue.toJSONString();

    }


    public static String generateJSONforSearchHTTPS(){
        JSONObject search = new JSONObject();
//        JSONObject jql = new JSONObject();
//        JSONObject startAt = new JSONObject();
//        JSONObject maxResults = new JSONObject();

        search.put("jql", "project = TES and reporter = admin");
        search.put("startAt","0");
        search.put("maxResults","3");


        return search.toJSONString();
    }




//    " Response response = given().\n"+
//            "                contentType(\"application/json\").\n"+
//            "                cookie(\"JSESSIONID=\" + sessionID).\n"+
//            "                body(\"{\\n\" +\n"+
//            "                        \"    \\\"jql\\\": \\\"project = QAAUT and reporter = r.polunov\\\",\\n\" +\n"+
//            "                        \"    \\\"startAt\\\": 0,\\n\" +\n"+
//            "                        \"    \\\"maxResults\\\": 3,\\n\" +\n"+
//            "                        \"    \\\"fields\\\": [\\n\" +\n"+
//            "                        \"        \\\"key\\\" \\n\" +\n"+
//            "                        \"    ]\\n\" +\n"+
//            "                        \"}\").\n"+
//            "                post(\"/rest/api/2/search\");"


    public static String generateJSONForCommentHTTPS(){
        JSONObject comment = new JSONObject();
        comment.put("body","REST API comment via RESTASSURED");
        return comment.toJSONString();
    }

    public static String generateJSONForSampleIssue() {
        JSONObject issue = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject project = new JSONObject();
        JSONObject issuetype = new JSONObject();
        JSONObject assignee = new JSONObject();
        JSONObject reporter = new JSONObject();

        issue.put("fields", fields);
        fields.put("project", project);
        fields.put("issuetype", issuetype);
        fields.put("assignee", assignee);
        fields.put("reporter", reporter);
        fields.put("summary", "rest_test");

        project.put("id", "10000");
        issuetype.put("id", "10001");
        assignee.put("name", "admin");
        reporter.put("name", "admin");
        return issue.toJSONString();

    }



}
