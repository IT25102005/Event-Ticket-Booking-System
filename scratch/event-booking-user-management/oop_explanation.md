# OOP Concepts Viva Guide

This guide explains how Object-Oriented Programming (OOP) concepts are implemented in your Spring Boot User Management module. Use these points during your university presentation.

## 1. Encapsulation
**Concept:** Bundling data (variables) and methods (functions) together and restricting direct access to the data.
**How it is used:**
- In the `User.java` class, all fields (like `firstName`, `email`, `password`) are marked as `private`.
- They can only be accessed or modified using public `getter` and `setter` methods. 
- *Why it's important:* We protect the `password` field. In our `UserResponseDTO`, we simply don't include the password field at all. This ensures that when user data is sent to the frontend, the password is completely encapsulated and hidden.

## 2. Inheritance
**Concept:** A mechanism where one class acquires the properties and behaviors of a parent class.
**How it is used:**
- We created a base abstract class `User`.
- We created two child classes: `CustomerUser extends User` and `AdminUser extends User`.
- *Why it's important:* Both customers and admins have names, emails, and passwords, so they inherit these from `User` to avoid code duplication. However, `CustomerUser` adds its own specific fields like `loyaltyPoints`, while `AdminUser` adds `adminLevel`. We used JPA `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)` to map this beautifully to the database using a `user_type` discriminator column.

## 3. Polymorphism
**Concept:** The ability of different objects to respond in a unique way to the same method call.
**How it is used:**
- We declared an abstract method `getRoleDescription()` in the base `User` class.
- Both `CustomerUser` and `AdminUser` provide their own specific `Override` for this method.
- *Why it's important:* If we have a list of generic `User` objects and call `user.getRoleDescription()`, the system dynamically knows whether to return "Customer user with X loyalty points" or "Administrator user with clearance level X", without needing `if/else` statements.

## 4. Abstraction
**Concept:** Hiding complex implementation details and showing only the essential features of the object.
**How it is used:**
- We created interfaces like `UserService` and `AuthService`.
- The Controllers (like `UserController`) only talk to the `UserService` interface, not the actual implementation class (`UserServiceImpl`).
- *Why it's important:* The Controller says "I need to register a user", but it doesn't care *how* it's done. The implementation (database saving, password hashing) is hidden inside `AuthServiceImpl`. If we change how passwords are saved later, we don't need to touch the Controller.

---

## Class Diagram Explanation (Text Format)

```text
       <<Interface>>
        UserService
             ^
             | (implements)
             |
      UserServiceImpl ------------> UserRepository <---- JpaRepository

             <<Abstract>>
                User
        (- userId, - email)
        (+ getEmail(), + getRoleDescription())
               ^
               | (extends)
      _________|_________
     |                   |
CustomerUser         AdminUser
(- loyaltyPoints)    (- adminLevel)
```

## Setup & Testing Guide

1. **Run Backend:** Open the project in IntelliJ IDEA. Let Maven download dependencies. Run `UserManagementApplication.java`. Ensure MySQL is running on port 3306.
2. **Run Frontend:** Just double-click the HTML files (e.g., `login.html`) or use VS Code Live Server to open them in your browser.
3. **Database:** After running the backend once, tables will be auto-created. Run the script in `sql_inserts.sql` in MySQL Workbench to load sample users.

## GitHub Branch Workflow Commands
```bash
git checkout -b user-account-management
git add .
git commit -m "Implement complete user and account management module with UI"
git push origin user-account-management
```
