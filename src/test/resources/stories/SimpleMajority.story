SimpleMajority story

Narrative:
In order to select a candidate by simple majority
As a development team
I want to use SimpleMajorityAlgorithm
					 
!-- Scenario:  Candidate list with clear winner from disk
!-- Given use SimpleMajority algorithm
!-- When load "/stories/simpleMajority/simple.tally.xml" tally
!-- When process with selected algorithm
!-- Then single result is "/stories/simpleMajority/simple.result.xml"

Scenario: Candidate list with a clear winner
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateC
Then result seat #0 isn't CandidateA
Then result seat #0 isn't CandidateB
Then result seat #0 isn't CandidateD
Then result seats contain CandidateC
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD

Scenario: Candidate list with a tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateA
Then result seats contain CandidateC
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD

Scenario: Candidate list with a false tie
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 125 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateC
Then result seat #0 isn't CandidateA
Then result seat #0 isn't CandidateB
Then result seat #0 isn't CandidateD
Then result seats contain CandidateC
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD

Scenario: Candidate list with a full tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 200 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 200 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateA
Then result seats contain CandidateB
Then result seats contain CandidateC
Then result seats contain CandidateD