RankByVotes story

Narrative:
In order to assign seats according to RankMethod by votes.
As a development team
I want to use RankByVotes method
					 
Scenario:  Rank candidates by votes
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes
Given algorithm has property numberOfSeats set to 4
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateB
Then result seat #2 is CandidateA
Then result seat #3 is CandidateC

Scenario:  Rank candidates by votes produces a TIE
Given empty scenario
Given tally has candidate CandidateA with 150 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes
Given algorithm has property numberOfSeats set to 4
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB
