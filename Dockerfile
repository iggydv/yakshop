FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/yakshop-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
USER 1000
ENTRYPOINT ["java","-jar","/app.jar"]