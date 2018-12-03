Feature: As a User I can search for tips in the gui

  Scenario: User searches with one tag includes search term
    Given a tip with title "title1", author "author1" and tags "tag1" has been added
    Given a tip with title "title2", author "author2" and tags "tag2" has been added
    When search text area is clicked
    When text "tag1" is entered
    Then tip containing "tag1" is displayed

  Scenario: User searches with one tag excludes
    Given a tip with title "title1", author "author1" and tags "tag1" has been added
    Given a tip with title "title2", author "author2" and tags "tag2" has been added
    When search text area is clicked
    When text "tag1" is entered
    Then tip containing "tag2" is not displayed

  Scenario: User clears search field
    Given a tip with title "title1", author "author1" and tags "tag1" has been added
    Given a tip with title "title2", author "author2" and tags "tag2" has been added
    When search text area is clicked
    When text "tag1" is entered
	When clear search is clicked
    Then all tips are displayed