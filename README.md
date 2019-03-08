
## Issue

This repository presents a program that provides ...


### Running

`$ java -jar deploy/standalone.jar`

`$ java -jar deploy/standalone.jar --server.port=10000`


### Technology

This solution makes use of a technology stack comprised of:
- Maven
- Spring (Boot, Web, JPA)
- Liquibase
- Vue
- PostgreSQL


### Build

`$ mvn clean install -DskipTests`

`$ mvn clean install -Dmaven.test.skip=true`

Alternatively, you could use maven wrapper:

`$ ./mvnw clean install` (for Unix systems)

`$ ./mvnw.cmd clean install` (for Batch)
