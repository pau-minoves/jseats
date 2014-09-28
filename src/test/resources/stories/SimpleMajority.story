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
Then result single candidate is CandidateC
Then result single candidate isn't CandidateA
Then result single candidate isn't CandidateB
Then result single candidate isn't CandidateD
Then result candidates contain CandidateC
Then result candidates do not contain CandidateA
Then result candidates do not contain CandidateB
Then result candidates do not contain CandidateD

Scenario: Candidate list with a tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result candidates contain CandidateA
Then result candidates contain CandidateC
Then result candidates do not contain CandidateB
Then result candidates do not contain CandidateD

Scenario: Candidate list with a false tie
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 125 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result single candidate is CandidateC
Then result single candidate isn't CandidateA
Then result single candidate isn't CandidateB
Then result single candidate isn't CandidateD
Then result candidates contain CandidateC
Then result candidates do not contain CandidateA
Then result candidates do not contain CandidateB
Then result candidates do not contain CandidateD

Scenario: Candidate list with a full tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 200 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 200 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result candidates contain CandidateA
Then result candidates contain CandidateB
Then result candidates contain CandidateC
Then result candidates contain CandidateD