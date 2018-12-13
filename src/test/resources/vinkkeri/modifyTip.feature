Feature: As a user I can modify a Tip in its own view

  Scenario: User clicks on the modify button and enters the modify tip view
    Given listing view is visible
    When listing view contains Tips
    And Tip is double-clicked
    And user clicks on Modify tip button
    Then modify tip view becomes visible

  Scenario: User modifies the attributes of a Tip and saves the changes
    Given listing view is visible
    When listing view contains Tips
    And Tip is double-clicked
    And user clicks on Modify tip button
    And user makes changes to tip attributes and saves them
    Then the changes are saved and shown
