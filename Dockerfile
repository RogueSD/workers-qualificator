FROM maven:3.8.3-amazoncorretto-17 AS builder
ADD pom.xml pom.xml
ADD ./src src/
RUN mvn clean package

FROM amazoncorretto:17.0.0-alpine
ADD ./src/main/resources/db/migration/* db/migration/
ADD ./src/main/resources/application.yml application-release.yml
COPY --from=builder ./target/project.jar application.jar

EXPOSE 8080

CMD [ "java", "-Xmx1024m", "-Dspring.profiles.active=release", "-jar", "application.jar" ]