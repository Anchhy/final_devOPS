FROM ubuntu:24.04

# Avoid tzdata interactive prompts during package installation
ENV DEBIAN_FRONTEND=noninteractive

# Install JDK 25, Git, NGINX, and OpenSSH Server
RUN apt-get update && apt-get install -y \
    openjdk-25-jdk \
    git \
    nginx \
    openssh-server \
    && rm -rf /var/lib/apt/lists/*

# Configure SSH
RUN mkdir -p /var/run/sshd
RUN echo 'root:root' | chpasswd
RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# Configure NGINX
COPY nginx.conf /etc/nginx/sites-available/default

WORKDIR /app

# Clone the repository
RUN git clone https://github.com/Anchhy/final_devOPS.git .

# Since the user might not have pushed the latest application.properties yet,
# we copy the local updated properties file into the cloned repository
COPY src/main/resources/application.properties /app/src/main/resources/application.properties

# Build the project (skip tests to avoid requiring the database at build time)
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Copy startup script
COPY startup.sh /usr/local/bin/startup.sh
RUN chmod +x /usr/local/bin/startup.sh

# Expose required ports
EXPOSE 80 8080 22

# Start services
CMD ["/usr/local/bin/startup.sh"]
