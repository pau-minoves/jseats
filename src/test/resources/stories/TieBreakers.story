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

Scenario: Tie in EqualProportions scenario from wikipedia (http://en.wikipedia.org/wiki/Huntington%E2%80%93Hill_method)
!-- Modified so Massachusetts and Pennsylvania have the same votes/population.
Given empty scenario
Given tally has candidate Connecticut with 236841 votes
Given tally has candidate Delaware with 55540 votes
Given tally has candidate Georgia with 70835 votes
Given tally has candidate Kentucky with 68705 votes
Given tally has candidate Maryland with 278514 votes
Given tally has candidate Massachusetts with 475327 votes and property minority=no
Given tally has candidate New Hampshire with 141822 votes
Given tally has candidate New Jersey with 179570 votes
Given tally has candidate New York with 331589 votes
Given tally has candidate North Carolina with 353523 votes
Given tally has candidate Pennsylvania with 475327 votes and property minority=yes
Given tally has candidate Rhode Island with 68446 votes
Given tally has candidate South Carolina with 206236 votes
Given tally has candidate Vermont with 85533 votes
Given tally has candidate Virginia with 630560 votes
Given algorithm has property numberOfSeats set to 105
Given algorithm has property numberOfInitialSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with EqualProportions method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is Massachusetts
Then result seat #1 is Pennsylvania
Given use tie breaker MinorityTieBreaker
When process with EqualProportions method
Then result type is MULTIPLE
Then result has 105 seats
Then result has 7  seats for Connecticut
Then result has 2  seats for Delaware
Then result has 2  seats for Georgia
Then result has 2  seats for Kentucky
Then result has 8  seats for Maryland
Then result has 14 seats for Massachusetts
Then result has 4  seats for New Hampshire
Then result has 5  seats for New Jersey
Then result has 9  seats for New York
Then result has 10 seats for North Carolina
Then result has 14 seats for Pennsylvania
Then result has 2  seats for Rhode Island
Then result has 6  seats for South Carolina
Then result has 2  seats for Vermont
Then result has 18 seats for Virginia

Scenario: Tie in DHondt scenario from wikipedia (http://en.wikipedia.org/wiki/Highest_averages_method)
!-- Modified so Whites and Reds have the same votes.
Given empty scenario
Given tally has candidate Yellows with 47000 votes
Given tally has candidate Whites with 16000 votes and property minority=no
Given tally has candidate Reds with 16000 votes and property minority=yes
Given tally has candidate Greens with 12000 votes
Given tally has candidate Blues with 6000 votes
Given tally has candidate Pinks with 3100 votes
Given tally has 100000 potential votes
Given algorithm has property groupSeatsPerCandidate set to true
Given algorithm has property numberOfSeats set to 10
When process with DHondt algorithm
Then result type is TIE
Then result has 2 seats
Then result seat #0 is Whites
Then result seat #1 is Reds
Given use tie breaker MinorityTieBreaker
When process with DHondt algorithm
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

Scenario: Tie in Droop scenario from wikipedia (http://en.wikipedia.org/wiki/Largest_remainder_method)
!-- Modified so Whites and Reds have the same votes.
Given empty scenario
Given tally has candidate Yellows with 47000 votes
Given tally has candidate Whites with 16000 votes
Given tally has candidate Reds with 16000 votes
Given tally has candidate Greens with 12000 votes
Given tally has candidate Blues with 6100 votes
Given tally has candidate Pinks with 3100 votes
Given tally has 100000 potential votes
Given algorithm has property numberOfSeats set to 10
When process with Droop algorithm
Then result type is TIE
Then result has 2 seats
Then result seat #0 is Whites
Then result seat #1 is Reds
Given use tie breaker MinorityTieBreaker
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
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

Scenario: Tie in Candidate list with a tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes and property minority=yes
Given tally has candidate CandidateD with 125 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateA
Then result seats contain CandidateC
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD
Given use tie breaker MinorityTieBreaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateC

Scenario: TieBreaker returns null candidate list (cannot decide).