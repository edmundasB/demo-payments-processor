FROM openjdk:8-jdk-alpine
FROM maven:alpine

WORKDIR /app
ADD pom.xml /app

RUN mvn clean install

EXPOSE 8081
ADD ./target/demo-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]
