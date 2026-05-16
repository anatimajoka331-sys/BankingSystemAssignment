-- =================================================================
-- SYSTEM SCHEMA SETUP
-- =================================================================
CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

-- Table 1: Customers Core Profile Registry
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Table 2: Accounts Registry (Linked to Customers via Foreign Key)
CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'Active',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- Table 3: Core Financial Transactions ledger Engine 
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- Table 4: Secure System Activity Audit Log
CREATE TABLE audit_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    action_performed VARCHAR(255) NOT NULL,
    log_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 5: Branch Locations Registry (Fulfills the 5-table condition)
CREATE TABLE branches (
    branch_id INT AUTO_INCREMENT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    branch_code VARCHAR(20) UNIQUE NOT NULL,
    city VARCHAR(50) NOT NULL
);

-- =================================================================
-- POPULATE DEMO DATA ROWS (Minimum 5 rows per checklist requirement)
-- =================================================================
INSERT INTO customers (first_name, last_name, email, phone) VALUES 
('Alice', 'Smith', 'alice@email.com', '0112345678'),
('Bob', 'Jones', 'bob@email.com', '0118765432'),
('Charlie', 'Brown', 'charlie@email.com', '0123456789'),
('David', 'Miller', 'david@email.com', '0155551234'),
('Eva', 'Davis', 'eva@email.com', '0177775678');

INSERT INTO accounts (customer_id, account_number, account_type, balance) VALUES 
(1, 'ACC1001', 'Checking', 5000.00),
(2, 'ACC1002', 'Savings', 15000.00),
(3, 'ACC1003', 'Checking', 250.00),
(4, 'ACC1004', 'Savings', 8900.50),
(5, 'ACC1005', 'Checking', 12300.00);

INSERT INTO transactions (account_id, transaction_type, amount) VALUES 
(1, 'Deposit', 1000.00),
(2, 'Withdrawal', 500.00),
(3, 'Deposit', 250.00),
(4, 'Deposit', 4000.00),
(5, 'Withdrawal', 1200.00);

INSERT INTO audit_logs (action_performed) VALUES 
('System initialized profiles safely'),
('Account ACC1001 registered first clearance check'),
('Completed testing script row population data tracking profile'),
('Database sync automated status review completed'),
('System administrative configuration parameters saved successfully');

INSERT INTO branches (branch_name, branch_code, city) VALUES 
('Downtown Hub', 'BR001', 'Johannesburg'),
('Waterfront Branch', 'BR002', 'Cape Town'),
('Northside Suburb', 'BR003', 'Durban'),
('East Gate Mall', 'BR004', 'Pretoria'),
('Valley View Center', 'BR005', 'Port Elizabeth');

-- =================================================================
-- REQUIRED SELECT SEARCH QUERIES (5 Unique WHERE Conditions)
-- =================================================================
-- Query 1: Find wealthy saving accounts
SELECT * FROM accounts WHERE balance > 10000.00;

-- Query 2: Find a specific customer by email 
SELECT * FROM customers WHERE email = 'alice@email.com';

-- Query 3: List only Checking account types
SELECT * FROM accounts WHERE account_type = 'Checking';

-- Query 4: View checking branches located specifically in Cape Town
SELECT * FROM branches WHERE city = 'Cape Town';

-- Query 5: Find high-value transaction distributions
SELECT * FROM transactions WHERE amount >= 1000.00;
-- =================================================================
-- REQUIRED ASSIGNMENT TESTING: UPDATE & DELETE STATEMENTS
-- =================================================================

-- 1. First Required UPDATE Statement: Modify a specific account balance
UPDATE accounts 
SET balance = 5500.00 
WHERE account_number = 'ACC1001';

-- 2. Second Required UPDATE Statement: Update a customer's contact phone number
UPDATE customers 
SET phone = '0829991122' 
WHERE customer_id = 3;

-- 3. First Required DELETE Statement: Clear out a sample log record
DELETE FROM audit_logs 
WHERE log_id = 1;

-- 4. Second Required DELETE Statement: Remove an unused sample branch tracking row
DELETE FROM branches 
WHERE branch_code = 'BR005';

