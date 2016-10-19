package apis;

import utils.RequestSenderHTTPS;

public class IssueAPI extends RequestSenderHTTPS{

    RequestSenderHTTPS requestSender = new RequestSenderHTTPS();

    public void createIsueHTTPS(String body){
        requestSender
                .createRequestHTTPS(body)
                .post(ApiUrls.ISSUE.getUri());
    }

    public void addCommentHTTPS(String body){
        requestSender
                .createRequestHTTPS(body)
                .post(ApiUrls.ISSUE.getUri("10009/comment"));
    }

    public void deleteIssueHTTPS(String key){
        requestSender
                .createRequestWithoutBodyHTTPS()
                .delete(ApiUrls.ISSUE.getUri(key));
    }

    public void searchHTTPS(String body){
        requestSender
                .createRequestHTTPS(body)
                .post(ApiUrls.SEARCH.getUri());
    }

}
