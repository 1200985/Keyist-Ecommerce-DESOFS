FROM maven:3.6-adoptopenjdk-11 AS build

WORKDIR /build

COPY pom.xml .
COPY src src

RUN mvn package -DskipTests

FROM openjdk:11-jre-slim
WORKDIR /app

COPY --from=build /build/target/*.jar resource_server.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "resource_server.jar"]