package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.RequestBody;
import entity.ResponseBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.Config;
import utilities.StudyMateAuthToken;

import static io.restassured.RestAssured.*;

public class LoginTestSteps {
    Response response;

    @Given("User tries to hit login API with invalid username and valid password")
    public void user_tries_to_hit_login_api_with_invalid_username_and_valid_password() {

        baseURI = "https://backend.studymate.us";
        basePath = "/api/auth/authenticate";

        RequestBody credentials = new RequestBody();
        credentials.setEmail(Config.getProperty("invalidUsername"));
        credentials.setPassword(Config.getProperty("passwordStudyMate"));

        response = given()
                .contentType(ContentType.JSON)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .body(credentials)
                .when()
                .post(baseURI + basePath);
    }

    @Then("The user verifies status code and failed login message {string}")
    public void the_user_verifies_status_code_and_failed_login_message(String message) {
        int statusCode = response.statusCode();
        message = response.path("message");
        String expectedMessageInvalidUsername = "User with email " + Config.getProperty("invalidUsername") + " not found";
        String expectedMessageInvalidPassword = "Invalid password";
        if (statusCode == 404) {
            Assert.assertEquals(statusCode, 404);
            Assert.assertEquals(message, expectedMessageInvalidUsername);
            System.out.println("Message: " + message);
        } else if (statusCode == 401) {
            Assert.assertEquals(statusCode, 401);
            Assert.assertEquals(message, expectedMessageInvalidPassword);
            System.out.println("Message: " + message);

        }

    }

    @Given("User tries to hit login API with invalid password and valid username")
    public void user_tries_to_hit_login_api_with_invalid_password_and_valid_username() {
        baseURI = "https://backend.studymate.us";
        basePath = "/api/auth/authenticate";

        RequestBody credentials = new RequestBody();
        credentials.setEmail(Config.getProperty("emailStudyMate"));
        credentials.setPassword(Config.getProperty("invalidPassword"));

        response = given()
                .contentType(ContentType.JSON)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .body(credentials)
                .when()
                .post(baseURI + basePath);
    }

    @Given("User tries to hit login API with valid username and valid password")
    public void user_tries_to_hit_login_api_with_valid_username_and_valid_password() {
        baseURI = "https://backend.studymate.us";
        basePath = "/api/auth/authenticate";

        RequestBody credentials = new RequestBody();
        credentials.setEmail(Config.getProperty("emailStudyMate"));
        credentials.setPassword(Config.getProperty("passwordStudyMate"));

        response = given()
                .contentType(ContentType.JSON)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .body(credentials)
                .when()
                .post(baseURI + basePath);

    }

    @Then("The user verifies authority value;")
    public void the_user_verifies_authority_value() throws JsonProcessingException {
        String jsonResponse = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        ResponseBody responseBody = mapper.readValue(jsonResponse, ResponseBody.class);
        String authority = responseBody.getAuthority();
        Assert.assertNotNull(authority);
        System.out.println("Authority: " + responseBody.getAuthority());
    }


}
