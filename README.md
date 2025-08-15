# Microservice Patterns implemented with Spring Boot

This project demonstrates the following microservice patterns:
* External config - Using spring cloud config
* Fault tolerance - Use of resilience4j for retry logic on rest client calls

The following design patterns are also present in the project:
* AOP - Exception handling on both the rest client and server side
* DAO - Using spring data jpa
* DI/Singleton - Autowiring and component annotations
* Chain of responsibility pattern - Use of filters in Spring security

## Prerequisites

* JDK 24
* Maven Latest version
* Latest Docker installation

## How to Run

### Standalone
*Start the mock server*:
```
docker-compose up -d
```
*Start the config server*:
```
cd ../spring-cloud-config-server
mvn spring-boot:run
```
*Start the api*:
```
cd ../books-api
mvn spring-boot:run -Dspring-boot.run.profiles=local -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" 
```
Alternatively for the spring boot microservices you can do a build with maven jar plugin
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
* Get book by id from mock api - http://localhost:8080/books/{id}?mock-api=true
* Get error from mock api - http://localhost:8080/books/{id}?mock-api=true&error=true


### H2

#### spring-cloud-config-server
* Console url: http://localhost:8888/h2-console/  
* Database url: jdbc:h2:mem:testdb  
* username: sa  
* password is empty
<br/>

Properties for profiles can also be seen at following urls:
http://localhost:8888/books-api/local <br/>
http://localhost:8888/books-api/dev

#### books-api
* Console url: http://localhost:8080/books/h2-console/  
* Database url: jdbc:h2:mem:testdb
* username: sa
* password is empty

### Database Config


### Actuator
http://localhost:8080/actuator/env