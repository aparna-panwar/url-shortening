# Stage 1: Build the application
FROM gradle:8.3-jdk17 AS builder
WORKDIR /app
COPY . .

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

