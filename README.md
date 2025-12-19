# mendel-challenge

## Table of Contents
[High-level architecture](#High-level-architecture)

[Tech stack](#Tech-stack)

[Build and run the application](#Build-and-run-the-application)

[Persistence](#Persistence)

[API documentation](#API-documentation)

[Integration tests](#Integration-tests)

[Further testing with Postman](#Further-testing-with-Postman)

[Docker](#Docker)

## High-level architecture

This is a three tier architecture consisting of the following modules:
* Api
* Service
* Repository

The API is a REST API.

## Tech stack

* Java 17
* SpringBoot
* Spring Web
* Maven
* Lombok
* JUnit
* RestAssured
* Docker

## Build and run the application

Clone GitHub repository locally:

`https://github.com/joaquinrojkind/mendel-challenge`

From the terminal window in the project's root directory compile the project:

`mvn clean install`

Run the application, it uses port `8090`, make sure it's not in use.

`mvn spring-boot:run`

## Persistence

Since the requirements indicate that persistence should be solved in-memory there's a class that takes care to emulate a repository and a database.

The class is `src/main/java/com/mendel/persistence/repository/TransactionRepositoryImpl.java`

The database is represented by a Map and the repository interacts with it to simulate database operations.

For debuging and testing purposes the repository will log the entire state of the database to the console upon every operation.

## API documentation

-----

`PUT /transactions/{transactionId}`

Request body

```
{
    "amount": 1200.00,
    "type": "Credit",
    "parentId": 12
}
```

Returns the transaction details

```
{
    "transactionId": 11,
    "amount": 1200.0,
    "type": "Credit",
    "parentId": 12
}
```

-----

`GET /transactions/types/{type}`

Returns the ids of the matching transactions

```
[11, 12]
```

-----

`GET /transactions/sum/{transactionId}`

Returns the sum of amounts of all the transactions associated to this transactionId through the parentId property. The sum includes the parent transaction's amount too.

```
{ "sum", 1200.00 }
```

## Integration tests

Full coverage tests have been provided with the class `src/test/java/com/mendel/TransactionIntegrationTests.java`. Tests will run as a part of the `mvn clean install` command. To run tests only execute:

`mvn test`

## Further testing with Postman

Download the Postman collection from `src/main/resources/postman` and import it into the Postman app in order to test all the endpoints.

## Docker

With the docker daemon running (for example with Docker Desktop) and standing on the project's root directory execute the following:

Build image:

`docker image build -t mendel-challenge:latest .`

Run container:

`docker run -d -p 127.0.0.1:8090:80 mendel-challenge`
 
<b>[TODO]</b>

The image is built correctly, the container runs alright and the app starts app alright too, however there's a problem with the ports binding since the app cannot be reached from the outside. This is a pending fix.