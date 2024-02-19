package api;

import entity.RequestBody;
import io.restassured.http.ContentType;
import utilities.Config;

import static io.restassured.RestAssured.*;

public class CashwiseToken {
    public static String getAuthorized(){
        baseURI = "https://backend.cashwise.us";
        basePath ="/api/myaccount/auth/login";

        RequestBody credentials = new RequestBody();
        credentials.setEmail(Config.getProperty("emailCashwise"));
        credentials.setPassword(Config.getProperty("passwordCashwise"));

        String jwt_token = given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post(baseURI+basePath)
                .jsonPath().getString("jwt_token");

        return jwt_token;
    }
}
