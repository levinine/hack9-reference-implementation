FROM openjdk:11.0-jre-slim
MAINTAINER Nikola Milutinovic <n.milutinovic@levi9.com>
LABEL company="Levi Nine"
LABEL event="Hack9"

ARG jar_file="target/backend-0.0.1-SNAPSHOT.jar"

RUN useradd hack9 && \
    mkdir -p /var/lib/hack9 && \
    chown hack9 /var/lib/hack9
    
USER hack9
COPY ${jar_file} /var/lib/hack9/hack9-ri.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/var/lib/hack9/hack9-ri.jar"]
CMD ["--server.port=8080", "--hack9.configuration.csv=classpath:Codes.csv"]