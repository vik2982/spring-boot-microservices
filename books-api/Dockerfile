FROM openjdk:25-jdk
ENV APP_FILE books-api-0.0.1-SNAPSHOT.jar
ENV APP_HOME /usr/apps
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["java", "-jar", "./books-api-0.0.1-SNAPSHOT.jar"]
