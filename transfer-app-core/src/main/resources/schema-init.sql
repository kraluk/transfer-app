-- Create proper schema

CREATE SCHEMA IF NOT EXISTS TRANSFER_APP;
SET SCHEMA TRANSFER_APP;

-- DDLs

DROP TABLE IF EXISTS test_business_entity;

CREATE TABLE test_business_entity
(
  `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
  `business_id` UUID DEFAULT RANDOM_UUID(),
);

-- DMLs

-- TODO: add some test data