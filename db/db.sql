-- Insurance Application Database Schema

CREATE DATABASE IF NOT EXISTS insurance_db;
USE insurance_db;

-- Users/Customers Table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insurance Policies Table
CREATE TABLE policies (
    policy_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    policy_number VARCHAR(50) UNIQUE NOT NULL,
    policy_type ENUM('Health', 'Auto', 'Home', 'Life') NOT NULL,
    coverage_amount DECIMAL(12, 2) NOT NULL,
    premium_amount DECIMAL(10, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('Active', 'Inactive', 'Expired', 'Cancelled') DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    INDEX idx_customer (customer_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Claims Table
CREATE TABLE claims (
    claim_id INT AUTO_INCREMENT PRIMARY KEY,
    policy_id INT NOT NULL,
    claim_number VARCHAR(50) UNIQUE NOT NULL,
    claim_amount DECIMAL(12, 2) NOT NULL,
    claim_date DATE NOT NULL,
    claim_description TEXT,
    status ENUM('Pending', 'Approved', 'Rejected', 'Closed') DEFAULT 'Pending',
    approved_amount DECIMAL(12, 2),
    approval_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (policy_id) REFERENCES policies(policy_id) ON DELETE CASCADE,
    INDEX idx_policy (policy_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Payments Table
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    policy_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method ENUM('Credit Card', 'Debit Card', 'Bank Transfer', 'Check') NOT NULL,
    status ENUM('Pending', 'Completed', 'Failed') DEFAULT 'Pending',
    transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (policy_id) REFERENCES policies(policy_id) ON DELETE CASCADE,
    INDEX idx_policy (policy_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Agents Table
CREATE TABLE agents (
    agent_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(20),
    license_number VARCHAR(50) UNIQUE,
    commission_rate DECIMAL(5, 2) DEFAULT 5.00,
    status ENUM('Active', 'Inactive') DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample Data
INSERT INTO customers (first_name, last_name, email, phone, city, state, zip_code) VALUES
('John', 'Doe', 'john.doe@example.com', '555-1234', 'New York', 'NY', '10001'),
('Jane', 'Smith', 'jane.smith@example.com', '555-5678', 'Los Angeles', 'CA', '90001'),
('Robert', 'Johnson', 'robert.johnson@example.com', '555-9012', 'Chicago', 'IL', '60601');

INSERT INTO policies (customer_id, policy_number, policy_type, coverage_amount, premium_amount, start_date, end_date) VALUES
(1, 'POL-2024-001', 'Auto', 500000.00, 1200.00, '2024-01-01', '2025-01-01'),
(1, 'POL-2024-002', 'Home', 300000.00, 900.00, '2024-01-01', '2025-01-01'),
(2, 'POL-2024-003', 'Health', 100000.00, 500.00, '2024-02-01', '2025-02-01'),
(3, 'POL-2024-004', 'Life', 1000000.00, 300.00, '2024-03-01', '2025-03-01');

INSERT INTO agents (first_name, last_name, email, phone, license_number, commission_rate) VALUES
('Michael', 'Brown', 'michael.brown@insurance.com', '555-1111', 'LIC-001', 5.50),
('Sarah', 'Wilson', 'sarah.wilson@insurance.com', '555-2222', 'LIC-002', 6.00);

INSERT INTO payments (policy_id, amount, payment_date, payment_method, status) VALUES
(1, 1200.00, '2024-01-15', 'Credit Card', 'Completed'),
(2, 900.00, '2024-01-20', 'Bank Transfer', 'Completed'),
(3, 500.00, '2024-02-10', 'Debit Card', 'Completed');
