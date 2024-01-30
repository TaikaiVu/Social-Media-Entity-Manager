# ======================
# Build Stage
# ======================
FROM gradle:8.3-jdk17 AS builder

# Copy the application files from the host machine to the image filesystem
COPY --chown=gradle:gradle . /home/gradle/src

# Set the directory within the image for executing future commands
WORKDIR /home/gradle/src

# Run the Gradle build within the container (exclude tests)
RUN gradle build -x test

# ======================
# Packet Stage
# ======================
FROM openjdk:17

WORKDIR /app

# Copy the JAR file built by Gradle to the app folder
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/user-entity.jar

#EXPOSE 8081  # Adjust the port as needed for the user-service

CMD ["java", "-jar", "user-entity.jar"]
