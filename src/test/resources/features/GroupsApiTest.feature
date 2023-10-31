@GroupsTest
Feature: Groups Related Tests
  Scenario: Create a group
    Given user hits Crete Group api with valid path, parameters and headers
    Then user verify status code and success message
