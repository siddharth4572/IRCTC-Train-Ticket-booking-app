# IRCTC Train Ticket Booking App

## Overview

The IRCTC Train Ticket Booking App is a Java-based console application that enables users to book train tickets, manage bookings, and search for available trains between different stations.

## Features

- **User Registration and Login**: Sign up with a username and password, secure them with hashed storage.
- **Train Search**: Find trains based on source and destination stations.
- **Seat Booking**: Book available seats on a selected train.
- **Booking Management**: View and cancel existing bookings.

## Technology Stack

- **Java 8**: The application is built using Java 8.
- **Gradle**: Build automation using Gradle.
- **JSON**: Data stored in JSON format for users and trains.
- **Jackson**: JSON processing with Jackson library.
- **BCrypt**: Password hashing with BCrypt for enhanced security.

## Project Structure

- **src/main/java/org/example/App.java**: Main entry point of the application.
- **src/main/java/org/example/entities**: Contains entity classes like `User`, `Train`, and `Ticket`.
- **src/main/java/org/example/services**: Business logic for managing users and trains.
- **src/main/java/org/example/util/UserServiceUtil.java**: Utility functions for password hashing and checking.
- **localDb**: Directory containing JSON files for storing users and trains data.

## Setup and Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/IRCTC-Train-Ticket-booking-app.git
   ```

2. Navigate to the project directory:

   ```bash
   cd IRCTC-Train-Ticket-booking-app
   ```

3. Build the project using Gradle:

   ```bash
   ./gradlew build
   ```

4. Run the application:

   ```bash
   ./gradlew run
   ```

## Usage

Upon running the application, a menu-driven interface will allow you to:

- Sign up and log in.
- Search for trains between specific stations.
- Book and cancel seats.
- View booked tickets.

## Author 

Siddharth Katyal

## Contributing

Feel free to contribute to this project by submitting issues and pull requests. Let's make this app better together!

## License

This project is licensed under the MIT License.

---

**Disclaimer**: This app is for educational purposes only and not affiliated with the official IRCTC.

