package steps;

import static io.restassured.RestAssured.*;
import static utilities.StudyMateAuthToken.getAuthorized;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entity.RequestBody;
import entity.ResponseBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.APIRunner;
import utilities.Config;

import java.util.HashMap;
import java.util.Map;


public class GroupTestStepsTest {
    Response response;
    RequestBody requestBody = new RequestBody();

    int id;

    @Given("'{int} , {string}, {string}, {string}")
    public void int_string_string_string(Integer imageId, String groupName, String dateOfFinish, String description) {
        requestBody.setImageId(imageId);
        requestBody.setGroupName(groupName);
        requestBody.setDateOfFinish(dateOfFinish);
        requestBody.setDescription(description);
    }

    @Then("hit create group API endpoint {string} and to build request body and get confirmation message")
    public void hit_create_group_api_endpoint_and_to_build_request_body_and_get_confirmation_message(String path) {
        APIRunner.runPOST(path, requestBody);
        System.out.println("Message: " + APIRunner.getResponseBody().getMessage());
        Assert.assertEquals(APIRunner.getResponseBody().getMessage(), "Group successfully saved");
    }


    @Then("hit get group API endpoint {string} and get created group id by accessing {string},")
    public void hit_get_group_api_endpoint_and_get_created_group_id_by_accessing(String path, String groupName) throws JsonProcessingException {

        APIRunner.runGET(path, requestBody);
        for (ResponseBody i : APIRunner.getResponseBody().getObjects()) {
            if (i.getName() != null && i.getName().equals("APITestGroup")) {
                id = i.getId();
                System.out.println("The group with the name " + i.getName() + " is stored under ID " + id);
            }
        }
    }


    @Then("hit update Group API endpoint  {string} concatenated by above group id to update groupName to {string} and use same rest of data  {int}, {string}, {string}")
    public void hit_update_group_api_endpoint_concatenated_by_above_group_id_to_update_group_name_to_and_use_same_rest_of_data(String path, String updatedGroupName, int imageId, String dateOfFinish, String description) {
        int concatID = id;
        Map<String, Object> params = new HashMap<>();
        params.put("id", concatID);
        requestBody.setImageId(imageId);
        requestBody.setGroupName(updatedGroupName);
        requestBody.setDateOfFinish(dateOfFinish);
        requestBody.setDescription(description);
        APIRunner.runPUT(path, requestBody, params, concatID);
    }

    @Then("hit get group API endpoint {string} concatenated by above group id")
    public void hit_get_group_api_endpoint_concatenated_by_above_group_id(String path) throws JsonProcessingException {
        APIRunner.runGET(path, requestBody);
    }


    @Then("verify that group name got updated successfully to {string}")
    public void verify_that_group_name_got_updated_successfully_to(String updatedName) {
        for (ResponseBody i : APIRunner.getResponseBody().getObjects()) {
            if (i.getName() != null && i.getName().equals(updatedName)) {
                Assert.assertEquals(i.getName(),updatedName);

                System.out.println("The name of the group with id " + id + " was successfully changed to " + i.getName());
            }
        }
    }
    @Then("hit delete group API endpoint {string} concatenated by above group id")
    public void hit_delete_group_api_endpoint_concatenated_by_above_group_id(String path) {
        APIRunner.runDELETE(path,id);
    }
    @Then("verify successful group deletion process by asserting confirmation message {string}")
    public void verify_successful_group_deletion_process_by_asserting_confirmation_message(String confirmationMessage) {
        Assert.assertEquals(APIRunner.getResponseBody().getMessage(),confirmationMessage);
    }

    @Then("hit get trash API endpoint {string} to get deleted  id by accessing name {string}")
    public void hit_get_trash_api_endpoint_to_get_deleted_id_by_accessing_name(String path, String deletedGroupName) throws JsonProcessingException {
        APIRunner.runGET(path);
        for (ResponseBody i : APIRunner.getResponseBody().getObjects()) {
            if (i.getName() != null && i.getName().equals(deletedGroupName)) {
                id = i.getId();
                Assert.assertEquals(i.getName(),deletedGroupName);
                System.out.println("The deleted group with the name " + i.getName() + " was stored in trash under id " + i.getId());
            }
        }
    }

    @Then("hit delete trash API endpoint {string} concatenated by above group id in form of path parameter")
    public void hit_delete_trash_api_endpoint_concatenated_by_above_group_id_in_form_of_path_parameter(String path) {

        APIRunner.runDELETE(path,id);

    }
    @Then("verify successful group deletion from trash by asserting confirmation message {string}")
    public void verify_successful_group_deletion_from_trash_by_asserting_confirmation_message(String groupDeletedFromTrashConfirmationMessage) {
        Assert.assertEquals( groupDeletedFromTrashConfirmationMessage,APIRunner.getResponseBody().getMessage());
        System.out.println("The group with id " + id + " was successfully removed from the trash" );
    }

    @Given("user hits Crete Group api with valid path, parameters and headers")
    public void user_hits_crete_group_api_with_valid_path_parameters_and_headers() {
        String token = getAuthorized();
        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();
        requestBody.setImageId(1);
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
