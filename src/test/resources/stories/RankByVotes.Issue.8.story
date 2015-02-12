Sample story

Narrative:
In order to fix bug when there are ties below the numberOfSeats threshold (See https://github.com/pau-minoves/jseats/issues/8)
As a development team
I want to fix RankMethod tie algorithm.


Scenario:  Base bug scenario
Meta: 
@skip
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 100 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB
!-- The tie is below the numberOfSeats threshold.  Result should be SINGLE -> CandidateD, as in the scenario below:
					 
Scenario:  Base bugfix scenario
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 100 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is SINGLE
Then result has 1 seats
Then result seat #0 is CandidateD

Scenario:  yet this must still be a tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 200 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 100 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB

Scenario:  and this must also be a tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes and property minority=yes
Given tally has candidate CandidateB with 200 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 300 votes
Given algorithm has property numberOfSeats set to 2
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB
Given use tie breaker MinorityTieBreaker
When process with RankByVotes method
Then result type is MULTIPLE
Then result has 2 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateA

Scenario:  full tie
Given empty scenario
Given tally has candidate CandidateA with 123 votes
Given tally has candidate CandidateB with 123 votes
Given tally has candidate CandidateC with 123 votes
Given tally has candidate CandidateD with 123 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 4 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB
Then result seat #2 is CandidateC
Then result seat #3 is CandidateD

Scenario:  full tie with tieBreaker
Given empty scenario
Given tally has candidate CandidateA with 123 votes and property minority=yes
Given tally has candidate CandidateB with 123 votes
Given tally has candidate CandidateC with 123 votes
Given tally has candidate CandidateD with 123 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
Given use tie breaker MinorityTieBreaker
When process with RankByVotes method
Then result type is SINGLE
Then result has 1 seats
Then result seat #0 is CandidateA

Scenario:  full tie that tieBreaker can't break
Given empty scenario
Given tally has candidate CandidateA with 123 votes
Given tally has candidate CandidateB with 123 votes
Given tally has candidate CandidateC with 123 votes
Given tally has candidate CandidateD with 123 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
Given use tie breaker MinorityTieBreaker
When process with RankByVotes method
Then result type is TIE
Then result has 4 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB
Then result seat #2 is CandidateC
Then result seat #3 is CandidateD
