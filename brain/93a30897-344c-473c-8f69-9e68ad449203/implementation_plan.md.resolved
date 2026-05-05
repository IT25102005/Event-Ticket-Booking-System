# Implementation Plan: User & Account Management Module

This plan outlines the architecture, file structure, and development steps for the "User & Account Management" module of the Event Ticket Booking System, following all required OOP principles and standard MVC architecture.

## User Review Required

> [!IMPORTANT]  
> Please review the proposed folder structure, the technology choices (e.g., standard explicit getters/setters instead of Lombok to clearly show Encapsulation during your viva), and the database configurations. I will create a new directory at `C:\Users\akmal\.gemini\antigravity\scratch\event-booking-user-management` for this project. **Please set this directory as your active workspace after I start execution.**

> [!WARNING]  
> Since you require BCrypt for password hashing without full Spring Security overhead (to keep it simple for beginners), I will include the `spring-security-crypto` library specifically for the `BCryptPasswordEncoder`. This avoids complex security filter chains while meeting your security requirement.

## Open Questions

1. Do you have a specific Maven Group ID and Artifact ID you prefer? (Default: `com.eventbooking` and `user-management`).
2. Are you okay with me generating standard getters and setters instead of using Lombok? This is highly recommended for university projects as it explicitly demonstrates **Encapsulation** to the examiner.

## Proposed Changes

We will create a standard Maven Spring Boot project structure along with a static folder for the frontend.

### Project Setup
- Generate standard `pom.xml` with dependencies: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `mysql-connector-j`, `spring-boot-starter-validation`, `spring-security-crypto`.
- Configure `application.properties` with MySQL connection details and `spring.jpa.hibernate.ddl-auto=update`.

### Backend: Core Models (Demonstrating Inheritance & Encapsulation)
- `model/User.java`: Abstract base class with `@Entity` and `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`. Private fields with getters/setters.
- `model/CustomerUser.java`: Extends `User`. Adds `loyaltyPoints` and `preferredCategory`.
- `model/AdminUser.java`: Extends `User`. Adds `adminLevel` and `permissionCode`.
- `model/UserRole.java`: Enum (CUSTOMER, ADMIN).
- `model/AccountStatus.java`: Enum (ACTIVE, INACTIVE, SUSPENDED).

### Backend: Repositories & Services (Demonstrating Abstraction & Polymorphism)
- `repository/UserRepository.java`: Extends `JpaRepository<User, Long>`. Includes custom finder methods.
- `service/UserService.java` & `service/AuthService.java`: Interfaces defining business operations.
- `service/UserServiceImpl.java` & `service/AuthServiceImpl.java`: Implementations containing core logic. Polymorphism is demonstrated by overridden methods returning customized details based on user type.

### Backend: Controllers & DTOs
- `controller/AuthController.java`: Handles `/api/auth/register`, `/api/auth/register-admin`, and `/api/auth/login`.
- `controller/UserController.java`: Handles CRUD APIs under `/api/users`.
- `dto/*`: DTOs to transfer data without exposing the `User` entity directly (e.g., avoiding password leakage).
- `exception/*`: Custom exception handling for clean error messages.

### Frontend: HTML, CSS, JS
- Beautiful, responsive HTML pages with glassmorphism UI: `login.html`, `register.html`, `user-dashboard.html`, `profile.html`, `edit-profile.html`, `change-password.html`, `user-details.html`.
- `css/user-management.css`: Core styles, variables, gradients.
- `js/auth.js`: Fetch API logic for login and registration.
- `js/user-management.js`: Fetch API logic for dashboard, CRUD, and profile management.

### Documentation & Explanations
- `oop_explanation.md`: Detailed mapping of OOP concepts.
- `viva_guide.md`: Step-by-step viva explanation guide.
- `postman_collection.json` or text guide: API testing examples.
- `sql_inserts.sql`: Sample data.

## Verification Plan

### Automated/API Verification
- Send requests using internal scripts or Postman to test Registration, Login, Profile updates, and Admin actions.
- Ensure passwords are saved as BCrypt hashes in the database.

### Manual Verification
- Start the Spring Boot application.
- Open the HTML files in a browser to test the full E2E flow (Register -> Login -> Dashboard -> Edit Profile).
- Validate that the UI matches the premium, modern aesthetic requested.
