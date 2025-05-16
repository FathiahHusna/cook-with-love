# Docker build stage
FROM maven:3-openjdk-17 AS build

# Build stage
WORKDIR /opt/app

COPY ./ /opt/app
RUN mvn clean install

# Docker build stage
FROM openjdk:17-jdk

COPY --from=build /opt/app/target/*.jar app.jar

ENV PORT 8081
EXPOSE $PORT

ENTRYPOINT ["java","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]