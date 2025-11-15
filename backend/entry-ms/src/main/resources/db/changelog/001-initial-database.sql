-- liquibase formatted sql

-- changeset kiri4:1
CREATE TABLE entry (
    id UUID PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    court_id UUID NOT NULL,
    entry_time TIMESTAMP WITH TIME ZONE NOT NULL
);

-- changeset kiri4:2
CREATE INDEX idx_entry_user_id ON entry(user_id);
CREATE INDEX idx_entry_court_id ON entry(court_id);
CREATE INDEX idx_entry_time ON entry(entry_time);
CREATE INDEX idx_user_entry_time ON entry(user_id, entry_time);
