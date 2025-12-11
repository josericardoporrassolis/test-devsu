-- =============================================
-- 1. DATABASE CREATION
-- =============================================
IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'db_customers')
BEGIN
    CREATE DATABASE db_customers;
END
GO

IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'db_accounts')
BEGIN
    CREATE DATABASE db_accounts;
END
GO

-- =============================================
-- 2. MICROSERVICE: CUSTOMERS (db_customers)
-- =============================================
USE db_customers;
GO

-- Drop tables if exist
IF OBJECT_ID('dbo.customer', 'U') IS NOT NULL DROP TABLE dbo.customer;
IF OBJECT_ID('dbo.person', 'U') IS NOT NULL DROP TABLE dbo.person;

-- Table: Person
CREATE TABLE person (
                        person_id INT IDENTITY(1,1) PRIMARY KEY,
                        name VARCHAR(100),
                        gender VARCHAR(20),
                        age INT,
                        identification VARCHAR(20) UNIQUE,
                        address VARCHAR(200),
                        phone VARCHAR(20)
);

-- Table: Customer
CREATE TABLE customer (
                          person_id INT PRIMARY KEY,
                          password VARCHAR(50) NOT NULL,
                          status BIT NOT NULL,
                          email VARCHAR(50) UNIQUE NOT NULL,
                          FOREIGN KEY (person_id) REFERENCES person(person_id)
);
GO

-- Initial Data
IF NOT EXISTS (SELECT 1 FROM person WHERE identification = '1234567890')
BEGIN
INSERT INTO person (name, gender, age, identification, address, phone)
VALUES ('Jose Lema', 'Male', 30, '1234567890', 'Otavalo sn y principal', '098254785');

DECLARE @id1 INT = SCOPE_IDENTITY();

INSERT INTO customer (person_id, password, status, email)
VALUES (@id1, '1234', 1, 'jose.lema@email.com');
END
GO

IF NOT EXISTS (SELECT 1 FROM person WHERE identification = '9876543210')
BEGIN
INSERT INTO person (name, gender, age, identification, address, phone)
VALUES ('Marianela Montalvo', 'Female', 25, '9876543210', 'Amazonas y NNUU', '097548965');

DECLARE @id2 INT = SCOPE_IDENTITY();

INSERT INTO customer (person_id, password, status, email)
VALUES (@id2, '5678', 1, 'marianela.montalvo@email.com');
END
GO

IF NOT EXISTS (SELECT 1 FROM person WHERE identification = '1122334455')
BEGIN
INSERT INTO person (name, gender, age, identification, address, phone)
VALUES ('Juan Osorio', 'Male', 35, '1122334455', '13 junio y Equinoccial', '098874587');

DECLARE @id3 INT = SCOPE_IDENTITY();

INSERT INTO customer (person_id, password, status, email)
VALUES (@id3, '1245', 1, 'juan.osorio@email.com');
END
GO

-- =============================================
-- 3. MICROSERVICE: ACCOUNTS (db_accounts)
-- =============================================
USE db_accounts;
GO

-- Drop tables if exist
IF OBJECT_ID('dbo.movement', 'U') IS NOT NULL DROP TABLE dbo.movement;
IF OBJECT_ID('dbo.account', 'U') IS NOT NULL DROP TABLE dbo.account;

-- Table: Account
CREATE TABLE account (
                         account_number VARCHAR(20) PRIMARY KEY,
                         account_type VARCHAR(20) NOT NULL,
                         initial_balance DECIMAL(18, 2) NOT NULL,
                         status BIT NOT NULL,
                         person_id INT NOT NULL
);
GO

-- Table: Movement
CREATE TABLE movement (
                          movement_id INT IDENTITY(1,1) PRIMARY KEY,
                          movement_date DATETIME NOT NULL,
                          movement_type VARCHAR(20) NOT NULL,
                          movement_value DECIMAL(18, 2) NOT NULL,
                          balance DECIMAL(18, 2) NOT NULL,
                          account_number VARCHAR(20) NOT NULL,
                          FOREIGN KEY (account_number) REFERENCES account(account_number)
);
GO

-- Initial Data
IF NOT EXISTS (SELECT 1 FROM account WHERE account_number = '478758')
BEGIN
INSERT INTO account (account_number, account_type, initial_balance, status, person_id)
VALUES ('478758', 'Savings', 2000.00, 1, 1); -- Jose Lema
END
GO

IF NOT EXISTS (SELECT 1 FROM account WHERE account_number = '225487')
BEGIN
INSERT INTO account (account_number, account_type, initial_balance, status, person_id)
VALUES ('225487', 'Checking', 100.00, 1, 2); -- Marianela
END
GO

IF NOT EXISTS (SELECT 1 FROM account WHERE account_number = '495878')
BEGIN
INSERT INTO account (account_number, account_type, initial_balance, status, person_id)
VALUES ('495878', 'Savings', 0.00, 1, 3); -- Juan Osorio
END
GO

IF NOT EXISTS (SELECT 1 FROM account WHERE account_number = '496825')
BEGIN
INSERT INTO account (account_number, account_type, initial_balance, status, person_id)
VALUES ('496825', 'Savings', 540.00, 1, 2); -- Marianela
END
GO

IF NOT EXISTS (SELECT 1 FROM account WHERE account_number = '585545')
BEGIN
INSERT INTO account (account_number, account_type, initial_balance, status, person_id)
VALUES ('585545', 'Checking', 1000.00, 1, 1); -- Jose Lema
END
GO
