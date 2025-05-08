# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/BotFurioso-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]

