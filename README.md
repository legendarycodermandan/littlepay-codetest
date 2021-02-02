# Littlepay Code Test - Danny Reiner

## Assumptions

```
*Input file does not contain commas in the field data
*Taps are in order
*The companyId is the same for tap on and tap off

```

## Background
This application was written using java 14 and Maven 3.6.3 as the package manager.  Junit 5  was used as the testing framework.
A command line application is generated to run the app.

## Usage
Unit tests can be run with the following command from within the root folder:
```
mvn clean test
```

A jar file can be created by running the following command from within the root folder:
```
mvn clean package
```
Once the previous command has been run, a jar file(littlepay-codetest-1.0-SNAPSHOT-jar-with-dependencies.jar) should be found within the "target" folder.

To run the application, two arguments are needed.  The first is the absolute path for the taps input file, and the second argument is the path and file name where the output file should be created.
For example, to run the application with the source file at "/home/danny/DEL/input.csv" and the destination being "/home/danny/DEL/output.csv", the following command would be executed:
```
java -jar littlepay-codetest-1.0-SNAPSHOT-jar-with-dependencies.jar /home/danny/DEL/input.csv /home/danny/DEL/output.csv
```

## A self review and some comments on my code

```
Perhaps could have included a Maven Wrapper
some magic strings
not DRY
Perhaps could have used an OO design along the lines of Complete/Incomplete/Cancelled trips being subclasses of an abstract "Trip" class and overriding a method to calculate it's own concrete type of trip.
Could have used a logging library rather than SysOuts
Could have refactored the input reader to use opencsv (as I used for the output writer)
Better exception handling
```