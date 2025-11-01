FROM openjdk:17-alpine
WORKDIR /app
RUN apk add --no-cache curl
COPY target/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]