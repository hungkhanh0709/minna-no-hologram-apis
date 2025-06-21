# Development Guide for Minna No Hologram Recommend Service

This document provides detailed instructions for developers working on the recommendation API.

## Prerequisites

1. JDK 17 or higher
2. Gradle 8.0+
3. PostgreSQL database
4. Git

## Getting Started

### 1. Clone the Repository

If you haven't already cloned the repository:

```bash
git clone https://github.com/your-repo/minna-no-hologram-apis.git
cd minna-no-hologram-apis/minna-no-hologram-recommend
```

### 2. Set Up the Database

Create a PostgreSQL database:

```bash
# Connect to PostgreSQL
psql -U postgres

# Create the database
CREATE DATABASE hologram_recommend;

# Exit PostgreSQL
\q
```

### 3. Build the Project

```bash
./gradlew build
```

### 4. Run the Application

```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8081/api`.

## API Endpoints

### Get Recommendations

```
GET /api/v1/recommendations
```

Parameters:
- `userId` (required): ID of the user
- `videoId` (optional): ID of the current video being watched
- `categoryId` (optional): Filter by category
- `limit` (optional, default=10): Number of recommendations to return

Returns both video and DIY recommendations based on user preferences and current content.

### Get Popular Videos

```
GET /api/v1/recommendations/popular
```

Parameters:
- `limit` (optional, default=10): Number of recommendations to return

### Record Feedback

```
POST /api/v1/feedback
```

Request Body:
```json
{
  "userId": "string",
  "videoId": "string",
  "interactionType": "WATCH | LIKE | SKIP | FULL_VIEW",
  "watchTimeSeconds": 123,
  "timestamp": "2025-06-21T12:00:00"
}
```

## Development Workflow

### Adding New Features

1. Create a new branch: `git checkout -b feature/your-feature-name`
2. Implement your feature
3. Write tests
4. Run tests: `./gradlew test`
5. Build the project: `./gradlew build`
6. Submit a pull request

### Model Training and Updating

For now, the Small Language Model is mocked, but in a future implementation:

1. Collect user interaction data
2. Prepare training data
3. Train the model
4. Update model embeddings

## Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Ensure PostgreSQL is running
   - Check connection settings in `application.yml`

2. **Java Version Issues**
   - Ensure you're using JDK 17+: `java --version`

3. **Build Failures**
   - Check for compile errors: `./gradlew compileJava`
   - Check test failures: `./gradlew test`
