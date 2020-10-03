# hadoop-sample

![Test and build hadoop-sample](https://github.com/Shipaaaa/DS-BDA/workflows/Test%20and%20build%20hadoop-sample/badge.svg?branch=develop)

## Task

### Business logic

Program which searches the longest word in txt file (only ASCII symbols must be accepted).

### Output Format

CSV file.

### Additional Requirements

Counters is used for statistics about malformed rows collection.

### Report includes

#### Mandatory

* ZIP-ed src folder with your implementation
* Screenshot of successfully executed tests
* Screenshot of successfully uploaded file into HDFS
* Screenshots of successfully executed job and result
* Quick build and deploy manual (commands, OS requirements etc)

#### Optional

* Screenshot of usage counters

### General criteria

1) IDE agnostic build: Maven, Ant, Gradle, sbt, etc (10 points)

2) Unit tests are provided (20 points)

3) Code is well-documented (10 points)

4) Script for input file generation or extraction to HDFS must be provided (10 points)

5) Working MapReduce application that corresponds business logic, input/output 
format and additional Requirements that has been started on Hadoop cluster 
(not singleton mode) (30 points)

6) The relevant report was prepared (20 points)
