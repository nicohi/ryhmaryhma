Feature: As a user I can close the application

  Scenario: User can exit the application
    Given user interface is initialized
    When command "quit" is entered
    Then system will respond with "Quitting vinkkeri..."
