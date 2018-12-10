Feature: As a User I can hide read tips in the gui

  Scenario: User can hide read tip
    Given a tip with title "title1", author "author1" and tags "tag1" has been added
    Given a tip with title "title2", author "author2" and tags "tag2" has been added
	Given tip with title "title1" is read
    When hide read is clicked
    Then tip containing "title1" is not displayed

  Scenario: User can unhide read tip
    Given a tip with title "title1", author "author1" and tags "tag1" has been added
    Given a tip with title "title2", author "author2" and tags "tag2" has been added
	Given tip with title "title1" is read
    When hide read is clicked twice
    Then tip containing "title1" is displayed