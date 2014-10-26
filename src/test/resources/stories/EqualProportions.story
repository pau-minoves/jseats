EqualProportions story

Narrative:
In order to assign seats according to Equal proportions methods
As a development team
I want to use EqualProportions or Hungtington-Hill, not sure...
					 
Scenario: EqualProportions scenario from wikipedia (http://en.wikipedia.org/wiki/Huntington%E2%80%93Hill_method)
Given empty scenario
Given tally has candidate Connecticut with 236841 votes
Given tally has candidate Delaware with 55540 votes
Given tally has candidate Georgia with 70835 votes
Given tally has candidate Kentucky with 68705 votes
Given tally has candidate Maryland with 278514 votes
Given tally has candidate Massachusetts with 475327 votes
Given tally has candidate New Hampshire with 141822 votes
Given tally has candidate New Jersey with 179570 votes
Given tally has candidate New York with 331589 votes
Given tally has candidate North Carolina with 353523 votes
Given tally has candidate Pennsylvania with 432879 votes
Given tally has candidate Rhode Island with 68446 votes
Given tally has candidate South Carolina with 206236 votes
Given tally has candidate Vermont with 85533 votes
Given tally has candidate Virginia with 630560 votes
Given tally has 3615920 potential votes
Given algorithm has property numberOfSeats set to 105
Given algorithm has property numberOfInitialSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with EqualProportions method
Then result type is MULTIPLE
Then result has 105 seats
Then result has 7 seats for Connecticut
Then result has 2 seats for Delaware
Then result has 2 seats for Georgia
Then result has 2 seats for Kentucky
Then result has 8 seats for Maryland
Then result has 14 seats for Massachusetts
Then result has 4 seats for New Hampshire
Then result has 5 seats for New Jersey
Then result has 10 seats for New York
Then result has 10 seats for North Carolina
Then result has 12 seats for Pennsylvania
Then result has 2 seats for Rhode Island
Then result has 6 seats for South Carolina
Then result has 3 seats for Vermont
Then result has 18 seats for Virginia



