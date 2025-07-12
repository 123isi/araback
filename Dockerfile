FROM gradle:8.4-jdk21 AS builder
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon

FROM openjdk:21-jdk-slim
COPY wait-for-it.sh /wait-for-it.sh
COPY --from=builder /app/build/libs/*.jar /app.jar
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "dpg-d1p83qvfte5s73c2qebg-a:5432", "--timeout=60", "--strict", "--", "java", "-jar", "/app.jar"]
