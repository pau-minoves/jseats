Sample story

Narrative:
In order to run method configurations
As a user
I want to run a command line interface
					 
Scenario: CLI with a candidate list has one candidate with enouth votes to reach exact absolute majority.
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 501 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 1000 potential votes
!-- Absolute majority minimum votes is 501
When execute command with params at stories/cli/AbsoluteMajorityScenario1.params
!-- Then result is as in file $file
