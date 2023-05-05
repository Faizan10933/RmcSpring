FROM openjdk:20
LABEL maintainer="faizan"
COPY target/rmcspring-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]