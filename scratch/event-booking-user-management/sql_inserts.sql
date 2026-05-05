-- sql_inserts.sql
-- Run these queries in MySQL Workbench after Spring Boot has created the tables

-- Insert Admin User (Password is 'password123' hashed with BCrypt)
INSERT INTO users (user_type, first_name, last_name, email, username, password, phone_number, address, role, status, admin_level, created_at, updated_at)
VALUES ('ADMIN', 'System', 'Admin', 'admin@eventbooker.com', 'admin_super', 
'$2a$10$w/X2hY5z/53x9kF6A0.Y.e5r67Q.h1J6.X6h.17h.h.w.2721z.', '1234567890', 'Headquarters', 'ADMIN', 'ACTIVE', 5, NOW(), NOW());

-- Insert Customer Users (Password is 'password123' hashed with BCrypt)
INSERT INTO users (user_type, first_name, last_name, email, username, password, phone_number, address, role, status, loyalty_points, created_at, updated_at)
VALUES ('CUSTOMER', 'John', 'Doe', 'john@example.com', 'johndoe', 
'$2a$10$w/X2hY5z/53x9kF6A0.Y.e5r67Q.h1J6.X6h.17h.h.w.2721z.', '0987654321', 'New York', 'CUSTOMER', 'ACTIVE', 100, NOW(), NOW());

INSERT INTO users (user_type, first_name, last_name, email, username, password, phone_number, address, role, status, loyalty_points, created_at, updated_at)
VALUES ('CUSTOMER', 'Jane', 'Smith', 'jane@example.com', 'janesmith', 
'$2a$10$w/X2hY5z/53x9kF6A0.Y.e5r67Q.h1J6.X6h.17h.h.w.2721z.', '1112223333', 'London', 'CUSTOMER', 'INACTIVE', 0, NOW(), NOW());

INSERT INTO users (user_type, first_name, last_name, email, username, password, phone_number, address, role, status, loyalty_points, created_at, updated_at)
VALUES ('CUSTOMER', 'Bob', 'Johnson', 'bob@example.com', 'bobj', 
'$2a$10$w/X2hY5z/53x9kF6A0.Y.e5r67Q.h1J6.X6h.17h.h.w.2721z.', '4445556666', 'Sydney', 'CUSTOMER', 'SUSPENDED', 50, NOW(), NOW());
