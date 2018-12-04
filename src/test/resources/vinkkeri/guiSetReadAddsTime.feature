Feature: As a user I can set a Tip as read and then a timestamp is shown

  Scenario: User can set a timestamp indicating that the Tip is read
    Given listing view is visible
    When listing view contains Tips
    And tip is selected
    And flip read button is clicked
    Then a correct timestamp is shown

  Scenario: User can set read back to false
    Given listing view is visible
    When listing view contains Tips
    And tip is selected
    And flip read button is clicked
    And tip is selected
    And flip read button is clicked
    Then read attribute will be false
