Feature: When user adds a tip some tags are automatically inferred based on the url

  Scenario: User adds a new tip with a url into the program
    Given add tip view is clicked from the main view
    When title text area is clicked
    When text "haskell tutorial" is entered
    When url text area is clicked
    When text "https://www.youtube.com/watch?v=02_H3LjqMr8" is entered
    When add button is clicked
    When back button is clicked
    Then new tip with title "haskell tutorial" and tags "video" is stored in the program

  Scenario: User adds a new tip with a url into the program
    Given add tip view is clicked from the main view
    When title text area is clicked
    When text "data mining" is entered
    When url text area is clicked
    When text "https://ieeexplore.ieee.org/document/5694111" is entered
    When add button is clicked
    When back button is clicked
    Then new tip with title "data mining" and tags "article" is stored in the program

  Scenario: User adds a new tip with a url into the program
    Given add tip view is clicked from the main view
    When title text area is clicked
    When text "comp org" is entered
    When url text area is clicked
    When text "https://dl.acm.org/citation.cfm?id=3162173" is entered
    When add button is clicked
    When back button is clicked
    Then new tip with title "comp org" and tags "article" is stored in the program



