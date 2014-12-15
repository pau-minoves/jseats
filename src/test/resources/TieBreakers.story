Tie Breakers story

Meta:
@devel

Narrative:
In order to break ties in seat allocation algorithms
As a development team
I want to use TieBreakers
					 
Scenario:  Rank candidates by votes produces a TIE on top
Given empty scenario
Given tally has candidate CandidateA with 150 votes and property minority=yes
Given tally has candidate CandidateB with 200 votes and properties minority=no
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes and property minority=yes
Given algorithm has property numberOfSeats set to 4
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateB
Then result seat #1 is CandidateD
Given use tie breaker MinorityTieBreaker
When process with RankByVotes method
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateB
Then result seat #2 is CandidateA
Then result seat #3 is CandidateC

Scenario: Rank candidates by votes produces a TIE in the middle
Given empty scenario
Given tally has candidate CandidateA with 150 votes and property minority=yes
Given tally has candidate CandidateB with 150 votes and properties minority=no
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes
Given algorithm has property numberOfSeats set to 4
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB
Given use tie breaker MinorityTieBreaker
When process with RankByVotes method
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateA
Then result seat #2 is CandidateB
Then result seat #3 is CandidateC