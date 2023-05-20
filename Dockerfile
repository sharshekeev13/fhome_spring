FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/fhome-0.0.1-SNAPSHOT.jar fhome.jar
ENTRYPOINT ["java","-jar","fhome.jar"]
EXPOSE 8080