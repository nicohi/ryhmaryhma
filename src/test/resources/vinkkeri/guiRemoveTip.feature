Feature: As a user I want to be able to remove listed tips

  Scenario: User can remove a tip he or she has entered in the program
    Given add tip is clicked
    When title text area is clicked
    When text "mario" is entered
    When add button is clicked
    When back button is clicked
    When text "mario" is clicked
    When delete is clicked
    Then a tip with title "mario" is not stored in the program
