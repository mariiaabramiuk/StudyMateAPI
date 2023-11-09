package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.RequestBody;
import entity.ResponseBody;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.*;


public class APIRunner {

    @Getter
    private static ResponseBody responseBody;


    public static void runPOST(String path, RequestBody requestBody) {
        String token = StudyMateAuthToken.getAuthorized();
        baseURI = (Config.getProperty("baseURI"));
        basePath = path;
        Response response = given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .when()
                .post(baseURI + basePath);

        String jsonResponse = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        try {
            responseBody = mapper.readValue(jsonResponse, ResponseBody.class);
            responseBody.setResponseBody(response.asString());
        } catch (JsonProcessingException e) {
            System.out.println("This is most likely List of  response");
        }
    }

    public static void runGET(String path, RequestBody requestBody) throws JsonProcessingException {

        String token = StudyMateAuthToken.getAuthorized();
        baseURI = (Config.getProperty("baseURI"));
        basePath = path;
        Response response = given()
                .auth()
                .oauth2(token)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .when()
                .get(baseURI + basePath);

        String jsonResponse = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        responseBody = mapper.readValue(jsonResponse, ResponseBody.class);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    public static void runGET(String path) throws JsonProcessingException {

        String token = StudyMateAuthToken.getAuthorized();
        baseURI = (Config.getProperty("baseURI"));
        basePath = path;
        Response response = given()
                .auth()
                .oauth2(token)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .when()
                .get(baseURI + basePath);

        String jsonResponse = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        responseBody = mapper.readValue(jsonResponse, ResponseBody.class);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    public static void runPUT(String path, RequestBody requestBody, Map<String, Object> params, int concatID) {
        String token = StudyMateAuthToken.getAuthorized();
        baseURI = (Config.getProperty("baseURI"));
        basePath = path;

        Response response = given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .params(params)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .when()
                .pathParams("id", concatID)
                .put(baseURI + basePath + "{id}");

        String jsonResponse = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        try {
            responseBody = mapper.readValue(jsonResponse, ResponseBody.class);
            responseBody.setResponseBody(response.asString());
        } catch (JsonProcessingException e) {
            System.out.println("This is most likely List of  response");
        }
    }

    public static void runDELETE(String path, int concatID) {
        String token = StudyMateAuthToken.getAuthorized();
        baseURI = (Config.getProperty("baseURI"));
        basePath = path;

        Response response = given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .when()
                .pathParams("id", concatID)
                .delete(baseURI + basePath + "{id}");
        String jsonResponse = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        try {
            responseBody = mapper.readValue(jsonResponse, ResponseBody.class);
            responseBody.setResponseBody(response.asString());
        } catch (JsonProcessingException e) {
            System.out.println("This is most likely List of  response");
        }

    }


// How can you extract a value from a response for future requests in Rest Assured?
//Answer: You can use the extract method. For example:
//String value = given().when().get(“https://api.example.com/resource").then().extract().path("key");
}


