# Design Patterns implemented with Spring Boot Microservices

This project demonstrates the following microservice patterns:
* **External config** - Using spring cloud config
* **Fault tolerance** - Use of resilience4j for retry logic on rest client calls

The following design patterns are also present in the project:
* **AOP** - Exception handling on both the rest client and server side
* **DAO** - Using spring data jpa
* **DI/Singleton** - Autowiring and component annotations
* **Chain of responsibility pattern** - Use of filters in Spring security

## Prerequisites

* JDK 24
* Maven Latest version
* Latest Docker installation

## How to Run

### Standalone
**Start the mock server**  *- the mock server allows us to demonstrate rest client side logic within our books-api microservice.  In addition the mock server returns a error response so that we can test resilience4j retry logic*:
```
docker-compose up -d
```
**Start the config server**  *- the client microservice (books-api) config is stored in a h2 database.  The config consists of the connection properties for the mock api and the resilience4j config*:
```
cd ../spring-cloud-config-server
mvn spring-boot:run
```
**Start the books-api microservice**  *- on startup it saves books to a h2 database.  The restcontroller exposes methods to access the data from the database.  In addition there is rest client side logic within the microservice to interact with a mock server*:
```
cd ../books-api
mvn spring-boot:run -Dspring-boot.run.profiles=local -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" 
```

<br/>Alternatively for the spring boot microservices you can do a build with maven jar plugin
```
cd spring-cloud-config-server
mvn clean package 
java -jar target/spring-cloud-config-server-1.0.jar

cd ../books-api
mvn clean package 
java -jar -Dspring.profiles.active=local -Dserver.port=8080 target/books-api-1.0.jar
```

### Docker
Rather than running each application standalone we can leverage docker to start all services
```
cd spring-boot-microservices
mvn clean package

cd spring-cloud-config-server
docker build -t spring-cloud-config-server:1.0 .

cd ../books-api
docker build -t books-api:1.0 .

cd ..
docker-compose up -d 

# docker-compose down - stop and remove containers
```

### URLS

#### Test database logic
* Get book by id - http://localhost:8080/books/{id}
* Filter books by title - http://localhost:8080/books/filter?title=LOTR
* Sort books by price: http://localhost:8080/books/sort?sort_by=price&order_by=DESC
* Create a book - Open git bash command prompt and run following curl
```
curl -X POST -H "Content-Type: application/json" -d '{
  "title": "New Book",
  "author": "Vikrant Ardhawa",
  "yearPublished": 2025,
  "pages": 400,
  "price": 39.99
}' 'http://localhost:8080/books'
```

#### Test mock server logic
* Get book by id from mock server - http://localhost:8080/books/mock-api/4
* Get 500 error from mock server (no retry) - http://localhost:8080/books/mock-api/4?error=true
* Get 500 error from mock server (retry logic) - http://localhost:8080/books/mock-api/4?error=true&retry=true
* Get 400 error from mock server (retry predicate logic) -   http://localhost:8080/books/mock-api/4?error=true&retry=true&badRequest=true

### H2

#### spring-cloud-config-server
* Console url: http://localhost:8888/h2-console/  
* Database url: jdbc:h2:mem:testdb  
* username: sa  
* password is empty
<br/>

Properties for profiles can also be seen at following urls:<br/>
http://localhost:8888/books-api/local <br/>
http://localhost:8888/books-api/dev

#### books-api
* Console url: http://localhost:8080/books/h2-console/  
* Database url: jdbc:h2:mem:testdb
* username: sa
* password is empty

### Actuator
http://localhost:8080/actuator/env