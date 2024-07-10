# This image contains the JDK required to build the Spring Boot application
FROM openjdk:17-jdk-alpine as build


# This creates and sets the /app directory as the working directory
WORKDIR /app

# Copy the Gradle wrapper and build.gradle files to the container to download dependencies
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew
# This step uses the Gradle wrapper to download dependencies and cache them
RUN ./gradlew build -x test --no-daemon

# Copy the source code to the working directory
COPY src src

# Run the Gradle build command to compile the application and create a JAR file, skipping tests for faster builds
RUN ./gradlew bootJar -x test --no-daemon

# This stage uses a minimal JDK image to run the application, reducing the final image size
FROM openjdk:17-jdk-alpine

# Create and set the /app directory as the working directory for the runtime stage
WORKDIR /app

# Copy the JAR file from the /app/build/libs directory of the build stage to the current directory
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080, which is the default port for Spring Boot applications
EXPOSE 8080

# Specify the command to run the Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
