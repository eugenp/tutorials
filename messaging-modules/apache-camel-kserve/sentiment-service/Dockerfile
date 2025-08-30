FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the fat JAR from the builder stage
COPY target/sentiment-service-1.0-SNAPSHOT.jar app.jar

# Expose HTTP port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
