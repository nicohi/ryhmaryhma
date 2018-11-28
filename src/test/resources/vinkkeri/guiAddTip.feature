Feature: As a User I can add reading Tips into the program

  Scenario: User adds new tip into the program
    Given add tip is clicked
	When title text area is clicked
	When text "supergoodsupertitleofdoomanddestruction" is entered
	When url text area is clicked
	When text "wwwsupersivustosupertestillejokaonvaintestitestitesti" is entered
	When tag text area is clicked
	When text "testitag1testitag2testitag3" is entered
	When add button is clicked
	Then new tip with title "supergoodsupertitleofdoomanddestruction" and url "wwwsupersivustosupertestillejokaonvaintestitestitesti" and tags "testitag1testitag2testitag3" is stored in the program
