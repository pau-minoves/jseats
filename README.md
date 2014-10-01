JSeats [![Build Status](https://travis-ci.org/pau-minoves/jseats.svg?branch=master)](https://travis-ci.org/pau-minoves/jseats)
======



JSeats is a java implementation of common electoral seat allocation algorithms.

## Getting started

 * Example [usage](src/test/java/org/jseats/unit/ExampleProcessorTest.java)
 * BDD [stories](src/test/resources/stories)

## Supported methods
 
 * Majority
 	* Simple
    * Qualified
    * Absolute
 * Largest Remainder Method
 	* Hare
 	* Droop
 	* Imperiali
 * Highest Averages Method
 	* D'Hondt
 	* Sainte-Laguë (Webster)
 		* Supports user provided first divisor (Modified Sainte-Laguë)
 	* Huntington-Hill
 	* Imperiali
 	* Danish
