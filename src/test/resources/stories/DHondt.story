D'Hondt story

Narrative:
In order to assign seats according to Highest Averages with a D'Hondt divisor
As a development team
I want to use DHondt

Scenario: DHondt scenario from wikipedia (http://en.wikipedia.org/wiki/Highest_averages_method)
Given empty scenario
Given tally has candidate Yellows with 47000 votes
Given tally has candidate Whites with 16000 votes
Given tally has candidate Reds with 15900 votes
Given tally has candidate Greens with 12000 votes
Given tally has candidate Blues with 6000 votes
Given tally has candidate Pinks with 3100 votes
Given tally has 100000 potential votes
!-- All potential votes are casted (potential votes == effective votes)
Given algorithm has property numberOfSeats set to 10
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Whites
Then result seat #3 is Reds
Then result seat #4 is Yellows
Then result seat #5 is Greens
Then result seat #6 is Yellows
Then result seat #7 is Yellows
Then result seat #8 is Whites
Then result seat #9 is Reds
Given algorithm has property groupSeatsPerCandidate set to true
Given result has decorator AppendTextToCandidateNameDecorator My

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