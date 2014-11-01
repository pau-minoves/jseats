Sample story

Narrative:
In order to run method configurations
As a user
I want to run a command line interface
					 
Scenario: Use CLI to compute absolute-majority result, saving config and result in disk.
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 501 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 1000 potential votes
!-- Absolute majority minimum votes is 501
When execute command with parameters at create-absolute-majority-result.params
Given result is in file result.xml
Then result type is SINGLE
Then result has 1 seats
Then result seats contain CandidateC
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD

Scenario: Use CLI to load previos config, adding a candidate and changing method.
When execute command with parameters at load-config-and-do-dhondt-result.params
Given result is in file result.2.xml
Then result type is MULTIPLE
Then result has 5 seats
Then result has 3 seats for CandidateC
Then result has 2 seats for CandidateZ

Scenario: Reuse configuration but load alternative tally.
When execute command with parameters at load-config-and-replace-tally-result.params
Given result is in file result.3.xml
Then result type is MULTIPLE
Then result has 4 seats
Then result has 3 seats for Party3
Then result has 1 seats for Party2
