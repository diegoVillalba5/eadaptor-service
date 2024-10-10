FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

# Copy the pom.xml first to leverage Docker layer caching more effectively
COPY pom.xml .

# Run a dependency resolution step to cache dependencies without building the project
RUN mvn dependency:go-offline

COPY src src

RUN mvn clean package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

LABEL maintainer="dvillalba@logtech.global"

EXPOSE 443

CMD ["java", "-jar", "app.jar"]