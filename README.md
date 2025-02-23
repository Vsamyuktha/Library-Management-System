Library Management System
Install MYSQL

Create database: create database library_management_system;

Create user: CREATE USER 'lms'@'localhost' IDENTIFIED BY 'lms';

Grant permission: GRANT ALL PRIVILEGES ON library_management_system.* TO 'lms'@'localhost';