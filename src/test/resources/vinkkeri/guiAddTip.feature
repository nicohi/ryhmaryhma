Feature: As a User I can add reading Tips into the program

  Scenario: User adds new tip into the program with a title, an author and a comment
    Given add tip view is clicked from the main view
    When title text area is clicked
    When text "introduction to algorithms" is entered
    When author text area is clicked
    When text "clrs" is entered
    When comment text area is clicked
    When text "graphs are hard" is entered
    When add button is clicked
    When back button is clicked
    Then new tip with title "introduction to algorithms" and author "clrs" and description "graphs are hard" is stored in the program

  Scenario: User adds a new tip with a url into the program
    Given add tip view is clicked from the main view
    When title text area is clicked
    When text "structure and interpretation of computer programs" is entered
    When url text area is clicked
    When text "https://mitpress.mit.edu/sites/default/files/sicp/index.html" is entered
    When add button is clicked
    When back button is clicked
    Then new tip with title "structure and interpretation of computer programs" and url "https://mitpress.mit.edu/sites/default/files/sicp/index.html" is stored in the program

  Scenario: User adds a new tip with tags into the program
    Given add tip view is clicked from the main view
    When title text area is clicked
    When text "hobitti" is entered
    When tag text area is clicked
    When text "fantasia,kirja,tolkien" is entered
    When add button is clicked
    When back button is clicked
    Then new tip with title "hobitti" and tags "fantasia,kirja,tolkien" is stored in the program
