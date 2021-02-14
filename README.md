# People API
A simple CRUD API

Note: This branch feature/secure-it supports client credentials flow, the main branch does not include any security and is publicly available.

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

## Spring Security Support
This branch supports Spring Security that makes this a resource server. The GET APIs are still publicly available but the POST, PUT and DELETE uses OAUTH2 Client Credentials. This requires a bit of configuration. 

### Settings
The application.properties file include two settings that needs to be replaced, I left my test settings as default:

```
oauth2.audience=${OAUTH2_AUDIENCE:http://localhost:8080}
```
```
spring.security.oauth2.resourceserver.jwt.issuer-uri=${OAUTH2_ISSUER_URI:https://kehinde.eu.auth0.com/}
```

The client credentials flow expects a client credentials exchange to get an access token. Using my test settings on Auth0

```
curl --request POST \
  --url https://kehinde.eu.auth0.com/oauth/token \
  --header 'content-type: application/json' \
  --data '{"client_id":"TwFzyrwxTDxvEQuO4U9GNNfBj5hPsjih","client_secret":"VKOHLXcEeDzYLu8Ys3rzksK1mjW3r-EdNKQEl1m8F2v7r6Hwz4VQmEAiQGk4V4Ln","audience":"http://localhost:8080","grant_type":"client_credentials"}'
```

This returns an access token that can be used to make API calls to the resource server endpoints.

```
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik0wUkRNemd6UmtVeU9EQkRNa1EyUlROR1FqVTFNRFV3UXpSQk1UTXdNekE0T0RFMFJUUXhRUSJ9.eyJpc3MiOiJodHRwczovL2tlaGluZGUuZXUuYXV0aDAuY29tLyIsInN1YiI6IlR3Rnp5cnd4VER4dkVRdU80VTlHTk5mQmo1aFBzamloQGNsaWVudHMiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJpYXQiOjE2MTMzMjYwMDYsImV4cCI6MTYxMzQxMjQwNiwiYXpwIjoiVHdGenlyd3hURHh2RVF1TzRVOUdOTmZCajVoUHNqaWgiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.s4Hxa86hAcyYubz1eUZY-60nTfxZ1clon_sFHHnPxFFoMaXnB2NQAfnJ5y8FErsBhe8T17jVMUEscWW-jFbjkIo2H6eNz39_TREf13ILRKTJRoHPbCsHCgA2s-W_LGzu7IX_5XwMSNBCeI0CMf84h55gLahv-Wrim2Xf8on2IO6AWaCi2iiIU7cMEOJcyIrPBnlXdAEqxcCeRJU51M2XwfEJ1_GMVfNB-MO98D2eldaOUyt5HAW-AYtrvnGR2esqHjAiRRv4jBuEPlz1GYLxn5iOHMaXy0tFcziauRpe7SrMsdeQyexR4RXAsWfkq0o7XN0TEwxtM7zmunUVjwwf3g",
  "token_type": "Bearer"
}
```

Finally, make API requests as follows:

```
POST /persons HTTP/1.1
Host: 127.0.0.1:9091
Accept: application/json
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik0wUkRNemd6UmtVeU9EQkRNa1EyUlROR1FqVTFNRFV3UXpSQk1UTXdNekE0T0RFMFJUUXhRUSJ9.eyJpc3MiOiJodHRwczovL2tlaGluZGUuZXUuYXV0aDAuY29tLyIsInN1YiI6IlR3Rnp5cnd4VER4dkVRdU80VTlHTk5mQmo1aFBzamloQGNsaWVudHMiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJpYXQiOjE2MTMzMjYwMzUsImV4cCI6MTYxMzQxMjQzNSwiYXpwIjoiVHdGenlyd3hURHh2RVF1TzRVOUdOTmZCajVoUHNqaWgiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.CO_05Ewqj7RpCxBMzVcOaCtNQCVcPxAzzy2qPJQJ9i2WUYaYhZ5gqUwKk5-EWCzn8uZYKyv3PDahRT89jsG4bImgL_eulD1n6hj1sHj_szq8-ajOxpD5KxwcvwFA-LDngK-ja2eG04DoBY0YQ0g-5vOi4jSwRgT5ikIRXethuzHxG7InbskCA8vJ9qLvB8NKBqV3yqw8tMmg6c83LOCgmcpBAI1tHBrRK81LAto-hwlmjUJXl83lRGyCsdId9M306iMFp8LcKomAlpOqnVhP1tMAxxUxj-fZHcHh838_5obgE5kJrrpGkdt4n9sDEygeymNZzP9wK4MrFdUllmJr3A
Cookie: JSESSIONID=4C1CBB8EDACFCD912B4B7E6E94102F64
Content-Length: 108

{
    "age": 80,
    "first_name": "Bolaji",
    "last_name": "Funmilayo",
    "favourite_colour": "BROWN"
}
```

### Starting the docker container

```
docker run --rm --env OAUTH2_AUDIENCE=ttp://localhost:8080 --env OAUTH2_ISSUER_URI=https://kehinde.eu.auth0.com/ -p 9091:8080 me/person-test:0.9.1
```
For this to run properly in a Docker container, ensure the container can reach the authorization server.