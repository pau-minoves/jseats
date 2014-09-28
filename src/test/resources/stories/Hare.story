Sample story

Narrative:
In order to assign seats according to Hare method
As a development team
I want to use HareAlgorithm
					 
Scenario: Candidate list
Given empty scenario
Given tally has candidate Candidate1 with 391000 votes
Given tally has candidate Candidate2 with 311000 votes
Given tally has candidate Candidate3 with 184000 votes
Given tally has candidate Candidate4 with 73000 votes
Given tally has candidate Candidate5 with 27000 votes
Given tally has candidate Candidate6 with 12000 votes
Given tally has candidate Candidate7 with 2000 votes
Given tally has 1000000 potential votes
Given algorithm has property numberOfSeats set to 21
!-- All potential votes are casted (potential votes == effective votes)
When process with Hare algorithm
Then result type is MULTIPLE


