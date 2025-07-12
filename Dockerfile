FROM openjdk:21-jdk-slim

COPY wait-for-it.sh /wait-for-it.sh
COPY ./build/libs/muldum-0.0.1-SNAPSHOT.jar /app.jar

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["/wait-for-it.sh", "dpg-d1p83qvfte5s73c2qebg-a:5432", "--timeout=60", "--strict", "--", "java", "-jar", "/app.jar"]
