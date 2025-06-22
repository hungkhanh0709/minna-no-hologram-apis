# SLM Integration TODO List

This document outlines the planned integration of Small Language Models (SLM) for the recommendation system.

## Overview

The recommendation system will use SLM to generate embeddings for content and user profiles, enabling more accurate recommendations based on semantic similarity.

## Current Implementation

The current implementation uses a simple algorithm based on:
- Category matching
- User watch history analysis
- Popularity-based recommendations as fallback

## Future SLM Integration TODOs

### 1. Model Selection and Integration

- [ ] **Select appropriate SLM model**
  - Recommended: `sentence-transformers/all-MiniLM-L6-v2` (80MB, 384-dimensional embeddings)
  - Alternative: `sentence-transformers/all-mpnet-base-v2` (420MB, higher quality)
  - Multi-language: `paraphrase-multilingual-mpnet-base-v2` (supports 50+ languages)

- [ ] **Add model libraries to build.gradle**
  ```gradle
  // SLM dependencies
  implementation 'ai.djl:api:0.25.0'
  implementation 'ai.djl.huggingface:tokenizers:0.25.0'
  implementation 'ai.djl:pytorch-engine:0.25.0'
  implementation 'ai.djl:model-zoo:0.25.0'
  ```

### 2. Embedding Generation

- [ ] **Create domain class for content embeddings**
  ```java
  // Store embeddings for videos and DIYs
  public class ContentEmbedding {
      private String contentId;
      private String contentType; // "video" or "diy"
      private float[] vector;
      private long timestamp;
      
      // Methods for similarity comparison
  }
  ```

- [ ] **Generate meaningful text representations**
  - For videos: combine title, description, and tags
  - For DIYs: combine title, description, materials, and difficulty
  - For users: analyze watch history to create preference profile

- [ ] **Implement embedding caching**
  - Cache embeddings to avoid regeneration
  - Add TTL for refreshing embeddings periodically

### 3. Recommendation Engine Enhancements

- [ ] **Implement content-based recommendation using embeddings**
  - Calculate similarity between current content and other content
  - Return most similar items based on embedding similarity

- [ ] **Implement user profile-based recommendations**
  - Generate embeddings for user preferences
  - Find content with embeddings similar to user profile

- [ ] **Add hybrid recommendation approach**
  - Combine content-based and user-based recommendations
  - Weight factors based on recommendation quality

### 4. Configuration

- [ ] **Add model configuration to application.yml**
  ```yaml
  recommendation:
    model:
      name: "sentence-transformers/all-MiniLM-L6-v2"
      embedding-dimension: 384
      min-similarity-score: 0.5
  ```

- [ ] **Create model-specific properties class**
  - Allow configuration of model parameters
  - Support different model providers

### 5. Performance Optimization

- [ ] **Implement batch processing for embedding generation**
  - Process multiple items at once for efficiency
  - Use parallel streams for large content libraries

- [ ] **Add background processing for model training**
  - Schedule periodic model updates
  - Process new content asynchronously

### 6. Monitoring and Evaluation

- [ ] **Add metrics for recommendation quality**
  - Track recommendation acceptance rate
  - Measure similarity distribution

- [ ] **Implement A/B testing framework**
  - Compare SLM-based recommendations vs. simple algorithm
  - Collect user feedback on recommendation quality

## Implementation Steps

1. Start with a simplified mock implementation
2. Add embedding generation for content 
3. Implement similarity-based recommendations
4. Add user profile embeddings
5. Optimize and fine-tune the system

## Free SLM Models

| Model | Size | Dimensions | Provider | Notes |
|-------|------|------------|----------|-------|
| all-MiniLM-L6-v2 | 80MB | 384 | Hugging Face | Good balance of size and quality |
| all-mpnet-base-v2 | 420MB | 768 | Hugging Face | Higher quality, larger size |
| paraphrase-multilingual-mpnet-base-v2 | 970MB | 768 | Hugging Face | Multi-language support |
