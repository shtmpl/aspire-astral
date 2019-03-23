## Overview

The program provides access to a list of vacancies fetched from a remote resource ([HeadHunter API](https://dev.hh.ru/)).

The program supports importing functionality that allows vacancies found on a remote resource to be persisted into a local store (PostgreSQL database).


### Technology

This solution makes use of a technology stack comprised of:
- Maven
- Spring (Boot, Web, JPA)
- Liquibase
- PostgreSQL
- Vue (for the frontend prototype)


### Prerequisites

PostgreSQL (9.5.14) with a database created for the program is required.
(Database schema and data required for program operations shall be created by Liquibase.)


### Build

`$ mvn clean install`

Alternatively, you could use maven wrapper:

`$ ./mvnw clean install` (for Unix systems)

`$ ./mvnw.cmd clean install` (for Batch)


### Running

`$ java -jar target/standalone.jar`

`$ java -jar deploy/standalone.jar --server.port=10000`

REST API documentation could be found at `http://localhost:8080/swagger-ui.html`


`$ cd core`

`$ mvn spring-boot:run`


`$ cd front`

`$ npm run serve`
