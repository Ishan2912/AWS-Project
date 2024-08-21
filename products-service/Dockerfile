FROM openjdk:17-alpine
# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY build/libs/products-service-0.0.1-SNAPSHOT.jar /app/products-service.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/products-service.jar"]