FROM openjdk:17-jdk-slim

WORKDIR /app

COPY tenants-manage-java/target/app.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]