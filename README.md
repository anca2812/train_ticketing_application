# 🚂 Train Ticketing Application

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)

The **Train Ticketing Application** is a Java-based console application designed to simulate a railway reservation system. It provides a complete solution for managing train schedules, routes, and ticket bookings with integrated business rules.

---

## 📌 Project Overview
The application enables users to:
* 🎫 **Book tickets** (one or multiple) for specific trains. [cite: 6]
* 🔍 **Search routes** between stations, including direct and changeover options. [cite: 11, 14]
* 🚫 **Prevent overbooking** by validating train capacity. [cite: 7, 8]
* ⏰ **Manage delays** and notify affected customers. [cite: 19, 20]
* 📋 **Administrative control** over trains, routes, and station data. [cite: 15, 16, 17]

---

## 🏗️ Architecture & Structure
The project follows a **layered architecture** to ensure clean separation of concerns:

### Project Structure
| Package | Description |
| :--- | :--- |
| `DataModel` | Contains core entities: `Booking`, `Train`, `Station`, `Route`, and `Schedule`. |
| `BusinessLogic` | Implements core logic for bookings, overbooking prevention, and email notifications. |
| `DataAccess` | Contains route-finding algorithms and connectivity management. |
| `Main.java` | The application entry point located in the root of the source folder. |

---

## ✨ Implemented Features

### 🎫 Ticket Booking & Overbooking Prevention
The application lists available schedules and validates seat availability before confirming a booking.
* **Flow:** Menu Option 3 -> Select Schedule ID -> Enter Customer Details -> Enter Seats.
* **Output:** `Booking successful!` or `Booking failed. Train capacity exceeded.`

### 🔍 Route Search (Direct & Connections)
Users can find travel options between two stations by providing their unique IDs.
* **Predefined Stations:** * ID 1: Cluj-Napoca
    * ID 2: Bucuresti Nord
    * ID 3: Brasov
* **Example:** Searching for ID 1 to ID 2 will return the "InterCity" train schedule.
  
### 🛠️ Administrator Operations
* **Train Management:** Modify train names (Option 6) or remove trains from the system (Option 7).
* **Booking Oversight:** Display all active reservations (Option 4).
* **Delay Reporting:** Marking a train as delayed (Option 5) extracts a list of affected customer emails for notification.

---

## ⚙️ Initial Data (Setup)
The application starts with the following data pre-loaded:
* **Trains:**
    * `InterCity` (Route: Cluj - Bucuresti, 200 seats)
    * `Regional` (Route: Bucuresti - Brasov, 100 seats)
* **Schedules:**
    * InterCity: Departs 08:00, Arrives 14:00 (2024-12-01)
    * Regional: Departs 10:00, Arrives 16:00 (2024-12-01)
---

## ▶️ How to Run

### 1. Clone the Repository
```bash
git clone [https://github.com/anca2812/train_ticketing_application.git](https://github.com/anca2812/train_ticketing_application.git)
cd train_ticketing_application
```

### 2. Run the Application
```bash
mvn exec:java "-Dexec.mainClass=Main"
```

## 🖥️ Console Menu Preview
---- Menu Train Ticketing Application ----
1. Display Stations
2. Search Routes
3. Book Ticket
4. Display All Bookings
5. Report Train Delay
6. Modify Train
7. Remove Train
8. Exit
Choose option:
