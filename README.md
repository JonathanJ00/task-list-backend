# Task List Backend
Backend part of submission for challenge described here here: https://github.com/hmcts/dts-developer-challenge

## Prerequisites
- Java 21
- Gradle 8.14.3.

## Start guide
Either start within your preferred IDE or using the below command:
~~~
.\gradlew bootRun
~~~

By default the application will start with the URL http://localhost:8080.

## User Guide
This application provides an interface allowing the creation, reading, updating and deletion of tasks. Please see open-api.yaml for details of this api but example commands are given below. Please note, these examples were generated using Postman.

### Create a Task
~~~
curl --location 'http://localhost:8080/create' \
--header 'Content-Type: application/json' \
--data '{
  "title": "Complete project report",
  "description": "Finish writing the final project report and submit it to the team lead.",
  "status": "IN_PROGRESS",
  "date": "2025-07-31 17:00"
}'
~~~

### Retrieve all Tasks
~~~
curl --location 'http://localhost:8080/tasks'
~~~

### Retrieve a Specific Task by ID
~~~
curl --location 'http://localhost:8080/2402'
~~~

### Update a Task by ID
~~~
curl --location --request PUT 'http://localhost:8080/2402' \
--header 'Content-Type: application/json' \
--data '{
    "status" : "COMPLETED"
}'
~~~

### Delete a Task by ID
~~~
curl --location --request DELETE 'http://localhost:8080/2402'
~~~