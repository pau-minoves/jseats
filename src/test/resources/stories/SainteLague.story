Sainte-Lague story

Narrative:
In order to assign seats according to Highest Averages with a Sainte-Lague divisor
As a development team
I want to use Sainte-Lague

Scenario: Sainte-Lague scenario from wikipedia (http://en.wikipedia.org/wiki/Highest_averages_method)
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
When process with Sainte-Lague algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Whites
Then result seat #2 is Reds
Then result seat #3 is Yellows
Then result seat #4 is Greens
Then result seat #5 is Yellows
Then result seat #6 is Yellows
Then result seat #7 is Blues
Then result seat #8 is Whites
Then result seat #9 is Reds
Given algorithm has property groupSeatsPerCandidate set to true
When process with Sainte-Lague algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Yellows
Then result seat #3 is Yellows
Then result seat #4 is Whites
Then result seat #5 is Whites
Then result seat #6 is Reds
Then result seat #7 is Reds
Then result seat #8 is Greens
Then result seat #9 is Blues

Scenario: Modified Sainte-Lague scenario from wikipedia (http://en.wikipedia.org/wiki/Highest_averages_method)
Given empty scenario
Given tally has candidate Yellows with 47000 votes
Given tally has candidate Whites with 16000 votes
Given tally has candidate Reds with 15900 votes
Given tally has candidate Greens with 12000 votes
Given tally has candidate Blues with 6000 votes
Given tally has candidate Pinks with 3100 votes
Given tally has 100000 potential votes
!-- All potential votes are casted (potential votes == effective votes)
Given algorithm has property firstDivisor set to 1.4
Given algorithm has property numberOfSeats set to 10
When process with Sainte-Lague algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Whites
Then result seat #3 is Reds
Then result seat #4 is Yellows
Then result seat #5 is Greens
Then result seat #6 is Yellows
Then result seat #7 is Whites
Then result seat #8 is Reds
Then result seat #9 is Yellows
Given algorithm has property groupSeatsPerCandidate set to true
When process with Sainte-Lague algorithm
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

Scenario: Sainte-Lague scenario modified from govt.nz (http://www.electionresults.govt.nz/electionresults_2011/saint_lague.html)
!-- Note: Calculating with OTHER as if it was a party
Given empty scenario
Given tally has candidate NATIONAL_PARTY with 1058636 votes
Given tally has candidate LABOUR_PARTY with 614937 votes
Given tally has candidate GREEN_PARTY with 247372 votes
Given tally has candidate NEW_ZEALAND_FIRST_PARTY with 147544 votes
Given tally has candidate MAORI_PARTY with 31982 votes
Given tally has candidate MANA with 24168 votes
Given tally has candidate ACT_NEW_ZEALAND with 23889 votes
Given tally has candidate UNITED_FUTURE with 13443 votes
Given tally has candidate OTHER with 75493 votes
Then tally has 2237464 effective votes
Given tally has 2237464 potential votes
!-- All potential votes are casted (potential votes == effective votes)
Given algorithm has property numberOfSeats set to 70
!-- Given algorithm has property groupSeatsPerCandidate set to true
When process with Sainte-Lague algorithm
Then print result
Then result type is MULTIPLE
Then result has 70 seats
Then result has 33 seats for NATIONAL_PARTY
Then result has 19 seats for LABOUR_PARTY
Then result has 8 seats for GREEN_PARTY
Then result has 5 seats for NEW_ZEALAND_FIRST_PARTY
Then result has 1 seats for MAORI_PARTY
Then result has 1 seat for MANA
Then result has 1 seat for ACT_NEW_ZEALAND
Then result has 0 seat for UNITED_FUTURE
Then result has 2 seats for OTHER

Scenario: Party List seat allocation from New Zealand General Election 2014
!-- Reference: http://www.electionresults.govt.nz/electionresults_2014/e9/html/statistics.html
!-- Quotients: http://www.electionresults.govt.nz/electionresults_2014/e9/html/e9_part2.html
!-- Comment: the election described here allocates seats based on MMP (Mixed Member Proportional). 
!--          Here only the SaintLague part of the MMP is calculated. This is why numbers require 
!--          some interpretation as they don't match exactly.
Given empty scenario
Given tally has candidate NATIONAL_PARTY with 1131501 votes
Given tally has candidate LABOUR_PARTY with 604535 votes
Given tally has candidate GREEN_PARTY with 257359 votes
Given tally has candidate NEW_ZEALAND_FIRST_PARTY with 208300 votes
Given tally has candidate MAORI_PARTY with 31849 votes
Given tally has candidate ACT_NEW_ZEALAND with 16689 votes
Given tally has candidate UNITED_FUTURE with 5286 votes
Given algorithm has property numberOfSeats set to 120
Given algorithm has property groupSeatsPerCandidate set to true
When process with Sainte-Lague algorithm
Then print result
Then result type is MULTIPLE
Then result has 120 seats
Then result has 60 seats for NATIONAL_PARTY
Then result has 32 seats for LABOUR_PARTY
Then result has 14 seats for GREEN_PARTY
Then result has 11 seats for NEW_ZEALAND_FIRST_PARTY
Then result has 2 seats for MAORI_PARTY
Then result has 1 seat for ACT_NEW_ZEALAND
Then result has 0 seat for UNITED_FUTURE

