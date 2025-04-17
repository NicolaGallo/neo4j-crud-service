# Neo4j CRUD Microservice

## Purpose
This microservice provides a RESTful API for performing CRUD operations on a Neo4j graph database. It allows for the management of nodes and relationships in a generic way, making it suitable for any graph-based data model.

## Features
- CRUD operations for nodes with arbitrary labels and properties
- CRUD operations for relationships between nodes
- RESTful API with OpenAPI documentation
- Configured for multiple environments (local and production)
- Docker and Docker Compose setup for easy deployment
- Based on Hexagonal Architecture and Domain-Driven Design principles

## Technology Stack
- Java 11
- Spring Boot 2.7.5
- Spring Data Neo4j
- H2 Database (for operation logs)
- Docker & Docker Compose
- OpenAPI/Swagger for API documentation

## Architecture
The project follows Hexagonal Architecture and Domain-Driven Design principles:

- **Domain Layer**: Contains the core business entities and repository interfaces
- **Infrastructure Layer**: Implements the repository interfaces using Spring Data Neo4j
- **Application Layer**: Contains services that coordinate domain operations
- **API Layer**: Exposes RESTful endpoints and handles HTTP requests/responses

## How to Run

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

### Running in Local Environment

1. Create a local environment file:
   ```bash
   cp .env.example .env.local
   ```

2. Edit `.env.local` to set your preferred configuration.

3. Run the local environment using the provided script:
   ```bash
   chmod +x start-local.sh
   ./start-local.sh
   ```

4. The services will be available at:
   - Neo4j Browser: http://localhost:7474 (default port)
   - API: http://localhost:8080 (default port)

### Running in Production Environment

1. Create a production environment file:
   ```bash
   cp .env.example .env.prod
   ```

2. Edit `.env.prod` to set your production configuration.

3. Run the production environment using the provided script:
   ```bash
   chmod +x start-prod.sh
   ./start-prod.sh
   ```

### Building the Application

To build the application:

```bash
mvn clean package
```

The built JAR file will be in the `target` directory.

### API Documentation

Once the application is running, you can access the OpenAPI documentation at:

```
http://localhost:8080/swagger-ui.html
```

## Configuration

The application can be configured using the following methods:

1. **Environment Files**: Use `.env.local` and `.env.prod` for Docker environment variables
2. **Spring Profiles**: The application uses Spring profiles `local` and `prod`
3. **Environment Variables**: You can override any configuration by setting appropriate environment variables

## Security

**IMPORTANT**: The example configuration files contain default passwords. For production use:

1. Change all default passwords
2. Never commit sensitive credentials to source control
3. Consider using a secrets management solution in production

## License

This project is licensed under the Apache License 2.0.