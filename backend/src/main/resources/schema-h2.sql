-- ID of the price is prefix + valid_from
CREATE TABLE IF NOT EXISTS Prices (
    id VARCHAR(60),
    country VARCHAR(50),
    city VARCHAR(100),
    prefix VARCHAR(50),
    valid_from TIMESTAMP WITH TIME ZONE,
    price DOUBLE,
    initial INT,
    increment INT
);

CREATE TABLE IF NOT EXISTS Calls (
    caller VARCHAR(50),
    called VARCHAR(50),
    started TIMESTAMP WITH TIME ZONE,
    duration INT,
    cost DOUBLE,
    price_id VARCHAR(60),
    invoice_id VARCHAR(100),
    master_invoice_id BIGINT
);

CREATE TABLE IF NOT EXISTS Invoices (
    id VARCHAR(100),
    master_id BIGINT,
    calling VARCHAR(30),
    start_period TIMESTAMP WITH TIME ZONE,
    end_period TIMESTAMP WITH TIME ZONE,
    sum DOUBLE,
    count INT
);

CREATE SEQUENCE Invoice_ID;