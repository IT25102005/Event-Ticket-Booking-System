# Venue & Seat Management Module Documentation

This document contains everything you need to know to explain your module during the viva, test the API, and understand how Object-Oriented Programming (OOP) concepts were applied.

---

## 1. SQL Sample Insert Data
You can run this SQL in your MySQL Workbench to populate the database with sample data. *Note: Spring Boot will automatically create the tables if you have `spring.jpa.hibernate.ddl-auto=update` in `application.properties`.*

```sql
-- Insert an Indoor Venue
INSERT INTO venues (venue_type, venue_name, location, address, city, capacity, description, facilities, image_url, status, created_at, updated_at, air_conditioned, number_of_halls, has_sound_system) 
VALUES ('INDOOR', 'Nelum Pokuna Theatre', 'Colombo 07', 'No 110 Ananda Coomaraswamy Mawatha', 'Colombo', 1288, 'A fully equipped modern theatre.', 'Parking, AC, Sound System, Restrooms', 'https://upload.wikimedia.org/wikipedia/commons/4/4b/Nelum_Pokuna.jpg', 'ACTIVE', NOW(), NOW(), true, 1, true);

-- Insert an Outdoor Venue
INSERT INTO venues (venue_type, venue_name, location, address, city, capacity, description, facilities, image_url, status, created_at, updated_at, weather_protected, open_ground_area, has_parking_area) 
VALUES ('OUTDOOR', 'Galle Face Green', 'Galle Face', 'Galle Face Center Road', 'Colombo', 50000, 'Large urban park by the sea.', 'Public Restrooms, Food Stalls', 'https://upload.wikimedia.org/wikipedia/commons/e/e8/Galle_Face_Green_Colombo_Sri_Lanka.jpg', 'ACTIVE', NOW(), NOW(), false, 50000.0, true);

-- Insert Regular Seats for Nelum Pokuna (Assuming venue_id = 1)
INSERT INTO seats (seat_category, venue_id, section_name, row_name, seat_number, seat_type, seat_status, base_price, final_price, created_at, updated_at, standard_view, regular_service_fee)
VALUES 
('REGULAR', 1, 'Balcony', 'A', '1', 'REGULAR', 'AVAILABLE', 1000.00, 1000.00, NOW(), NOW(), true, 0.00),
('REGULAR', 1, 'Balcony', 'A', '2', 'REGULAR', 'AVAILABLE', 1000.00, 1000.00, NOW(), NOW(), true, 0.00);

-- Insert VIP Seats for Nelum Pokuna (Assuming venue_id = 1)
INSERT INTO seats (seat_category, venue_id, section_name, row_name, seat_number, seat_type, seat_status, base_price, final_price, created_at, updated_at, lounge_access, complimentary_drink, vip_service_fee)
VALUES 
('VIP', 1, 'Front Row', 'VIP-A', '1', 'VIP', 'AVAILABLE', 5000.00, 5500.00, NOW(), NOW(), true, true, 500.00),
('VIP', 1, 'Front Row', 'VIP-A', '2', 'VIP', 'AVAILABLE', 5000.00, 5500.00, NOW(), NOW(), true, true, 500.00);
```

---

## 2. Explanation of OOP Concepts Used

### 1. Encapsulation
- **How it's used**: All instance variables inside entities (`Venue`, `Seat`) and DTOs (`VenueRequestDTO`, `SeatResponseDTO`) are marked as `private`. They can only be accessed or modified using `public getX()` and `setX()` methods.
- **Why it matters**: This prevents external code from putting the object into an invalid state. We also enforce validation annotations (like `@NotBlank`, `@Min`) in the DTOs to encapsulate validation logic.

### 2. Inheritance
- **How it's used**: We created a parent class `Venue`. Then we created two child classes: `IndoorVenue extends Venue` and `OutdoorVenue extends Venue`. Similarly, we have `Seat`, with `RegularSeat extends Seat` and `VIPSeat extends Seat`.
- **Why it matters**: Instead of duplicating fields like `venueName` and `capacity`, they are written once in `Venue` and inherited by the child classes. We used JPA's Single Table Inheritance (`@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`), which means all venues are stored in one database table, distinguished by a `venue_type` column.

