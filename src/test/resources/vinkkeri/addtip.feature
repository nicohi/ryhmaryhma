Feature: User can add reading Tip into the program
  Scenario: User adds new tip into the program
    Given new tip is selected
    When title "booktitle_temporary_pretty_long_name_for_testing_purposes" is entered
	And author "someone" is entered
	And comment "pretty good" is entered
	And isbn "10101010-123" is entered
    Then new tip with title "booktitle_temporary_pretty_long_name_for_testing_purposes" is in stored in the program