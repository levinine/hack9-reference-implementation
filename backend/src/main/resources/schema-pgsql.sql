-- ID of the price is prefix + valid_from
CREATE TABLE IF NOT EXISTS Prices (
    "id" VARCHAR(60),
    "country" VARCHAR(50),
    "city" VARCHAR(100),
    "prefix" VARCHAR(50),
    "valid_from" TIMESTAMP WITH TIME ZONE,
    "price" DOUBLE PRECISION,
    "initial" INT,
    "increment" INT
);

CREATE TABLE IF NOT EXISTS Calls (
    "caller" VARCHAR(50),
    "called" VARCHAR(50),
    "started" TIMESTAMP WITH TIME ZONE,
    "duration" INT,
    "cost" DOUBLE PRECISION,
    "price_id" VARCHAR(60),
    "invoice_id" VARCHAR(100),
    "master_invoice_id" BIGINT
);

CREATE TABLE IF NOT EXISTS Invoices (
    "id" VARCHAR(100),
    "master_id" BIGINT,
    "calling" VARCHAR(30),
    "start_period" TIMESTAMP WITH TIME ZONE,
    "end_period" TIMESTAMP WITH TIME ZONE,
    "sum" DOUBLE PRECISION,
    "count" INT
);

CREATE SEQUENCE IF NOT EXISTS Invoice_ID;