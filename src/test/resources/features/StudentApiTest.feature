@StudentTest
Feature: Student Related Tests
  Scenario: Add a student
    Given user hits add student api with valid path, parameters and headers
    Then user verifies status code and success message

  Scenario: Delete a student
    When user hits get students api with valid path, parameters and headers
    Given user gets the student id from that response
    And user hits delete student api with the just acquired student id
    Then user verifies delete status code and success message




