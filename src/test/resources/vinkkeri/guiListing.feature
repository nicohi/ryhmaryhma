Feature: As a user I want to be able to list existing tips and add tips in graphical user interface

  Scenario: User can see listed tips and change to add tip view
    Given listing view is visible
	When add tip button is clicked
    Then add view is visible
