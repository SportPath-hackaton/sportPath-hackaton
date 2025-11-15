FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="Kirill"

WORKDIR /app

# Копируем JAR файл
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]