@TeachersTest
Feature: Teacher CRUD Test
  Scenario Outline: Teacher creation, Teacher info update, Reading teacher's data, deleting the teacher
    Given "<name>" , "<lastName>", "<phoneNumber>", "<email>", "<specialization>", "<courses>"
    Then hit create a teacher API endpoint "/api/instructors" to build request body and get confirmation message
    Then hit get teachers API endpoint "/api/instructors" and get created teacher id by accessing "<lastName>",
    Then hit update teacher API endpoint  "/api/instructors/" concatenated by above teacher id to update lastName to "Updated Lastname" and use same rest of data  "<name>", "<phoneNumber>", "<email>", "<specialization>","<courses>"
    Then hit get teacher API endpoint "/api/instructors" concatenated by above teacher id
    Then verify that teachers lastName got updated successfully to "Updated Lastname"
    Then hit delete teacher API endpoint "/api/instructors/" concatenated by above teacher id
    Then verify successful teacher deletion process by asserting confirmation message "Instructor successfully deleted"
    Then hit get trash API endpoint "/api/trash" to get deleted id by accessing name "Minerva Updated Lastname"
    Then hit delete trash API endpoint "/api/trash/" concatenated by above teachers id in form of path parameter
    Then verify successful teacher deletion from trash by asserting confirmation message "Data deleted successfully"

    Examples:
      | name    | lastName   | phoneNumber | email          | specialization  | courses |
      | Minerva | McGonagall | 12345678    | mini@gmail.com | Transfiguration | 0       |
