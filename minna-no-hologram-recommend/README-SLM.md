# SLM-based Recommendation System

This module implements a recommendation system using Small Language Models (SLM) to suggest content to users. The system is designed with the following features:

## Features

- Content-based recommendations using SLM for embedding generation
- User history based recommendations using SLM
- Popular content fallback when user has no history
- Embedding caching for performance optimization
- Support for different model providers (Hugging Face, OpenAI, mock)
- Scheduled retraining and embedding updates
- Admin API for monitoring model status

## Model Architecture

The recommendation system uses embedding-based semantic search:

1. **Content Embeddings**: Generate vector embeddings for videos, DIY content
2. **User Profile Embeddings**: Create embeddings based on user watch history
3. **Similarity Matching**: Find content similar to what the user likes using cosine similarity
4. **Ranking**: Sort by similarity score to present most relevant content first

## Free Model Options

The system is designed to work with various free SLM options:

| Model | Size | Dimensions | Provider | Notes |
|-------|------|------------|----------|-------|
| all-MiniLM-L6-v2 | 80MB | 384 | Hugging Face | Good balance of performance and size |
| all-mpnet-base-v2 | 420MB | 768 | Hugging Face | Higher quality, larger size |
| Nomic Embed | <100MB | 768 | Hugging Face | General purpose embeddings |
| paraphrase-multilingual-mpnet-base-v2 | 970MB | 768 | Hugging Face | Multi-language support |

The default configuration uses `sentence-transformers/all-MiniLM-L6-v2` which is a good balance of size and quality.

## Configuration

Key configuration parameters in `application.yml`:

```yaml
recommendation:
  model:
    name: "sentence-transformers/all-MiniLM-L6-v2"
    provider: "huggingface"  # Options: huggingface, openai, tensorflow, mock
    embedding-dimension: 384
```

## Implementation TODOs

The following items are marked as TODOs for future implementation:

1. **Model Loading**: Implement actual HuggingFace model loading in `HuggingFaceModelService`
2. **DIY Recommendations**: Enhance DIY recommendation logic using embeddings
3. **Database Storage**: Add database storage for embeddings instead of in-memory caching
4. **Custom Model Training**: Add support for fine-tuning models on our specific content
5. **Performance Optimization**: Optimize embedding generation and similarity calculations
6. **Batch Processing**: Add batch processing for efficient embedding generation

## Development

To work on this project:

1. Ensure all dependencies are installed (see `build.gradle`)
2. For local development, you can use the "mock" provider
3. For real model usage, download a pre-trained model like `all-MiniLM-L6-v2`
4. Run the application with `./gradlew bootRun`

## API Endpoints

The recommendation API provides the following endpoints:

- `GET /api/v1/recommendations` - Get personalized recommendations
- `GET /api/v1/recommendations/popular` - Get popular content
- `POST /api/v1/feedback` - Record user interaction feedback
- `GET /api/admin/model/info` - Get model information
- `GET /api/admin/model/refresh` - Trigger model embedding refresh
