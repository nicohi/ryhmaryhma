Feature: As a user I want to be able to list existing tips

  Scenario: user can list tips
    Given user interface is initialized
    When command "list" is entered
    Then system will output a line containing "Title: Jukka Palmun Kosto"
