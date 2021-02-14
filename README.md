# People API
A simple CRUD API to demonstrate a bit of expertise :)

This code targets Java 11, but also works on Java 8, you'd have to change the targets in pom.xml though. 
```
<java.version>11</java.version>
```
Please set to 1.8 for Java 8 support.

The API supports the following operations:

1. Store (Create), Adds a new person.
1. Edit a person
1. Retrieve a list of persons: GET /persons/
1. GET a single person: GET /persons/{id}
1. DELETE a single person: DELETE /persons/{id}

The API documentation is provided using Spring RestDocs, with also tests the controller layer. To get the documentation, you need to package the application. 

```
mvn package or ./mvnw package
```

After successfully testing the REST controllers, the documentation will be available in /target/generated-docs/index.html. 

## Instructions

In development mode, you may start the application with the bootstrapped mvn script:
```
./mvnw spring-boot:run
```
