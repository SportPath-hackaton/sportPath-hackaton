--liquibase formatted sql
--changeset your-name:002-alter-user-id

ALTER TABLE entry
ALTER COLUMN user_id TYPE VARCHAR(36);