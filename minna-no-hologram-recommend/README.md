# Minna No Hologram - Recommender API

## Overview
This service provides personalized video and DIY recommendations for the Minna No Hologram platform. The Recommender API leverages a Small Language Model (SLM) to analyze user watching history, video metadata, and current context to suggest the most relevant content to users.

## Code Structure
The codebase follows the standard structure aligned with the BFF API:

- **domain**: Core domain objects organized by subdomain (video, diy, user, tag, common)
- **controller**: REST API endpoints and request/response objects
- **service**: Business logic implementations
- **repository**: Data access interfaces (for future database integration)
- **data**: In-memory data providers

## Key Features
- **Personalized Recommendations**: Based on user watching history and preferences.
- **Contextual Recommendations**: Suggests similar videos based on current video being watched.
- **Small Language Model Integration**: Uses a lightweight language model for advanced recommendation logic.
- **Feedback Loop**: Collects user interaction data to continuously improve recommendations.

## Technology Stack
- **Framework**: Spring Boot
- **Database**: Postgresql (for user history and video metadata)
- **Machine Learning**: Small Language Model implemented with Hugging Face's Transformers
- **API Documentation**: OpenAPI/Swagger

## API Endpoints

### Get Recommendations
```
GET /api/v1/recommendations
```
Parameters:
- `userId` (required): ID of the user
- `videoId` (optional): ID of the current video being watched
- `limit` (optional, default=10): Number of recommendations to return
- `filter` (optional): Filter by category, tag, etc.

The API returns both video and DIY recommendations in the following format:
```json
{
  "success": true,
  "data": {
    "userId": "user-123",
    "currentContentId": "video-456",
    "relatedVideos": [
      {
        "id": "video-789",
        "slug": "video-789",
        "title": "How to Create a Hologram",
        "thumbnail": "https://example.com/thumb.jpg",
        "category": "education",
        "tags": [
          {
            "id": "hologram",
            "name": "Hologram"
          }
        ],
        "likeCount": 250,
        "similarityScore": 0.85
      }
    ],
    "relatedDiy": [
      {
        "id": "diy-123",
        "slug": "diy-123",
        "title": "Make Your Own Projector",
        "thumbnail": "https://example.com/thumb.jpg",
        "summary": "Simple DIY projector using smartphone",
        "stepCount": 5,
        "estimatedTime": "30min",
        "difficulty": "Medium",
        "tags": [
          {
            "id": "projector",
            "name": "Projector"
          }
        ],
        "likeCount": 120,
        "similarityScore": 0.75
      }
    ],
    "generatedTimestamp": 1719236700000
  },
  "message": "Success"
}
```

## Recommendation Logic

### Small Language Model (SLM) Approach
The recommender system utilizes a small language model to:
1. Analyze video metadata (titles, descriptions, tags)
2. Learn patterns in user viewing history
3. Generate embeddings for videos and user preferences
4. Match users to videos with similar embedding patterns

### Hybrid Recommendation Strategy
The system combines multiple approaches:
- **Collaborative Filtering**: "Users who watched this also watched..."
- **Content-Based Filtering**: "Videos similar to what you've watched..."
- **Popularity-Based**: "Trending in your region/category..."
- **Language Model Insights**: Advanced semantic understanding of content

## Development

### Prerequisites
- JDK 17+
- Gradle 8.0+
- Postgresql
- Python 3.8+ (for SLM model training)

### Build and Run Locally
```bash
# Build the application
./gradlew clean build

# Run the application
./gradlew bootRun
```

The server will start on port 8081 with context path `/api`.

### Example API Calls

#### Get Recommendations
```bash
# Get recommendations for user-1 based on current video-1
curl -X GET "http://localhost:8081/api/v1/recommendations?userId=user-1&videoId=video-1&limit=5"

# Get general recommendations for user-2
curl -X GET "http://localhost:8081/api/v1/recommendations?userId=user-2&limit=10"
```

#### Send Feedback
```bash
# Record a LIKE interaction
curl -X POST "http://localhost:8081/api/v1/feedback" \
     -H "Content-Type: application/json" \
     -d '{"userId":"user-1","videoId":"video-2","interactionType":"LIKE","timestamp":"2025-06-21T12:00:00"}'

# Record a WATCH interaction with watch time
curl -X POST "http://localhost:8081/api/v1/feedback" \
     -H "Content-Type: application/json" \
     -d '{"userId":"user-2","videoId":"video-5","interactionType":"WATCH","watchTimeSeconds":180,"timestamp":"2025-06-21T12:30:00"}'
```

### Configuration
Key application properties (in `application.yml`):
- Server port and context path
- SLM model configuration
- API rate limits and security settings
- Recommendation parameters

## Testing
```bash
./gradlew test
```

## Deployment
The service is designed to be deployed as a standalone microservice, with optional scaling for the recommendation engine component.

## Integration with Other Services
- Connects to user service for profile information
- Integrates with video metadata service
- Sends analytics data to data pipeline

---

*For more detailed documentation, please refer to the [minna-no-hologram-docs](https://github.com/hungkhanh0709/minna-no-hologram-docs) repository.*
