-- Create proper schema

CREATE SCHEMA IF NOT EXISTS TRANSFER_APP;
SET SCHEMA TRANSFER_APP;

-- DDLs

DROP TABLE IF EXISTS todo;

CREATE TABLE test
(
  `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
  `business_id` UUID DEFAULT RANDOM_UUID(),
);

-- DMLs

-- TODO: add some test data