@LoginTest
Feature: Login related tests

  Scenario: Invalid username
    Given User tries to hit login API with invalid username and valid password
    Then The user verifies status code and failed login message "message"

  Scenario: Invalid password
    Given User tries to hit login API with invalid password and valid username
    Then The user verifies status code and failed login message "message"

    Scenario: Successful login
      Given User tries to hit login API with valid username and valid password
      Then The user verifies authority value;