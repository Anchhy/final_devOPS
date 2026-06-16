#!/bin/bash

# Start SSH service
service ssh start

# Start NGINX
service nginx start

# Find and run the Spring Boot JAR file
JAR_FILE=$(find /app/target -name "*.jar" | grep -v plain | head -n 1)
if [ -z "$JAR_FILE" ]; then
    echo "No JAR file found in /app/target. Did the build succeed?"
    exit 1
fi

echo "Starting Spring Boot application: $JAR_FILE"
java -jar "$JAR_FILE"
