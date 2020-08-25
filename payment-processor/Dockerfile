FROM openjdk:8-jdk-alpine
FROM maven:alpine

WORKDIR /app
ADD pom.xml /app

RUN mvn clean package

EXPOSE 8081
ADD ./target/payment-0.0.1-SNAPSHOT.jar payment.jar
ENTRYPOINT ["java", "-jar", "payment.jar"]
