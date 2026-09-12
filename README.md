# Hospital Appointment Management System

A Java-based console application designed to manage hospital patients, doctors, and appointments using a modular, service-oriented architecture.

## Project Overview

The Hospital Appointment Management System allows patients and hospital staff to manage doctor appointments efficiently. The application supports patient registration, doctor management, appointment booking, cancellation, rescheduling, doctor schedules, and patient appointment history.

The project is designed using real-world software development principles such as **Object-Oriented Programming, layered architecture, separation of concerns, exception handling, validation, Java Collections, and the Java Date & Time API**.

## Key Features

* Patient registration and management
* Doctor registration and management
* Doctor specialization management
* Appointment booking
* Appointment cancellation
* Appointment rescheduling
* Available time-slot management
* Prevention of duplicate appointment slots
* Doctor schedule viewing
* Patient appointment history
* Appointment status management
* Input and business validation
* Custom exception handling
* Automatic ID generation
* Console-based user-friendly menus

## Architecture

The application follows a layered architecture:

```text
User
  ↓
UI / Menu Layer
  ↓
Service Layer
  ↓
Repository Layer
  ↓
In-Memory Data Storage
```

### UI Layer

Responsible for:

* Displaying menus
* Reading user input
* Displaying results and messages

### Service Layer

Responsible for:

* Business logic
* Appointment rules
* Validation
* Patient and doctor operations
* Appointment management

### Repository Layer

Responsible for:

* Storing application data
* Searching data
* Updating data
* Deleting data

The current implementation uses Java `HashMap` for in-memory storage.

## Technologies Used

* Java
* Object-Oriented Programming
* Collections Framework
* `HashMap`
* `ArrayList`
* Java Date & Time API
* Exception Handling
* Regular Expressions
* Switch Statements
* Eclipse IDE

## Main Java Concepts Demonstrated

* Classes and Objects
* Encapsulation
* Constructors
* Enums
* Inheritance through custom exceptions
* Collections
* Service and Repository patterns
* Dependency Injection through constructors
* Exception Handling
* Input Validation
* `LocalDate`
* `LocalTime`
* `LocalDateTime`
* Modular application design

## Project Structure

```text
HospitalAppointmentManagementSystem/
│
├── src/
│   └── com/
│       └── hospital/
│           └── appointment/
│               │
│               ├── App.java
│               │
│               ├── model/
│               │   ├── Patient.java
│               │   ├── Doctor.java
│               │   ├── Appointment.java
│               │   ├── Specialization.java
│               │   └── AppointmentStatus.java
│               │
│               ├── repository/
│               │   ├── PatientRepository.java
│               │   ├── DoctorRepository.java
│               │   └── AppointmentRepository.java
│               │
│               ├── service/
│               │   ├── PatientService.java
│               │   ├── DoctorService.java
│               │   └── AppointmentService.java
│               │
│               ├── exception/
│               │   ├── PatientNotFoundException.java
│               │   ├── DoctorNotFoundException.java
│               │   ├── AppointmentNotFoundException.java
│               │   └── SlotAlreadyBookedException.java
│               │
│               ├── util/
│               │   ├── InputUtil.java
│               │   ├── ValidationUtil.java
│               │   ├── IdGenerator.java
│               │   └── DisplayUtil.java
│               │
│               └── ui/
│                   ├── MainMenu.java
│                   ├── PatientMenu.java
│                   ├── DoctorMenu.java
│                   └── AppointmentMenu.java
│
└── README.md
```

## Appointment Booking Rule

A doctor cannot have more than one appointment for the same:

```text
Doctor + Date + Time
```

For example:

```text
Doctor ID : 2001
Date      : 12-09-2026
Time      : 10:00
```

Once this slot is booked, another patient cannot book the same slot with the same doctor.

However, another doctor can have an appointment at the same time.

## Appointment Status

Appointments can have the following statuses:

```text
BOOKED
CANCELLED
COMPLETED
```

Cancelled appointments are retained as part of the appointment history while their time slot becomes available for a new booking.

## Current Data Storage

The current application stores data in memory using Java collections.

```text
Map<Integer, Patient>
Map<Integer, Doctor>
Map<String, Appointment>
```

Because the application currently uses in-memory storage, data will be lost when the application is restarted.

A future version can replace the repository implementation with a database such as MySQL without changing the overall application architecture.

## Future Enhancements

Planned improvements include:

* Advanced patient and doctor validation
* Duplicate patient/doctor detection
* Stronger appointment validation
* Patient double-booking prevention
* Improved appointment history
* Doctor availability management
* Soft deletion
* Appointment completion workflow
* MySQL database integration
* JDBC integration
* REST APIs using Spring Boot
* Authentication and authorization
* Web-based frontend
* Deployment and cloud integration

## Purpose

This project is built to demonstrate how a real-world Java application can be structured using clean separation of responsibilities instead of placing all business logic inside a single class.

It also serves as a practical project for understanding how core Java concepts can be applied to an actual business use case.
