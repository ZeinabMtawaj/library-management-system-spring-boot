
# Library Management System - Documentation

## Table of Contents
- [Introduction](#1-introduction)
- [Technologies Used](#2-technologies-used)
- [Setup and Installation](#3-setup-and-installation)
- [Running the Application](#4-running-the-application)
- [API Endpoints](#5-api-endpoints)
- [Authentication and Authorization](#6-authentication-and-authorization)
- [Transaction Management](#7-transaction-management)
- [Caching](#8-caching)
- [Testing](#9-testing)
- [Error Handling](#10-error-handling)
- [Additional Features](#11-additional-features)

## 1. Introduction
This project is a Library Management System API built using Spring Boot. It allows librarians and administrators to manage books, patrons, and borrowing records efficiently. This document outlines how to set up the system, interact with the API endpoints, and utilize the security features implemented.

## 2. Technologies Used
- Spring Boot
- PostgreSQL: For data persistence
- Spring Security: For authentication and authorization
- Spring AOP: For logging execution time of controller methods
- Ehcache: For caching frequently accessed data
- Mockito: For unit testing
- Spring Boot Starter Validation: For input validation
- Spring ControllerAdvice: For global exception handling

## 3. Setup and Installation
### Clone the Repository
```bash
git clone https://github.com/ZeinabMtawaj/library-management-system.git
cd library-management-system
```

### Configure the Database
Update the `application.properties` file with the appropriate database settings:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/library
spring.datasource.username=test
spring.datasource.password=test
```

### Build the Project
Make sure you have Maven installed, and run:

```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

## 4. Running the Application
Once the application is running, it will be accessible at:
`http://localhost:8080`

When the application starts, two default user accounts will be created if they weren't already present:

- **Admin:**
  - Username: `admin`
  - Password: `admin`
  
- **Librarian:**
  - Username: `librarian`
  - Password: `librarian`

## 5. API Endpoints
### User Management (Admin Only)
- **POST /api/users** – Create a new user
  - Request:
  ```json
  {
      "username": "lib3",
      "password": "9941",
      "type": "LIBRARIAN"
  }
  ```

- **GET /api/users** – Get a list of all users
- **GET /api/users/{id}** – Get details of a specific user
- **PUT /api/users/{id}** – Update user details
- **DELETE /api/users/{id}** – Remove a user

### Book Management (Admin and Librarian)
- **GET /api/books** – Retrieve a list of all books
- **GET /api/books/{id}** – Get details of a specific book
- **POST /api/books** – Add a new book
- **PUT /api/books/{id}** – Update book details
- **DELETE /api/books/{id}** – Remove a book

### Patron Management (Admin and Librarian)
- **GET /api/patrons** – Retrieve a list of all patrons
- **GET /api/patrons/{id}** – Get details of a specific patron
- **POST /api/patrons** – Add a new patron
- **PUT /api/patrons/{id}** – Update patron details
- **DELETE /api/patrons/{id}** – Remove a patron

### Borrowing Records (Admin and Librarian)
- **POST /api/borrow/{bookId}/patron/{patronId}** – Borrow a book
- **PUT /api/return/{bookId}/patron/{patronId}** – Return a borrowed book

## 6. Authentication and Authorization
### Users and Roles
- **Admin**: Has full access to all endpoints.
- **Librarian**: Can manage books, patrons, and borrowing records.

### Access Control
The system uses Basic Authentication. Each request to the API must include a valid username and password.

## 7. Transaction Management
The application uses Spring's `@Transactional` annotation to ensure data integrity. For example, when borrowing or returning a book, if any step fails, the entire operation will be rolled back.

## 8. Caching
The system uses Ehcache to store frequently accessed data, improving performance and reducing load on the database.

## 9. Testing
Unit tests are written using JUnit and Mockito.

## 10. Error Handling
The system handles errors gracefully by returning appropriate HTTP status codes. It uses Spring `ControllerAdvice` to provide consistent error responses across the application.

## 11. Additional Features
- **Logging**:
  Spring AOP logs the execution time of controller methods, offering insights into performance.
