# Neo4j CRUD Microservice

## Purpose
This microservice provides a RESTful API for performing CRUD operations on a Neo4j graph database. It allows for the management of nodes and relationships in a generic way, making it suitable for any graph-based data model.

## Features
- CRUD operations for nodes with arbitrary labels and properties
- CRUD operations for relationships between nodes
- RESTful API with OpenAPI documentation
- Docker and Docker Compose setup for easy deployment
- Based on Hexagonal Architecture and Domain-Driven Design principles

## Technology Stack
- Java 11
- Spring Boot 2.7.5
- Neo4j (via official Java driver)
- H2 Database (for operation logs)
- Docker & Docker Compose
- OpenAPI/Swagger for API documentation

## Architecture
The project follows Hexagonal Architecture and Domain-Driven Design principles:

- **Domain Layer**: Contains the core business entities and repository interfaces
- **Infrastructure Layer**: Implements the repository interfaces using Neo4j driver
- **Application Layer**: Contains services that coordinate domain operations
- **API Layer**: Exposes RESTful endpoints and handles HTTP requests/responses

## How to Run

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (for containerized deployment)

### Running with Docker Compose
To run the service with a Neo4j instance, use the provided Docker Compose file:

```bash
docker-compose up






exit
