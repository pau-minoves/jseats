JSeats [![Build Status](https://travis-ci.org/pau-minoves/jseats.svg?branch=master)](https://travis-ci.org/pau-minoves/jseats)
======



JSeats is a java implementation of common electoral seat allocation algorithms.

## Getting started

 * Example [usage](src/test/java/org/jseats/unit/ExampleProcessorTest.java)
 * BDD [stories](src/test/resources/stories)

## Supported methods
 
 * Majority Method
 	* Simple
    * Qualified
    * Absolute
 * Ranked Method
    * By votes (direct and reverse).
 * Equal Proportions Method (aka [Huntington-Hill](http://en.wikipedia.org/wiki/Huntington%E2%80%93Hill_method))
 * [Largest Remainder Method](http://en.wikipedia.org/wiki/Largest_remainder_method)
 	* Hare
 	* Droop
 	* Imperiali
 * [Highest Averages Method](http://en.wikipedia.org/wiki/Highest_averages_method)
 	* D'Hondt
 	* Sainte-Laguë (Webster)
 		* Supports user provided first divisor (Modified Sainte-Laguë)
 	* Imperiali
 	* Danish
