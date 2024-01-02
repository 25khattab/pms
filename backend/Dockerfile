FROM eclipse-temurin:17-jdk-alpine

VOLUME ["/app/testing"]

WORKDIR /backend

COPY . /backend

EXPOSE 8080

CMD ["./mvnw","spring-boot:run"]