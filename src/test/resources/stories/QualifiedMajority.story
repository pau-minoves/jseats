QualifiedMajority story

Narrative:
In order to select a candidate by qualified majority
As a development team
I want to use QualifiedMajorityAlgorithm

Scenario: Candidate list has not enough effective votes
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given algorithm has property minimumVotes set to 576
When process with QualifiedMajority algorithm
Then result type is UNDECIDED

Scenario: Candidate list has no candidate that reaches minimum votes
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given algorithm has property minimumVotes set to 300
When process with QualifiedMajority algorithm
Then result type is UNDECIDED

Scenario: Candidate list has a candidate that reaches minimum votes
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given algorithm has property minimumVotes set to 190
When process with QualifiedMajority algorithm
Then result type is SINGLE
Then result single candidate is CandidateC
Then result single candidate isn't CandidateA
Then result single candidate isn't CandidateB
Then result single candidate isn't CandidateD

Scenario: Candidate list has two candidates that reach minimum votes
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given algorithm has property minimumVotes set to 130
When process with QualifiedMajority algorithm
Then result type is SINGLE
Then result single candidate is CandidateC
Then result single candidate isn't CandidateA
Then result single candidate isn't CandidateB
Then result single candidate isn't CandidateD

Scenario: Candidate list has two candidates on a tie that reach minimum votes
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given algorithm has property minimumVotes set to 190
When process with QualifiedMajority algorithm
Then result type is TIE
Then result candidates contain CandidateA
Then result candidates contain CandidateC
Then result candidates do not contain CandidateB
Then result candidates do not contain CandidateD

Scenario: Candidate list has two candidates on a tie that don't reach minimum votes
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given algorithm has property minimumVotes set to 300
When process with QualifiedMajority algorithm
Then result type is UNDECIDED


Scenario: Candidate list has two candidates that reach qualified proportion
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 190 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 575 potential votes
Given algorithm has property qualifiedProportion set to 0.3
!-- minimum votes are 173
When process with QualifiedMajority algorithm
Then result type is SINGLE
Then result single candidate is CandidateC
Then result single candidate isn't CandidateA
Then result single candidate isn't CandidateB
Then result single candidate isn't CandidateD

Scenario: Candidate list has one candidate that reaches exact qualified proportion 
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 173 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 575 potential votes
Given algorithm has property qualifiedProportion set to 0.3
!-- minimum votes are 173
When process with QualifiedMajority algorithm
Then result type is SINGLE
Then result single candidate is CandidateC
Then result single candidate isn't CandidateA
Then result single candidate isn't CandidateB
Then result single candidate isn't CandidateD

Scenario: Candidate list has no candidates that reach qualified proportion
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 539 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 600 potential votes
Given algorithm has property qualifiedProportion set to 0.9
!-- minimum votes are 540
When process with QualifiedMajority algorithm
Then result type is UNDECIDED