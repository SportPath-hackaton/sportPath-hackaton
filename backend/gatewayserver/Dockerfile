FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="Kirill"

WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]