package steps;

import static io.restassured.RestAssured.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entity.RequestBody;
import entity.ResponseBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.Config;
import utilities.StudyMateAuthToken;


public class GroupTestSteps {
    Response response;

    @Given("user hits Crete Group api with valid path, parameters and headers")
    public void user_hits_crete_group_api_with_valid_path_parameters_and_headers() {
        String token = StudyMateAuthToken.getAuthorized();
        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();
        requestBody.setImageId("0");
        requestBody.setGroupName(faker.programmingLanguage().name());
        requestBody.setDateOfFinish("2023-11-06");
        requestBody.setDescription(faker.chuckNorris().fact());
        baseURI = (Config.getProperty("baseURI"));
        basePath = "/api/groups";

        response = given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .when()
                .post(baseURI + basePath);
    }
    @Then("user verify status code and success message")
    public void user_verify_status_code_and_success_message() throws JsonProcessingException {

        String jsonResponse = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        ResponseBody responseBody = mapper.readValue(jsonResponse, ResponseBody.class);
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(responseBody.getMessage(),"Group successfully saved");

    }


}
