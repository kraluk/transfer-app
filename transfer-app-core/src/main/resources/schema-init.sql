-- Create proper schema

CREATE SCHEMA IF NOT EXISTS TRANSFER_APP;
SET SCHEMA TRANSFER_APP;

-- DDLs

DROP TABLE IF EXISTS financial_transaction;

CREATE TABLE financial_transaction
(
  `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
  `business_id` UUID               DEFAULT RANDOM_UUID(),
  `name`        VARCHAR(256),

  `created_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- DMLs

-- TODO: add some test data