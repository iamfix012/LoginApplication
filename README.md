# LoginApplication - PROG5121 POE

**Student Name:** Ovayo Mxalisa
**Student Number:** ST10501503
**Module:** Programming (PROG5121)

## Project Overview
This Java application manages user authentication and messaging through an interactive console interface built using object-oriented programming (OOP) principles, regex validation, file IO, and automated JUnit 4 unit tests[cite: 32, 41, 45].

### Features
* **Username Check:** Ensures username contains an underscore and is <= 5 characters[cite: 32].
* **Password Complexity:** Validates length (8+), uppercase, digits, and special characters[cite: 32].
* **Cell Phone Check:** Validates South African international format (+27)[cite: 33].
* **Interactive Console UI:** Uses Scanner and while loops for application navigation[cite: 41].
* **Automated Tests:** Includes comprehensive JUnit 4 test suites for core logic[cite: 34, 45].

## Part 2 - QuickChat Messaging System
* **Message ID & Hash Generation:** Autogenerates unique 10-digit IDs and uppercase message hashes (e.g., `00:0:HITHANKS`)[cite: 38].
* **Message Length & Recipient Validation:** Restricts text input to 250 characters and enforces international cell number formatting[cite: 38].
* **JSON File Storage:** Serializes stored message details directly to `.json` files using Java `FileWriter`[cite: 39].
* **Interactive Menu System:** Provides options to send, disregard, or store messages with numerical selection[cite: 37, 39].

## Project Structure
```text
LoginApplication/
│
├── src/
│   └── loginapplication/
│       ├── Login.java          # User authentication logic and credential validation
│       ├── Message.java        # Message data model, hash creation, and JSON export
│       └── Main.java           # Entry point managing registration, login, and QuickChat menu
│
└── test/
    └── loginapplication/
        ├── LoginTest.java      # JUnit tests for user registration and login status
        └── MessageTest.java    # JUnit tests for message length, recipient regex, and hash generation
