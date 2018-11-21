Feature: As a user I can ask for help

  Scenario: Ask help
    Given user interface is initialized
    When command "help" is entered
    Then system will respond with "help - view help"
