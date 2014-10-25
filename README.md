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
 * [Largest Remainder Method](http://en.wikipedia.org/wiki/Largest_remainder_method)
 	* Hare
 	* Droop
 	* Imperiali
 * [Highest Averages Method](http://en.wikipedia.org/wiki/Highest_averages_method)
 	* D'Hondt
 	* Sainte-Laguë (Webster)
 		* Supports user provided first divisor (Modified Sainte-Laguë)
 	* Huntington-Hill
 	* Imperiali
 	* Danish

## Usage

Currently JSeats provides both a clean java API and a command line launcher. A web interface is planned. The command line looks like this:

```
$ java -jar jseats.jar -h
Usage: JSeats [options]
  Options:
    -c, --candidate
       Add candidate to tally. Candidates follow the format Name:Votes.
    -h, --help
       Print this message.
       Default: false
    -ic, --input-config
       Configuration input file.
    -it, --input-tally
       Tally input file. Overrides tally provided in configuration via
       --input-config, if any.
    -lD, --list-decorators
       List built-in result decorators.
       Default: false
    -lF, --list-filters
       List built-in tally filters.
       Default: false
    -lM, --list-methods
       List built-in seat allocation methods.
       Default: false
    -m, --method
       Seat allocation method to use. See --list-methods for available methods.
    -oc, --output-config
       Configuration output file.
    -o, --output-result
       Result output file.
    -pv, --potential-votes
       Potential votes. If not set, defaults to effective votes (sum of all
       casted votes).
       Default: -1
    -D, --processor-property
       Processor properties as in -D numberOfSeats=105
    -v, --verbose
       Level of verbosity.
       Default: false
```

