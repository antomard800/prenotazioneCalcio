FROM maven:3-adoptopenjdk-11 AS MAVEN_BUILD
MAINTAINER Antonio Marco DAddetta
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -Dmaven.test.skip=true

FROM amazoncorretto:11
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker","app.jar"]