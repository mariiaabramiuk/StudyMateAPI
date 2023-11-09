@GroupsTest
Feature: Groups CRUD Test

  Scenario: Group Creation Test
  Scenario Outline: Group creation, group info update, reading group's data, deleting group
    Given '<imageId> , "<groupName>", "<dateOfFinish>", "<description>"
    Then hit create group API endpoint "/api/groups" and to build request body and get confirmation message
    Then hit get group API endpoint "/api/groups" and get created group id by accessing "<groupName>",
    Then hit update Group API endpoint  "/api/groups/" concatenated by above group id to update groupName to "This is updated group name" and use same rest of data  <imageId>, "<dateOfFinish>", "<description>"
    Then hit get group API endpoint "/api/groups/" concatenated by above group id
    Then verify that group name got updated successfully to "This is updated group name"
    Then hit delete group API endpoint "/api/groups/" concatenated by above group id
    Then verify successful group deletion process by asserting confirmation message "Group successfully deleted"
    Then hit get trash API endpoint "/api/trash" to get deleted  id by accessing name "This is updated group name"
    Then hit delete trash API endpoint "/api/trash/" concatenated by above group id in form of path parameter
    Then verify successful group deletion from trash by asserting confirmation message "Data deleted successfully"

    Examples:
      | imageId | groupName    | dateOfFinish | description      |
      | 0       | APITestGroup | 2023-12-25   | This is API test |


