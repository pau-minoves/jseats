Droop story

Narrative:
In order to assign seats according to Largest Remainder method with a Droop quotient formula
As a development team
I want to use Droop

Scenario: Droop scenario
Given empty scenario
Given tally has candidate Candidate1 with 391000 votes
Given tally has candidate Candidate2 with 311000 votes
Given tally has candidate Candidate3 with 184000 votes
Given tally has candidate Candidate4 with 73000 votes
Given tally has candidate Candidate5 with 27000 votes
Given tally has candidate Candidate6 with 12000 votes
Given tally has candidate Candidate7 with 2000 votes
Given tally has 1000000 potential votes
!-- All potential votes are casted (potential votes == effective votes)
Given algorithm has property numberOfSeats set to 21
When process with Droop algorithm
Then result type is MULTIPLE
Then result has 21 seats
Then result seat #0 is Candidate1
Then result seat #1 is Candidate1
Then result seat #2 is Candidate1
Then result seat #3 is Candidate1
Then result seat #4 is Candidate1
Then result seat #5 is Candidate1
Then result seat #6 is Candidate1
Then result seat #7 is Candidate1
Then result seat #8 is Candidate2
Then result seat #9 is Candidate2
Then result seat #10 is Candidate2
Then result seat #11 is Candidate2
Then result seat #12 is Candidate2
Then result seat #13 is Candidate2
Then result seat #14 is Candidate2
Then result seat #15 is Candidate3
Then result seat #16 is Candidate3
Then result seat #17 is Candidate3
Then result seat #18 is Candidate3
Then result seat #19 is Candidate4
Then result seat #20 is Candidate4
	 
Scenario: Droop scenario from wikipedia (http://en.wikipedia.org/wiki/Largest_remainder_method)
Given empty scenario
Given tally has candidate Yellows with 47000 votes
Given tally has candidate Whites with 16000 votes
Given tally has candidate Reds with 15800 votes
Given tally has candidate Greens with 12000 votes
Given tally has candidate Blues with 6100 votes
Given tally has candidate Pinks with 3100 votes
Given tally has 100000 potential votes
!-- All potential votes are casted (potential votes == effective votes)
Given algorithm has property numberOfSeats set to 10
When process with Droop algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Yellows
Then result seat #3 is Yellows
Then result seat #4 is Yellows
Then result seat #5 is Whites
Then result seat #6 is Whites
Then result seat #7 is Reds
Then result seat #8 is Reds
Then result seat #9 is Greens
