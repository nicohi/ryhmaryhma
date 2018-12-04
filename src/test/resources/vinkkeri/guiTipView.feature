Feature: As a user I can see the details of a Tip in its own view

  Scenario: User can enter the Tip view by double-clicking a Tip in the listing view
    Given listing view is visible
    When listing view contains Tips
    And Tip is double-clicked
    Then Tip view becomes visible

  Scenario: User can delete a Tip in Tip view
    Given listing view is visible
    When listing view contains Tips
    And Tip is double-clicked
    And delete button is pressed
    Then the Tip is not listed anymore in the listing view

  Scenario: User can set read status in Tip view
    Given listing view is visible
    When listing view contains Tips
    And Tip is double-clicked
    And flip read button is pressed
    Then the timestamp will show the correct time
