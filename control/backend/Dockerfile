FROM openjdk:11.0-jre-slim
MAINTAINER Nikola Milutinovic <n.milutinovic@levi9.com>
LABEL company="Levi Nine"
LABEL event="Hack9"
LABEL service="Skeleton callback handler"

ARG jar_file="target/control-backend-0.0.1-SNAPSHOT.jar"

RUN useradd hack9 && \
    mkdir -p /var/lib/hack9 && \
    chown hack9 /var/lib/hack9
    
USER hack9
COPY ${jar_file} /var/lib/hack9/hack9-callback.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/var/lib/hack9/hack9-callback.jar"]
