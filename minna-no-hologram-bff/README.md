# Minna No Hologram BFF API

This project is the Backend For Frontend (BFF) API for the Minna No Hologram platform. It aggregates and delivers content to the frontend web application, combining data from video storage, CMS, cache, and the recommender API.

## Features
- RESTful API for videos, DIY articles, categories, search, and likes
- Aggregates data from multiple backend services
- API documentation available in `docs/bff-swagger.json` and online (see below)

## Project Structure
- `src/main/java/com/betonamura/hologram/` - Main application code
  - `controller/` - REST controllers for API endpoints
  - `service/` - Business logic and integration with backend services
  - `domain/` - Domain models and DTOs (see OpenAPI spec for details)
  - `repository/` - Data access and integration
  - `config/` - Spring and application configuration
- `src/main/resources/` - Application configuration (YAML), static resources
- `docs/` - API documentation and overview

## Getting Started

### Prerequisites
- Java 17+
- Gradle 8+

### Running the Application

You can run the application using Gradle. If you have the Gradle wrapper (recommended), use:

```sh
./gradlew bootRun
```

Or, if you have Gradle installed globally, you can use:

```sh
gradle bootRun
```

The API will be available at `http://localhost:8080` by default.

### Running Tests and Viewing Code Coverage

To run tests and generate a Jacoco code coverage report:

```sh
gradle test jacocoTestReport
```

The HTML report will be available at `build/reports/jacoco/test/html/index.html`.

### API Documentation
- See [`docs/bff-swagger.json`](docs/bff-swagger.json) (OpenAPI 3.0)
- See [`docs/overview.md`](docs/overview.md) for a high-level description
- Online API documentation: [https://github.com/hungkhanh0709/minna-no-hologram-docs](https://github.com/hungkhanh0709/minna-no-hologram-docs)

## License

This project is licensed under the Apache License 2.0.
