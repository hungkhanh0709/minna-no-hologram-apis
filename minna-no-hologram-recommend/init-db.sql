-- Initialize database for Minna No Hologram Recommend API
-- Run this script with: psql -U postgres -d hologram_recommend -f init-db.sql

-- Drop existing tables if they exist
DROP TABLE IF EXISTS user_interactions;
DROP TABLE IF EXISTS user_profiles;
DROP TABLE IF EXISTS videos;

-- Create videos table
CREATE TABLE videos (
    id SERIAL PRIMARY KEY,
    video_id VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    thumbnail_url VARCHAR(255),
    category_id VARCHAR(50) NOT NULL,
    tags VARCHAR(255),
    view_count INTEGER DEFAULT 0,
    like_count INTEGER DEFAULT 0,
    embedding BYTEA,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create user_profiles table
CREATE TABLE user_profiles (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL UNIQUE,
    preferred_categories VARCHAR(255),
    preferred_tags VARCHAR(255),
    embedding BYTEA,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create user_interactions table
CREATE TABLE user_interactions (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    video_id VARCHAR(255) NOT NULL,
    action VARCHAR(50) NOT NULL, -- WATCH, LIKE, SHARE, FULL_VIEW, SKIP
    watch_time INTEGER, -- in seconds
    interaction_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP
);

-- Create indices for faster queries
CREATE INDEX idx_videos_video_id ON videos (video_id);
CREATE INDEX idx_videos_category_id ON videos (category_id);
CREATE INDEX idx_user_profiles_user_id ON user_profiles (user_id);
CREATE INDEX idx_user_interactions_user_id ON user_interactions (user_id);
CREATE INDEX idx_user_interactions_video_id ON user_interactions (video_id);
CREATE INDEX idx_user_interactions_action ON user_interactions (action);
