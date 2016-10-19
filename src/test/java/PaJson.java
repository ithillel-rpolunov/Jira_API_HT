import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;

/**
 * Created by Vasya on 09.10.2016.
 */

public class PaJson {

    public static void jsonCreator(){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "testName");
        jsonObject.put("secondname", "secondtestName");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("test1");
        jsonArray.add("test2");
        jsonArray.add("test3");
        jsonArray.add("test4");
        jsonArray.add("test5");

        jsonObject.put("list", jsonArray);



        String sJsonObj = jsonObject.toJSONString();

        System.out.println(sJsonObj);


        String incommingJSON ="{\"comments\":[{\"author\":{\"active\":true,\"avatarUrls\":{\"16x16\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=xsmall&avatarId=10335\",\"24x24\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=small&avatarId=10335\",\"32x32\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=medium&avatarId=10335\",\"48x48\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?avatarId=10335\"},\"displayName\":\"r.polunov\",\"emailAddress\":\"r.polunov@gmail.com\",\"key\":\"r.polunov\",\"name\":\"r.polunov\",\"self\":\"http://soft.it-hillel.com.ua:8080/rest/api/2/user?username=r.polunov\",\"timeZone\":\"America/New_York\"},\"body\":\"https://github.com/ithillel-rpolunov/Jira_API_HT.git\",\"created\":\"2016-10-04T17:17:54.222-0400\",\"id\":\"11017\",\"self\":\"http://soft.it-hillel.com.ua:8080/rest/api/2/issue/13433/comment/11017\",\"updateAuthor\":{\"active\":true,\"avatarUrls\":{\"16x16\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=xsmall&avatarId=10335\",\"24x24\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=small&avatarId=10335\",\"32x32\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=medium&avatarId=10335\",\"48x48\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?avatarId=10335\"},\"displayName\":\"r.polunov\",\"emailAddress\":\"r.polunov@gmail.com\",\"key\":\"r.polunov\",\"name\":\"r.polunov\",\"self\":\"http://soft.it-hillel.com.ua:8080/rest/api/2/user?username=r.polunov\",\"timeZone\":\"America/New_York\"},\"updated\":\"2016-10-04T17:17:54.222-0400\"},{\"author\":{\"active\":true,\"avatarUrls\":{\"16x16\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=xsmall&avatarId=10335\",\"24x24\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=small&avatarId=10335\",\"32x32\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=medium&avatarId=10335\",\"48x48\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?avatarId=10335\"},\"displayName\":\"r.polunov\",\"emailAddress\":\"r.polunov@gmail.com\",\"key\":\"r.polunov\",\"name\":\"r.polunov\",\"self\":\"http://soft.it-hillel.com.ua:8080/rest/api/2/user?username=r.polunov\",\"timeZone\":\"America/New_York\"},\"body\":\"new comment via API\",\"created\":\"2016-10-04T17:53:01.718-0400\",\"id\":\"11019\",\"self\":\"http://soft.it-hillel.com.ua:8080/rest/api/2/issue/13433/comment/11019\",\"updateAuthor\":{\"active\":true,\"avatarUrls\":{\"16x16\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=xsmall&avatarId=10335\",\"24x24\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=small&avatarId=10335\",\"32x32\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?size=medium&avatarId=10335\",\"48x48\":\"http://soft.it-hillel.com.ua:8080/secure/useravatar?avatarId=10335\"},\"displayName\":\"r.polunov\",\"emailAddress\":\"r.polunov@gmail.com\",\"key\":\"r.polunov\",\"name\":\"r.polunov\",\"self\":\"http://soft.it-hillel.com.ua:8080/rest/api/2/user?username=r.polunov\",\"timeZone\":\"America/New_York\"},\"updated\":\"2016-10-04T17:53:01.718-0400\"}],\"maxResults\":2,\"startAt\":0,\"total\":2}";

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject obJ = (JSONObject) jsonParser.parse(incommingJSON);



            String s16 = String.valueOf((Long) obJ.get("maxResults"));

            System.out.println(s16);

            JSONArray msg = (JSONArray) obJ.get("comments.author");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(String.valueOf(iterator.next()));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


//        JSONObject p16 = incommingJSON.ge;






        System.out.println(jsonObject);

    }

    public static String loginJSON(String username, String password){

        JSONObject jsonlogin = new JSONObject();

        jsonlogin.put("username", username);
        jsonlogin.put("password", password);

        String sjsonlogin = jsonlogin.toString();

    return sjsonlogin;
    }


    public static String commentJSON(String comment){

        JSONObject commentJSON = new JSONObject();

        commentJSON.put("body", comment);

        String scommentJSON = commentJSON.toString();

        return scommentJSON;
    }


    public static String parserJson(String resp){

        JSONParser jsonParser = new JSONParser();

        String sid ="";

        try {
            Object o = jsonParser.parse(resp);
            System.out.println("resp " + String.valueOf(resp));
            JSONObject jsonObject = (JSONObject) o;
            sid = String.valueOf(jsonObject.get("id"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        JSONObject jsonObject = (JSONObject) resp;

//        String sid = String.valueOf(jsonObject.get("id"));
        System.out.println("sid " + sid);

        return sid;
    }

}
