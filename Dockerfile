# Base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Maintainer Contact
LABEL maintainer="amin.benbelkacem@gmail.com"

# Application's jar file
ARG JAR_FILE=target/dataprovider-0.0.1-SNAPSHOT.jar

# Add the jar
ADD ${JAR_FILE} dataprovider.jar

# Run Application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/dataprovider.jar"]
