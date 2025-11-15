-- liquibase formatted sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- changeset timur:2
CREATE TABLE IF NOT EXISTS court_info (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL,
    address VARCHAR(500) NOT NULL,
    rating DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    type VARCHAR(50) NOT NULL,
    photo_url VARCHAR(500),
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    description TEXT,
    title VARCHAR(255) NOT NULL,
    city_id BIGINT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE
);

-- changeset timur:3
CREATE INDEX idx_court_info_city_id ON court_info(city_id);