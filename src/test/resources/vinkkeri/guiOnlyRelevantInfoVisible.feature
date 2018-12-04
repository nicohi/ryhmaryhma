Feature: Only the title, author, related tags and read information is shown to the user in the listing view

  Scenario: User sees only title, author, tags and read attributes of the Tip
    Given listing view is visible
    When listing view is visible
    Then only title, author, tags and read information shown
