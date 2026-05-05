# Walkthrough: User & Account Management Module

The full backend and frontend implementation for the Event Ticket Booking System User Management module is complete! This document summarizes the changes and how you can run and present your project.

## What Was Completed

### Backend Architecture (Spring Boot)
- **Domain Models:** Created the `User` base entity with `CustomerUser` and `AdminUser` subclasses mapped using JPA's `SINGLE_TABLE` inheritance strategy. Strict encapsulation was maintained using explicit getters and setters instead of Lombok, as requested for your viva.
- **Data Transfer Objects (DTOs):** DTOs like `RegisterRequestDTO` and `UserResponseDTO` map incoming data and protect sensitive fields like passwords from being leaked.
- **Service Layer:** Created the `UserService` and `AuthService` interfaces and their implementations. This cleanly abstracts business logic (like BCrypt password hashing, fetching by roles) away from the controllers.
- **Exception Handling:** A `@RestControllerAdvice` global error handler gracefully catches errors (like `UserNotFoundException` and `DuplicateEmailException`) to return clean JSON error responses to the frontend.
- **REST APIs:** The `AuthController` handles registration and login while the `UserController` handles full CRUD (Profile updates, Search, Change Password, Deactivation).

### Frontend Architecture (HTML/CSS/JS)
- **Antigravity Theme:** The UI features a premium deep-space aesthetic (`#030508`) with interactive glassmorphism (`backdrop-filter`) and cyan/purple glowing shadows.
- **User Dashboard:** A responsive layout displaying key metrics in floating cards (CSS `@keyframes` animations) and a data table for user filtering.
- **Forms & Integration:** Login, Registration, Profile, and Settings pages have been integrated using vanilla JavaScript (`fetch` API) to send actual POST/PUT/PATCH requests to the local Spring Boot backend.

### Documentation
- **OOP Viva Guide:** An `oop_explanation.md` document exists in the workspace. It details precisely where Encapsulation, Inheritance, Polymorphism, and Abstraction are used in the codebase so you can ace your presentation.
- **SQL Pre-loads:** An `sql_inserts.sql` script provides test data with pre-hashed BCrypt passwords.

## How to Test and Run

1. **Start the Database:** Make sure your local MySQL server is running on port 3306. Create an empty database named `event_ticket_booking`.
2. **Start the Backend:** Open the `event-booking-user-management` folder in IntelliJ IDEA. Let Maven resolve the dependencies (`pom.xml`). Run the `UserManagementApplication` main class.
3. **Load Sample Data:** Open MySQL Workbench and run the queries inside `sql_inserts.sql`. This adds an Admin user (`admin_super` / `password123`) and a few customer users.
4. **Open Frontend:** Navigate to the `frontend` folder and open `login.html` in any web browser. You can test the login and registration flows directly against the live Spring Boot API.

> [!TIP]
> **Viva Prep:** Make sure to open the `oop_explanation.md` file and review how `getRoleDescription()` demonstrates Polymorphism across the `CustomerUser` and `AdminUser` entities. Examiners love clear examples of overriding abstract methods!
