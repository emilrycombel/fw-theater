# FW-Theater: System Design Challenge


## Detailed Document
[Download Confluence PDF Export - describes the architecture, decisions that have been made and potential changes in future](./docs/Solution Document.pdf)

## Overview
This repository contains the implementation for Fourtwall's system design challenge. The project demonstrates architecture, coding, and project organization skills, focusing on scalability and delivering the minimum viable product (MVP).

## Features
- **Cinema Owner Features**:
  - Update showtimes and prices for movie catalogs.
- **Moviegoer Features**:
  - Fetch movie showtimes.
  - Fetch detailed movie information (e.g., name, description, release date, rating, IMDb rating).
  - Submit 1–5 star reviews with comments (includes profanity filtering).
- **Additional Features**:
  - OpenAPI documentation (Swagger).
  - Circuit breaker for OMDb API integration.

## Architecture
- **Current Setup**: Pseudo-hexagonal architecture to isolate domain logic and prepare for future modularization.
- **Hosting**: On-premise for cost-efficiency, cloud-ready.
- **Persistence**: PostgreSQL database for ACID compliance and scalability.
- **Tech Stack**:
  - Spring Boot 3.4
  - Kotlin 1.9.25 (JDK 21)
  - Hibernate 6.4
  - Liquibase 4.24.0
  - Micrometer (Prometheus integration)
  - Resilience4j (Circuit Breaker)

## Application Endpoints
### Public API
Accessible to all users.
```
/public/api/{versionNumber}
```
### Internal API
Secured with JWT.
```
/api/
```
**Default credentials**:
- Username: `cinemaadmin`
- Password: `password`

## Getting Started
### Prerequisites
- Docker installed on your machine.
- PostgreSQL (optional, if running without Docker).

### Run the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/emilrycombel/fw-theater.git
   ```
2. Navigate to the project directory:
   ```bash
   cd fw-theater
   ```
3. Start the application using Docker:
   ```bash
   docker-compose up
   ```

### API Documentation
Access the OpenAPI documentation at:
```
http://localhost:8080/swagger-ui/index.html
```

## Testing
- **Unit Tests**: Comprehensive tests for domain logic.
- **Integration Tests**: Using Testcontainers for PostgreSQL.
- **Endpoint Tests**: Validating API responses with test accounts.

## Future Enhancements
- Modularize components for scalability.
- Implement proper CI/CD pipelines.
- Improve caching (e.g., OMDb API results).
- Introduce advanced user management with authentication and SSO.
- Transition to a distributed architecture as business needs evolve.


## License
This project is licensed under the Apache-2.0 license. See the LICENSE file for details.

---
