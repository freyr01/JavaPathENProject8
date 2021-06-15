# TourGuide
TourGuide is an application multi-platform about upgrading tourism experiences of users by features based on GPS.
This app is splitted into multiple micro services

This application use different technologies to achieve his purpose like:
- Java 8
- Spring Boot
- Docker

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

- Java >= 1.8
- Docker >= 20

### Installing environment

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Docker:

https://docs.docker.com/engine/install/

### Installing App

1.Clone this project using git:

`git clone <url>`

2.Build and run the app in your docker environment:

`gradlew runDocker`

this will build all micro services needed and add them to your docker environment.

### Running Tests

Run tests:

`gradlew test`

note that TourGuide tests need other micro services to complete, so all needed micro services will be build and add to your docker environment. After test complete, this test environment will be cleaned.

### URLs

SpringBoot will open four sockets for each micro services:

- TourGuide: http://localhost:8080
- TourGuideGpsUtil: http://localhost:8081
- TourGuideTripPricer: http://localhost:8082
- TourGuideGpsRewardCentral: http://localhost:8083

### Docs

A swagger doc is available at http://localhost:8080/swagger-ui.html#/
