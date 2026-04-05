FROM gradle:8.7-jdk17 AS builder
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY settings.gradle .
COPY build.gradle .

COPY common ./common
COPY hub-service ./hub-service
COPY hubpath-service ./hubpath-service

RUN chmod +x ./gradlew
RUN ./gradlew :hubpath-service:clean :hubpath-service:build -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/hubpath-service/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]