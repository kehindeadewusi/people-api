# People API
A simple CRUD API.

Note: This branch does not enable Spring Security, please switch to the feature/secure-it which supports client credentials flow.

This code targets Java 11, but would also works on Java 8, you'd have to change the targets in pom.xml though. 
```
<java.version>11</java.version>
```
and set to 1.8 for Java 8 support. The included Dockerfile uses an OpenJDK 11 base image though.

The API supports the following operations:

1. Store (Create), Adds a new person.
1. Edit a person
1. Retrieve a list of persons: GET /persons/
1. GET a single person: GET /persons/{id}
1. DELETE a single person: DELETE /persons/{id}

The API documentation is provided using Spring RestDocs, which tests the controller layer and generates documentation snippets. 
To get the documentation, you need to package the application. 

```
mvn package or ./mvnw package
```

After successfully testing the REST controllers, the documentation will be available in /target/generated-docs/index.html. 

A sample pdf output is included in the docs folder as PersonAPI.pdf

## Instructions

In development mode, you may start the application with the bootstrapped mvn script:
```
./mvnw spring-boot:run
```

## Running with Docker Image
There are a number of ways, but for simplicity...

### Multi-Step Docker file
I added a simple Dockerfile to do a multi-stage build, this would be very easy to test if you have Docker desktop. It takes time to run though the first stage of the build which would normally be part of a CI pipeline.

```
docker build -t me/person-test:0.9.1 .
```
You may run it with the following command
```
docker run --rm -p 9091:8080 me/person-test:0.9.1
```

### Spring Boot Image Builder
Probably the simplest is to use the built-in option from Spring Boot.
```
mvn spring-boot:build-image
```
This will create an image based on the artifactId and version number in the pom.xml e.g. 
```
docker.io/library/people-api:0.0.1-SNAPSHOT
```
Then you can run the image locally using the following command e.g. 

```
docker run -p 9090:8080 -t docker.io/library/people-api:0.0.1-SNAPSHOT
```
Normally, this would be part of some workflow and be deployed to a container orchestration platform like Kubernetes.
