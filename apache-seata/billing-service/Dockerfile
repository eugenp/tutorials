FROM maven:3.9.13-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .
RUN mvn compile

COPY . .
RUN mvn clean package -DskipTests



FROM eclipse-temurin:17

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
