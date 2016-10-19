//import apis.ApiUrls;
//import utils.RequestSender;
//
///**
// * Created by Vasya on 16.10.2016.
// */
//
//
//public class Worked {
//
//
//    public static String ATLASSIAN_TOKEN = null;
//    public static String STUDIO_TOKEN = null;
//
//    public void authenticateSecured() {
//        RestAssured.baseURI = "https://forapitest.atlassian.net";
//
//        GenerateJSONForJIRA generateJSON = new GenerateJSONForJIRA();
//        String credentials = generateJSON.login();
//
//        createRequest(credentials)
//                .post(ApiUrls.LOGIN.getUri());
//
//        this.JSESSIONID = response.then().extract().path("session.value");
//        this.ATLASSIAN_TOKEN = response.then().extract().cookie("atlassian.xsrf.token");
//        this.STUDIO_TOKEN = response.then().extract().cookie("studio.crowd.tokenkey");
//    }
//
//    public RequestSender createRequestSecured(String body){
//        this.createRequestSpecification()
//                .addHeader("Content-Type", CONTENT_TYPE.toString())
//                .addHeader("Cookie", "JSESSIONID="+RequestSender.JSESSIONID)
//                .addHeader("Cookie", "atlassian.xsrf.token="+RequestSender.ATLASSIAN_TOKEN)
//                .addHeader("Cookie", "studio.crowd.tokenkey="+RequestSender.STUDIO_TOKEN)
//                .addBody(body);
//        return this;
//    }
//
//    public void createIssueSecure(String body){
//        requestSender.createRequestSecured(body).post(ApiUrls.ISSUE.getUri());
//    }
//
//}
