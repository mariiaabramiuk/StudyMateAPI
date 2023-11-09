// This is the package declaration for the steps package
package steps;

// Importing necessary libraries
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entity.RequestBody;
import entity.ResponseBody;
import entity.StudentResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.Config;
import utilities.StudyMateAuthToken;

import java.util.List;

import static io.restassured.RestAssured.*;
import static utilities.StudyMateAuthToken.getAuthorized;

// This is the main class containing Cucumber steps for student-related API tests
public class StudentTestSteps {

    // Response object to store API responses
    Response response;

    // Student ID to be used for deletion
    private int studentIdToDelete;

    // Step for adding a student
    @Given("user hits add student api with valid path, parameters and headers")
    public void user_hits_add_student_api_with_valid_path_parameters_and_headers() {
        // Get authorization token
        String token = getAuthorized();

        // Create request body using Faker for random data
        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();

        requestBody.setName(name);
        requestBody.setLastName(lastName);
        requestBody.setPhoneNumber(faker.phoneNumber().phoneNumber());
        requestBody.setEmail(name + lastName + "@gmail.com");
        requestBody.setGroupId(0);
        requestBody.setStudyFormat("ONLINE");

        // Set base URI and path for the API
        baseURI = (Config.getProperty("baseURI"));
        basePath = "/api/students";

        // Send POST request to add a student
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

    // Step for verifying the status code and success message after adding a student
    @Then("user verifies status code and success message")
    public void user_verifies_status_code_and_success_message() throws JsonProcessingException {
        // Convert the response to a string
        String jsonResponse = response.asString();

        // Create an instance of the ObjectMapper class from the Jackson library
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize the JSON response into a ResponseBody object
        ResponseBody responseBody = mapper.readValue(jsonResponse, ResponseBody.class);

        // Verify the status code and success message
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseBody.getMessage(), "New student successfully saved");
    }

    // Step for hitting the get students API
    @When("user hits get students api with valid path, parameters and headers")
    public void user_hits_get_students_api_with_valid_path_parameters_and_headers() {
        // Get authorization token
        String token = getAuthorized();

        // Set base URI and path for the API
        baseURI = (Config.getProperty("baseURI"));
        basePath = "/api/students";

        // Send GET request to retrieve students
        response = given()
                .auth()
                .oauth2(token)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .get(baseURI + basePath);
    }

    // Step for getting the student ID from the response
    @Given("user gets the student id from that response")
    public void user_gets_the_student_id_from_that_response() throws JsonProcessingException {
        // Convert the response to a string
        String jsonResponse = response.asString();

        // Create an instance of the ObjectMapper class from the Jackson library
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize the JSON response into a StudentResponse object
        StudentResponse studentResponse = mapper.readValue(jsonResponse, StudentResponse.class);

        // Assuming you want the ID of the second student in the list
        // Note: List indices in Java start from 0, so [1] corresponds to the second element
        studentIdToDelete = studentResponse.getObjects().get(1).getStudentId();
    }

    // Step for hitting the delete student API with the acquired student ID
    @Given("user hits delete student api with the just acquired student id")
    public void user_hits_delete_student_api_with_the_just_acquired_student_id() {
        // Get authorization token
        String token = getAuthorized();

        // Create request body using Faker for random data
        RequestBody requestBody = new RequestBody();

        // Set base URI and path for the API
        baseURI = (Config.getProperty("baseURI"));
        basePath = "/api/students";

        // Send DELETE request to delete student
        response = given()
                .auth()
                .oauth2(token)
                .header("Origin", Config.getProperty("Origin"))
                .header("Referer", Config.getProperty("Referer"))
                .delete(baseURI + basePath + "/" + studentIdToDelete);
    }

    // Step for verifying the delete status code and success message
    @Then("user verifies delete status code and success message")
    public void user_verifies_delete_status_code_and_success_message() throws JsonProcessingException {
        // Convert the response to a string
        String jsonResponse = response.asString();

        // Create an instance of the ObjectMapper class from the Jackson library
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize the JSON response into a ResponseBody object
        ResponseBody responseBody = mapper.readValue(jsonResponse, ResponseBody.class);

        // Verify the status code and success message
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseBody.getMessage(), "Student successfully deleted");
    }
}
