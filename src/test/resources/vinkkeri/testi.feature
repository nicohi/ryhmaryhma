Feature: Cucumber works in the project
  Scenario: true == true
    Given true is true
    When true is not true
    Then false is true