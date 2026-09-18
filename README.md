# LoginApplication - PROG5121 Part 1

**Student Name:** Ovayo Mxalisa  
**Student Number:** ST10501503  
**Module:** Programming (PROG5121)

## Project Overview
This Java application handles user registration and authentication using basic OOP principles, Scanner input validation with retry loops, and JUnit 4 unit testing.

Features
- Username Check: Ensures username contains an underscore and is <= 5 characters.
- Password Complexity:* Validates length (8+), uppercase, digits, and special characters.
- Cell Phone Check: Validates South African international format (+27).
- Interactive Console UI: Uses Scanner and while loops to reprompt on invalid entries.
- Automated Tests: Includes JUnit 4 unit tests for core validation logic.


## Project Structure
```text
LoginApplication/
│
├── src/
│   └── loginapp/
│       ├── Login.java      # Core business logic and validation rules
│       └── Main.java       # User interface with Scanner input and retry loops
│
├── test/
│   └── loginapp/
│       └── LoginTest.java  # JUnit 4 unit tests
│
├── nbproject/             # NetBeans configuration files
├── build.xml              # Ant build script
└── README.md              # Project documentation
