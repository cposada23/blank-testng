# TESTNG | AUTOMATION CHALLENGE
#### _challenge to test web automation skills_

Here are the instructions to run the tests

## Installation

Required dependencies
- Java17
- Maven 3.9.2 or Just use maven wrapper from IntelliJ

## Running the tests:

#### From command line
```
sh ./runTest.sh
```
#### You can run the test in Chrome and firefox, changing the browser in the properties file
- Properties file folder: src/test/resources/config/test.properties
```sh
# In the .properties file you can see this options
browser=chrome
headless=false
# Change them accordingly to the needs
```

## Reports and logs:
#### You can find the report for the test execution, sorted by date in the Reports folder