Feature: As a User I can add reading Tips into the program

  Scenario: User adds new tip into the program
    Given add tip is clicked
    When title text area is clicked
    When text "super" is entered
    When url text area is clicked
    When text "wwwfi" is entered
    When tag text area is clicked
    When text "tag1tag2" is entered
    When add button is clicked
    Then new tip with title "super" and url "wwwfi" and tags "tag1tag2" is stored in the program

  Scenario: User adds a new tip with a url into the program
    Given add tip is clicked
    When title text area is clicked
    When text "kp12x" is entered
    When url text area is clicked
    When text "45koq" is entered
    When add button is clicked
    When back button is clicked
    Then new tip with title "kp12x" and url "45koq" is stored in the program
