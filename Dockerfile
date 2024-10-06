FROM openjdk:17-alpine

WORKDIR /app

COPY target/simulation-world-2d-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]