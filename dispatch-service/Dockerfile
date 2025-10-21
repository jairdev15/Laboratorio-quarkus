FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package -Dquarkus.package.type=uber-jar

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/*-runner.jar /app/app.jar
EXPOSE 8080
CMD ["java","-jar","/app/app.jar"]
