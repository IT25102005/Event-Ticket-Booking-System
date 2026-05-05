# Architecture & Class Diagrams

Here are the visual charts representing the code we generated. These charts will be very helpful for your university project presentation and documentation.

## 1. Object-Oriented Class Diagram

This diagram visualizes the **Inheritance**, **Encapsulation**, and **Polymorphism** in your models.

```mermaid
classDiagram
    class User {
        <<Abstract>>
        -Long userId
        -String firstName
        -String lastName
        -String email
        -String password
        -UserRole role
        -AccountStatus status
        +getRoleDescription()* String
        +gettersAndSetters()
    }
    
    class CustomerUser {
        -int loyaltyPoints
        -String preferredCategory
        +getRoleDescription() String
        +getLoyaltyPoints() int
        +setLoyaltyPoints(int)
    }
    
    class AdminUser {
        -int adminLevel
        -String permissionCode
        +getRoleDescription() String
        +getAdminLevel() int
        +setAdminLevel(int)
    }
    
    class UserRole {
        <<Enumeration>>
        CUSTOMER
        ADMIN
    }
    
    class AccountStatus {
        <<Enumeration>>
        ACTIVE
        INACTIVE
        SUSPENDED
    }

    User <|-- CustomerUser : Inheritance
    User <|-- AdminUser : Inheritance
    User "1" *-- "1" UserRole : Composition
    User "1" *-- "1" AccountStatus : Composition
```

## 2. Abstraction & Interface Diagram

This diagram shows how the Controller interacts with the Service Interfaces (Abstraction), hiding the implementation details.

```mermaid
classDiagram
    class UserController {
        -UserService userService
        +getAllUsers()
        +getUserById(Long id)
        +updateUserProfile()
    }
    
    class UserService {
        <<Interface>>
        +getAllUsers() List
        +getUserById(Long id) UserResponseDTO
        +updateUserProfile() UserResponseDTO
    }
    
    class UserServiceImpl {
        -UserRepository userRepository
        -PasswordEncoder passwordEncoder
        +getAllUsers() List
        +getUserById(Long id) UserResponseDTO
    }
    
    class UserRepository {
        <<Interface>>
        +findByEmail(String email)
        +save(User user)
    }

    UserController --> UserService : Depends on Interface
    UserServiceImpl ..|> UserService : Implements
    UserServiceImpl --> UserRepository : Uses
```

## 3. System Architecture & Flow Chart

This flowchart demonstrates the MVC (Model-View-Controller) architecture and how data flows from the frontend to the database.

```mermaid
flowchart TD
    %% Frontend Layer
    subgraph Frontend [Frontend (HTML/CSS/JS)]
        UI[User Dashboard UI]
        Auth[Login/Register UI]
        JS[Fetch API Calls]
    end

    %% Backend Layer (Spring Boot)
    subgraph Backend [Backend (Spring Boot MVC)]
        RC[REST Controllers\nAuthController / UserController]
        SV[Service Layer\nAuthService / UserService]
        RP[Repository Layer\nUserRepository]
        MD[Models / Entities\nUser / CustomerUser / AdminUser]
    end

    %% Database Layer
    subgraph DB [Database]
        MySQL[(MySQL Database)]
    end

    %% Flow connections
    UI --> JS
    Auth --> JS
    JS -- JSON HTTP Requests --> RC
    RC -- DTOs --> SV
    SV -- Business Logic / BCrypt --> RP
    RP -- Entity Objects --> MD
    RP -- JPA/Hibernate SQL --> MySQL
    MySQL -- Result Set --> RP
    SV -- Maps to Response DTO --> RC
    RC -- JSON HTTP Response --> JS
```
