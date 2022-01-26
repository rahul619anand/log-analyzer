# Energy estimator 

## Dependencies 

* Java 8 or above

## App Algorithm
* parses messages submitted via stdin
* stores messages in an in-memory store 
* calculates and outputs the estimated energy, once it receives an EOF event

### Edge cases

Some edge cases noticed are as follows:
* First and last message should be Turnoff - its told to be assumed but would be better to put a check on this.
* Delta messages should have a value
* Message type should always be either Delta or Turnoff
* The timestamp format must be valid
* The dimmer change value should be valid, within -1.0 and 1.0
* The light dimmer value should be valid, within 0 and 1.0

Not all the above mentioned edge case is implemented. This is mainly due to time constraint.
Some edge cases, when detected and leading to an invalid message, are logged and ignored.
They should be given more care and reported appropriately.

## Running the solution 

    ./gradlew run

Example 1: 

    ./gradlew run
    
    1544206562 TurnOff 
    1544206563 Delta +0.5 
    1544210163 TurnOff 
    Ctrl + D
    
    03:41:08.693 [main] INFO  c.n.e.util.EnergyEstimatorUtil - Estimated energy used: 2.5Wh


Example 2: 

    ./gradlew run
    
    1544206562 TurnOff    
    1544206563 Delta +0.5 
    1544210163 Delta -0.25
    1544211963 Delta +0.75
    1544211963 Delta +0.75
    1544213763 TurnOff    
    
    03:45:40.941 [main] INFO  c.n.e.util.EnergyEstimatorUtil - Estimated energy used: 5.625Wh


Note: Pressing `Ctrl + D` signifies an EOF


## Solution to challenges

Deduplication
* Using a map so that messages with duplicate timestamps are overriden

Out-of-order delivery 
* The messages are sorted based on time before energy estimation.

## Message store 

* Currently, the application uses an in-memory map to store the messages. This is mainly due to time constraint.

Cons of using an in-memory store:
* It would lose the stored messages whenever the app is restarted.
* Message storage capacity is limited by app memory. 

* In production, use a database for persistence.
* Kafka (a message queue) is another way of keeping things in a transient store and scale up processing.


## Testing 

* Unit test is written using JUnit 5
* Run unit test
    
    `./gradlew test`

Future test improvements
* Improve test coverage for edge cases
* Write E2E test testing EnergyEstimatorApp
* Write load test to understand capacity


## Deployment options

* As container on K8s 
* As fat jar

## Scale 

* It is memory intensive. Control memory (heap) using `Xmx` and `Xms`
* It is also compute intensive. 

## Resilience

* Redundancy / auto-scaling
* Retries for processing failures 
* Data replication to avoid data loss etc.

## Monitoring 

Logging 
* uses Logback 
* logs both to File (logs/app.log) and standard output
