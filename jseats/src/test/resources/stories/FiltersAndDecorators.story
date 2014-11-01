FiltersAndDecorators story

Narrative:
In order to enhance processor versatility
As a development team
I want to be able to add TallyFilters and ResultDecorators
					 
Scenario:  Add Null filter and decorator
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given tally has filter Null 
Given result has decorator Null 
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

Scenario:  Filter out candidates below 1000 votes
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given tally has filter RemoveCandidatesBelow 1000
Given result has decorator Null 
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD

Scenario:  Filter out candidates below 1000 votes and append text ot name
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given tally has filter RemoveCandidatesBelow 150
Given result has decorator SuffixTextToCandidateNameDecorator My
Given result has decorator AppendTextToCandidateNameDecorator -Winner
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is MyCandidateC-Winner
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD
