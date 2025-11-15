-- liquibase formatted sql

-- changeset timur:1
CREATE TABLE IF NOT EXISTS city(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);