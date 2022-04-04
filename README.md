# Log Analyzer

The log analyzer app reads the input log file, analyzes it based on the requirements definition and writes the events data to HSQLDB in the required format. 
Check inline code docs for a better understanding.

## Dependencies 

* Java 8
* Jackson - for deserializing event log json
* Logback - logging


## Running the solution 

  Command
  
     ./gradlew run --args='logfile.txt'
     
  Output 
     
     02:31:27.296 [main] INFO  c.c.loganalyzer.util.ConnectionUtil - Connected to DB URL:jdbc:hsqldb:file:hsqldb/eventsStore
     02:31:27.445 [main] INFO  c.c.l.util.EventLogObjectMapper - Created 'events' table
     02:31:27.446 [main] INFO  com.creditsuisse.loganalyzer.App - Started reading log file: logfile.txt
     02:31:27.502 [main] INFO  c.c.l.util.EventLogObjectMapper - Event written to DB: Event{id='scsmbstgra', duration=5, alert=true, host=Optional[12345], type=Optional[APPLICATION_LOG]}
     02:31:27.502 [main] INFO  c.c.l.util.EventLogObjectMapper - Event written to DB: Event{id='scsmbstgrc', duration=8, alert=true, host=Optional.empty, type=Optional.empty}
     02:31:27.503 [main] INFO  c.c.l.util.EventLogObjectMapper - Event written to DB: Event{id='scsmbstgrb', duration=3, alert=false, host=Optional.empty, type=Optional.empty}
     02:31:27.503 [main] INFO  com.creditsuisse.loganalyzer.App - Finished reading log file: logfile.txt
     02:31:27.506 [main] INFO  c.c.l.util.EventLogObjectMapper - All events in DB: [Event{id='scsmbstgra', duration=5, alert=true, host=Optional[APPLICATION_LOG], type=Optional[12345]}, Event{id='scsmbstgrc', duration=8, alert=true, host=Optional.empty, type=Optional.empty}, Event{id='scsmbstgrb', duration=3, alert=false, host=Optional.empty, type=Optional.empty}]
     02:31:27.507 [main] INFO  c.c.l.util.EventLogObjectMapper - Deleted all events from DB
     02:31:27.507 [main] INFO  c.c.loganalyzer.util.ConnectionUtil - Closed DB connection
     
     

## Limitations of the solution

* If the app is restarted, the app would start processing the file again from beginning. 
  It doesn't store an offset to know how much it processed successfully before restarting.

* The app doesn't handle duplicate event logs gracefully.

* An in-memory map is used to temporarily hold event data for calculating duration and setting alert flag. 
  Cons of using an in-memory map:
  * We would lose the stored event logs whenever the app is restarted.
  * For very high scale, we would have memory limitation. The log data would then need to be cached in a persistent store for processing.

* Exception handling needs to be better. Implement custom exceptions to handle exceptions appropriately. 

## Testing 

* Unit test is written using JUnit 5
* Run unit test
    
    `./gradlew test`

Future test improvements:
* Improve test coverage. Currently, only happy path tests are written due to time constraint.
* Write E2E tests
* Write load test to understand app capacity for a given set of resources

## Deployment options

* As container on K8s 
* As fat jar

## Scale 

* It is memory intensive. Control memory (heap) using `Xmx` and `Xms`

## Resilience

* Implement app redundancy / auto-scaling
* Implement retries for processing failures.
* Implement data replication to avoid data loss etc.

## Monitoring 

Logging 
* uses Logback - logs at different log levels
* logs both to File (logs/loganalyzer.log) and standard output
