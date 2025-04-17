FROM openjdk:11-jre-slim

WORKDIR /app

COPY ./target/neo4j-crud-service-1.0.0.jar /app/neo4j-crud-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "neo4j-crud-service.jar"]
