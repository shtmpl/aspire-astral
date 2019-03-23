## Program

The program provides access to a list of vacancies fetched from a remote resource ([HeadHunter API](https://dev.hh.ru/)).

The program supports importing functionality 
that allows vacancies found on a remote resource to be persisted into a local store (PostgreSQL database).


### Technology

The program makes use of a technology stack composed of:
- Maven
- Spring (Boot, Web, JPA)
- Liquibase
- PostgreSQL
- Vue (for the frontend prototype)


## Operation

### Prerequisites

PostgreSQL (9.5.14) with a database created for the program is required.

**Note**: Database schema and data required for program operation shall be created by the Liquibase.


### Building

`$ mvn clean install`

Alternatively, you could use maven wrapper:

`$ ./mvnw clean install` (for Unix systems)

`$ ./mvnw.cmd clean install` (for Batch)

This shall assemble an executable .jar file under the `core/target` directory.


### Running

`$ java -jar core/target/core-0.0.1-SNAPSHOT.jar`

`$ java -jar core/target/core-0.0.1-SNAPSHOT.jar --server.port=10000`

This will fire up a Web server at `http://localhost:8080` (or whatever port you'd specified).

The documentation for the REST API exposed by the application could be found at:

`http://localhost:8080/swagger-ui.html` (if you prefer an HTML representation)

`http://localhost:8080/v2/api-docs` (if you could really make use of all that JSON)


### Running (Alternative)

Alternatively, you could fire up backend and frontend separately (for development purposes, for example):

#### Bringing the backend up:

(Assuming you're in the project base directory)

`$ cd core`

`$ mvn spring-boot:run`

This will fire up a backend server at `http://localhost:8080`.

#### Bringing the frontend up:

(Assuming you're in the project base directory)

`$ cd front`

`$ npm run serve` (You have to have `npm` installed in your system)

This will fire up a frontend Web server at `http://localhost:3142` 
with all the requests executed against the `/api` path proxied to `http://localhost:8080`.