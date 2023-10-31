package utilities;

import entity.RequestBody;
import entity.ResponseBody;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class StudyMateAuthToken {
    public static String getAuthorized(){
        baseURI ="https://backend.studymate.us";
        basePath ="/api/auth/authenticate";

        RequestBody credentials = new RequestBody();
        credentials.setEmail(Config.getProperty("emailStudyMate"));
        credentials.setPassword(Config.getProperty("passwordStudyMate"));

        String token = given()
                .contentType(ContentType.JSON)
                .header("Origin",Config.getProperty("Origin"))
                .header("Referer",Config.getProperty("Referer"))
                .body(credentials)
                .when()
                .post(baseURI+basePath)
                .jsonPath().getString("token");

        return token;
    }
}
