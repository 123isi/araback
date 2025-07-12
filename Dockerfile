FROM openjdk:21-jdk-slim

COPY wait-for-it.sh /wait-for-it.sh
COPY ./build/libs/*.jar app.jar

RUN chmod +x /wait-for-it.sh

EXPOSE 8081
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--timeout=60", "--strict", "--", "java", "-jar", "/app.jar"]