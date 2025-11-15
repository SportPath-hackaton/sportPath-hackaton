-- liquibase formatted sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- changeset timur:4
CREATE TABLE IF NOT EXISTS courts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL,
    court_type VARCHAR(50) NOT NULL,
    city_id BIGINT NOT NULL,
    court_info_id UUID NOT NULL UNIQUE,
    FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE,
    FOREIGN KEY (court_info_id) REFERENCES court_info(id) ON DELETE CASCADE
);

-- changeset timur:5
CREATE INDEX idx_courts_city_id ON courts(city_id);
CREATE INDEX idx_courts_court_info_id ON courts(court_info_id);