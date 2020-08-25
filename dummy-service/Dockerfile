FROM openjdk:8-jdk-alpine
FROM maven:alpine

WORKDIR /app
ADD pom.xml /app

RUN mvn clean package

EXPOSE 8082
ADD ./target/dummy-0.0.1-SNAPSHOT.jar dummy.jar
ENTRYPOINT ["java", "-jar", "dummy.jar"]
