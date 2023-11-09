package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.RequestBody;
import entity.ResponseBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.APIRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeachersSteps {
    RequestBody requestBody = new RequestBody();

    int id;

// made on working  branch
    @Given("{string} , {string}, {string}, {string}, {string}, {string}")
    public void string_string_string_string_string_string(String name, String lastName, String phoneNumber, String email, String specialization, String courses) {
        requestBody.setName(name);
        requestBody.setLastName(lastName);
        requestBody.setPhoneNumber(phoneNumber);
        requestBody.setEmail(email);
        requestBody.setSpecialization(specialization);
        List<Integer> coursesList = Arrays.stream(courses.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        requestBody.setCourses(coursesList);
    }
    @Then("hit create a teacher API endpoint {string} to build request body and get confirmation message")
    public void hit_create_a_teacher_api_endpoint_to_build_request_body_and_get_confirmation_message(String path) {
        APIRunner.runPOST(path,requestBody);
        System.out.println("Message: " + APIRunner.getResponseBody().getMessage());
        Assert.assertEquals("Instructor successfully saved",APIRunner.getResponseBody().getMessage() );
    }
    @Then("hit get teachers API endpoint {string} and get created teacher id by accessing {string},")
    public void hit_get_teachers_api_endpoint_and_get_created_teacher_id_by_accessing(String path, String lastName) throws JsonProcessingException {
        APIRunner.runGET(path, requestBody);
        for (ResponseBody i : APIRunner.getResponseBody().getObjects()) {
            if (i.getFullName() != null && i.getFullName().contains(lastName)) {
                id = i.getId();
                System.out.println("The teacher with the name " + i.getFullName() + " is stored under ID " + id);
            }
        }
    }
    @Then("hit update teacher API endpoint  {string} concatenated by above teacher id to update lastName to {string} and use same rest of data  {string}, {string}, {string}, {string},{string}")
    public void hit_update_teacher_api_endpoint_concatenated_by_above_teacher_id_to_update_last_name_to_and_use_same_rest_of_data(String path, String updatedLastName, String name, String phoneNumber, String email, String specialization, String courses) {
        int concatID = id;
        Map<String, Object> params = new HashMap<>();
        params.put("id", concatID);
        requestBody.setName(name);
        requestBody.setLastName(updatedLastName);
        requestBody.setPhoneNumber(phoneNumber);
        requestBody.setEmail(email);
        requestBody.setSpecialization(specialization);
        List<Integer> coursesList = Arrays.stream(courses.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        requestBody.setCourses(coursesList);

        APIRunner.runPUT(path, requestBody, params, concatID);
    }
    @Then("hit get teacher API endpoint {string} concatenated by above teacher id")
    public void hit_get_teacher_api_endpoint_concatenated_by_above_teacher_id(String path) throws JsonProcessingException {
        APIRunner.runGET(path, requestBody);
    }
    @Then("verify that teachers lastName got updated successfully to {string}")
    public void verify_that_teachers_last_name_got_updated_successfully_to(String updatedLastName) {
        for (ResponseBody i : APIRunner.getResponseBody().getObjects()) {
            if (i.getFullName() != null && i.getFullName().contains(updatedLastName)) {
                Assert.assertTrue(i.getFullName().contains(updatedLastName));

                System.out.println("The name of the teacher with id " + id + " was successfully changed to " + i.getFullName());
            }
        }
    }
    @Then("hit delete teacher API endpoint {string} concatenated by above teacher id")
    public void hit_delete_teacher_api_endpoint_concatenated_by_above_teacher_id(String path) {
        APIRunner.runDELETE(path,id);
    }
    @Then("verify successful teacher deletion process by asserting confirmation message {string}")
    public void verify_successful_teacher_deletion_process_by_asserting_confirmation_message(String confirmationMessage) {
        Assert.assertEquals(APIRunner.getResponseBody().getMessage(),confirmationMessage);

    }
    @Then("hit get trash API endpoint {string} to get deleted id by accessing name {string}")
    public void hit_get_trash_api_endpoint_to_get_deleted_id_by_accessing_name(String path, String updatedTeacherName ) throws JsonProcessingException {
        APIRunner.runGET(path);
        for (ResponseBody i : APIRunner.getResponseBody().getObjects()) {
            if (i.getName() != null && i.getName().contains(updatedTeacherName)) {
                id = i.getId();
                Assert.assertTrue(i.getName().contains(updatedTeacherName));
                System.out.println("The deleted teacher with the name " + i.getName() + " was stored in trash under id " + i.getId());
            }
        }
    }


    @Then("hit delete trash API endpoint {string} concatenated by above teachers id in form of path parameter")
    public void hit_delete_trash_api_endpoint_concatenated_by_above_teachers_id_in_form_of_path_parameter(String path) {
        APIRunner.runDELETE(path,id);
    }


    @Then("verify successful teacher deletion from trash by asserting confirmation message {string}")
    public void verify_successful_teacher_deletion_from_trash_by_asserting_confirmation_message(String confirmationMessage) {
        Assert.assertEquals( confirmationMessage,APIRunner.getResponseBody().getMessage());
        System.out.println("The teacher with id " + id + " was successfully removed from the trash" );
    }
}
