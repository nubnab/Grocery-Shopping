FROM maven:3.9.8-eclipse-temurin-22-alpine AS build

WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-alpine

RUN apk update && apk add --no-cache bash && rm -rf /var/cache/apk/*

COPY --from=build /app/target/*.jar /app/app.jar

COPY wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["/app/wait-for-it.sh", "postgres-container:5432", "--strict", "--timeout=60", "--", "java", "-jar", "/app/app.jar"]
