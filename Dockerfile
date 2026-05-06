# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Install curl (needed by Maven/Gradle wrappers)
RUN apk add --no-cache curl

# Maven build (default)
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

# Gradle build (alternative)
# Keeping this here makes it easy to switch the Docker build to Gradle if desired.
# To use it, comment out the Maven block above and uncomment these lines.
#
# COPY gradlew settings.gradle.kts build.gradle.kts ./
# COPY gradle gradle
# RUN chmod +x gradlew && ./gradlew --no-daemon dependencies
# COPY src src
# RUN ./gradlew --no-daemon build

# Run stage (JVM)
FROM eclipse-temurin:21-jre-alpine AS jvm
WORKDIR /app
COPY --from=build /app/target/micronaut-petclinic-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
