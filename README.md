# Spring-boot-Quiz-backend-REST-api

A backend-only quiz REASTful api application built with Spring Boot ready to integrate frontend frameworks like React.js, focusing on security, performance, and layered architecture.

Key Features:
Spring Security & JWT: Implements secure authentication with refresh/rotate tokens stored in HttpOnly cookies.
Layered Architecture: Organized for maintainability, scalability, and separation of concerns.
Performance Optimizations: Converts SQL query results into maps for faster data retrieval.
Secure Quiz Management: Supports authenticated users, admin operations, and fast backend processing.
Relational Tables with JPA: Models quizzes, questions, choices, and users using JPA entities with relationships like One-to-Many and Many-to-One.

Admin Functionality
Admins can create courses, add subjects, and manage subject quizzes.
Secure CRUD operations for managing quiz content.

User Functionality
Users can create accounts and enroll in courses.
Add subjects to enrolled courses.
Take quizzes and receive real-time scoring.

Tech Stack:
Spring Boot, Spring Security, JWT, MySQL
Layered architecture (Controller → Service → Repository)
Optimized SQL handling with map-based data structures
