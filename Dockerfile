FROM amazoncorretto:17-alpine
COPY target/mendel-challenge-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