### 3. Polymorphism
- **How it's used**: Method Overriding. In the `Venue` class, there is a method `getVenueTypeDescription()`. This method is overridden in both `IndoorVenue` and `OutdoorVenue` to return different, specific strings. Similarly, in the `Seat` class, `calculateFinalPrice()` is overridden in `RegularSeat` and `VIPSeat` (VIP adds a `vipServiceFee`).
- **Why it matters**: In the `VenueServiceImpl`, when mapping the entity to the DTO, we simply call `venue.getVenueTypeDescription()`. Java automatically figures out at runtime whether the `venue` object is an Indoor or Outdoor venue and calls the correct method.

### 4. Abstraction
- **How it's used**: We defined interfaces `VenueService` and `SeatService`. These interfaces only declare method signatures (like `createVenue(...)`, `addSeat(...)`) but do not contain the actual logic. The logic is implemented in `VenueServiceImpl` and `SeatServiceImpl`.
- **Why it matters**: The `VenueController` only knows about `VenueService`. It does not know *how* a venue is saved to the database. This separates "what to do" from "how to do it".

---

## 3. Simple Class Diagram Explanation

If you are asked to explain the class diagram, use this structure:

**Controllers:**
`VenueController` and `SeatController`. These handle HTTP requests from the frontend and call the Services.

**Services:**
`VenueService` (Interface) -> Implemented by `VenueServiceImpl`.
`SeatService` (Interface) -> Implemented by `SeatServiceImpl`.

**Repositories:**
`VenueRepository` and `SeatRepository`. These extend `JpaRepository` to talk to the MySQL database.

**Models (Entities):**
*Venue Hierarchy:*
```
       [Venue] (Parent)
      /       \
[IndoorVenue] [OutdoorVenue] (Children)
```
*Seat Hierarchy:*
```
       [Seat] (Parent)
      /      \
[RegularSeat] [VIPSeat] (Children)
```

**Relationships:**
`Venue` 1 ----- * `Seat` (One-to-Many Relationship). One venue can have many seats. A seat belongs to one venue.

---

## 4. API Testing Examples (Postman)

**1. Create a new Indoor Venue**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/venues`
- **Body (JSON)**:
```json
{
  "venueName": "BMICH",
  "venueType": "INDOOR",
  "location": "Colombo 07",
  "city": "Colombo",
  "capacity": 1500,
  "airConditioned": true,
  "numberOfHalls": 3
}
```

**2. Add a Block of Seats**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/venues/1/seats/bulk` (Assuming BMICH is ID 1)
- **Body (JSON)**:
```json
{
  "sectionName": "Main Balcony",
  "rowName": "A",
  "startingSeatNumber": 1,
  "numberOfSeats": 50,
  "seatType": "REGULAR",
  "basePrice": 1000.00
}
```

**3. Get Venue Details**
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/venues/1`

**4. Change Seat Status**
- **Method**: `PATCH`
- **URL**: `http://localhost:8080/api/seats/1/status` (Assuming Seat ID 1)
- **Body (JSON)**:
```json
{
  "status": "BOOKED"
}
```

---

## 5. Beginner-Friendly Viva Explanation

If the lecturer asks **"Explain how your module works"**, you can answer like this:

> "My module is Venue and Seat Management. I built the backend using Java Spring Boot and the frontend using HTML, CSS, and plain JavaScript. 
>
> First, an Admin can create a Venue. Because of **Inheritance**, when they create a venue, they must choose if it's an Indoor Venue or an Outdoor Venue. Indoor venues have special fields like AC and number of halls, while Outdoor venues have fields like ground area. 
> 
> Once the venue is created, the Admin can generate Seats for that venue. The `Venue` and `Seat` have a One-to-Many relationship. When creating seats, they can choose between a Regular Seat or a VIP Seat. Using **Polymorphism**, the system calculates the final price differently—for example, VIP seats automatically add a VIP service fee to the base price.
>
> All of my database interactions are hidden behind Service Interfaces, which demonstrates **Abstraction**. And I used DTOs (Data Transfer Objects) to safely transfer data between the frontend and backend, which represents **Encapsulation**."

---

## 6. GitHub Branch Workflow Commands

To push this exact code to your group's repository on a new branch, run these commands in your terminal:

```bash
# Create a new branch and switch to it
git checkout -b venue-seat-management

# Add all the new files to staging
git add .

# Commit the files with a message
git commit -m "Implement full venue and seat management module with UI and backend"

# Push the branch to the remote GitHub repository
git push origin venue-seat-management
```
