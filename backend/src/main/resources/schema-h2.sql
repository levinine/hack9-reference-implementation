-- ID of the price is prefix + valid_from
CREATE TABLE IF NOT EXISTS Prices (
    id VARCHAR(60),
    country VARCHAR(50),
    city VARCHAR(50),
    prefix VARCHAR(50),
    valid_from TIMESTAMP,
    price DOUBLE,
    initial INT,
    increment INT
);

CREATE TABLE IF NOT EXISTS Calls (
    caller VARCHAR(50),
    called VARCHAR(50),
    started TIMESTAMP,
    duration INT,
    cost DOUBLE,
    price_id VARCHAR(60),
    invoice_id VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Invoices (
    id VARCHAR(100),
    calling VARCHAR(30),
    start TIMESTAMP,
    end TIMESTAMP,
    sum DOUBLE,
    count INT
);