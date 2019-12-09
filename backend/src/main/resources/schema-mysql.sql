-- ID of the price is prefix + valid_from
CREATE TABLE IF NOT EXISTS Prices (
    `id` VARCHAR(60),
    `country` VARCHAR(50),
    `city` VARCHAR(100),
    `prefix` VARCHAR(50),
    `valid_from` TIMESTAMP,
    `price` DOUBLE,
    `initial` INT,
    `increment` INT
);

--
-- Call register
--
CREATE TABLE IF NOT EXISTS Calls (
    `caller` VARCHAR(50),
    `called` VARCHAR(50),
    `started` TIMESTAMP,
    `duration` INT,
    `cost` DOUBLE,
    `price_id` VARCHAR(60),
    `invoice_id` VARCHAR(100),
    `master_invoice_id` BIGINT
);

--
-- Invoices
--
CREATE TABLE IF NOT EXISTS Invoices (
    `id` VARCHAR(100),
    `master_id` BIGINT,
    `calling` VARCHAR(30),
    `start_period` TIMESTAMP,
    `end_period` TIMESTAMP,
    `sum` DOUBLE,
    `count` INT
);

--
-- Master ID sequence
--
CREATE TABLE IF NOT EXISTS MasterInvoiceId (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	
	PRIMARY KEY (id)
);
