# Illumio Technical Challenge

## Introduction

This project is a technical challenge provided by Illumio, which involves parsing network flow log data, matching it to a lookup table, and generating a summary report. The challenge is designed to demonstrate proficiency in Java, design patterns, and clean code principles.

## Problem Description

The task is to parse network flow log files, match each record against a lookup table to determine the appropriate tag, and then output a summary report of the tag counts. The project includes handling different versions of network flow logs and ensuring robust error handling for invalid records.

## Compilation and Run Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/GaurangJotwani/IllumioChallenge
   cd IllumioChallenge/
   javac -d out -sourcepath src src/runner/FlowLogCounterRunner.java
   java -cp out runner/FlowLogCounterRunner
    ```
## Input Files:

- **Lookup File:** `test/LookUp_Table_Sample.csv`
- **Network Flow Log File:** `test/Sample_Records_V2.csv`
- **Output File:** `test/output`
- **NetworkLogVersion:** `2`

## Demo Run:


## Key Assumptions

- The input files (lookup table and network flow records) are in CSV format.
- The first row of each CSV file is assumed to contain headers.
- Any records that cannot be parsed or contain invalid columns (e.g., strings in integer columns) are ignored.
- The program is designed to handle version 2 and 3 of network flow using the order here: https://docs.aws.amazon.com/vpc/latest/userguide/flow-log-records.html
- All unique protocol/ports combinations are counted even if they dont exists in lookup table

## High-Level Design Patterns

- **Singleton Pattern:** Used for the IANA to protocol name mapping (`IanaProtocolMapper`) to ensure a single instance is used throughout the application and map is not initialized repeatedly.
- **Generic Class:** Created for the `CSVparser` and `NetworkFlowRecordLoader` to handle various data types and file structures. 
- **Used SOLID OOP Principles:** Kept a Parser Interface since we can have json, XML parsers in the future keeping scalability in mind 
- **Strategy Pattern:** Employed to parse network flow logs in `NetworkFlowRecordLoader` according to the version specified (e.g., V2 or V3).
- **Dependency Injection:** The `FlowLogCounter` class is designed to be modular by injecting dependencies like `NetworkFlowLogLoader`, `MatchingService`, and `Parser`, making the code easier to test and maintain.

## Integration Testing

Integration test were written for:

- **CSV Parser:** Ensures the parser correctly interprets and handles CSV files with various data types and structures.
- **Matching Algorithm:** Validates the accuracy and performance of the algorithm used to match flow records against the lookup table.
- **Edge Cases:** Edge cases like missing files, invalid data and case-sensitivity errors were tested

## Data Structures

- **HashMap:** Multiple HashMaps were used to store and quickly retrieve the tag for each combination of destination port and protocol, ensuring efficient lookups during the matching process.

## Areas for Further Improvement

If more time were available, the following improvements could be made:

- **Enhanced Error Handling:** Implement more sophisticated error handling by creating custom exceptions
- **Implement Unit Tests with Mocking:** Expand the unit tests to cover edge cases and integration testing by introducing mocking library.
- **Configuration Management:** Introduce configuration files or environment variables to manage file paths and other parameters.
