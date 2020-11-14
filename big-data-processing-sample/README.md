# big-data-processing-sample

## Task

### Business logic

Program which calculate linux syslog priority count by hours. Is given by:

* 0 - EMERGENCY;  
* 1 - ALERT;
* 2 - CRITICAL;
* 3 - ERROR;
* 4 - WARNING;
* 5 – NOTICE;
* 6 – INFORMATIONAL;
* 7 – DEBUG.

#### Ingest technology

Kafka producer

#### Storage technology

Ignite Native Persistence

#### Computation technology

Ignite compute

### Report includes

* ZIP-ed src folder with your implementation
* Screenshot of successfully executed tests
* Screenshots of successfully executed job and result (logs)
* Quick build and deploy manual (commands, OS requirements etc)
* System components communication diagram (UML or COMET)

### General criteria

1) IDE agnostic build: Maven, Ant, Gradle, sbt, etc (10 points)
2) Unit tests are provided (20 points)
3) Code is well-documented (10 points)
4) Script for input file generation or calculation setup and control (10 points)
5) Working application that corresponds business logic,
 input/output format and additional requirements
 that has been started on cluster (30 points)
6) The relevant report was prepared (20 points)
