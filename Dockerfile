FROM openjdk:8-jdk-alpine

COPY target/sboot-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]