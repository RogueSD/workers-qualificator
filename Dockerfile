FROM maven:3.5.0-jdk-17 AS builder
ADD pom.xml pom.xml
ADD ./src src/
RUN mvn clean package

FROM amazoncorretto:17-jre-alpine
ADD ./src/main/resources/db/migration/* *
ADD ./src/main/resources/application.yml application.yml
COPY --from=builder ./target/project.jar application.jar

EXPOSE 8080

CMD [ "java", "-Xmx=1024M", "-Dspring.profiles.active=default", "-jar", "application.jar" ]