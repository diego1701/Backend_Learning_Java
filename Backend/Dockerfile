FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Learning_Java-1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
