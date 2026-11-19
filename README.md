# LoginApplication — QuickChat Messaging System (PROG5121 PoE)

**Student Name:** Ovayo Mxalisa  
**Student Number:** ST10501503  
**Module:** PROG5121 — Programming 1A (Portfolio of Evidence)  
**Repository:** [https://github.com/iamfix012/LoginApplication](https://github.com/iamfix012/LoginApplication)

---

## Project Overview

**LoginApplication** is a Java console-based registration, authentication, and messaging application (**QuickChat**) built iteratively across Parts 1, 2, and 3 of the PROG5121 Portfolio of Evidence. It validates user credentials, manages message creation and JSON persistence, and uses parallel arrays to store, search, delete, and report on message records.

---

## Key Features by Part

### Part 1: User Registration & Authentication (`Login.java`)
* **Username Validation:** Ensures usernames contain an underscore (`_`) and are no more than 5 characters long.
* **Password Complexity:** Enforces at least 8 characters, including at least one uppercase letter, one numeric digit, and one special character.
* **Cell Phone Validation:** Uses regular expressions (`^\+27\d{1,9}$`) to verify South African international phone formatting.
* **Login Verification:** Authenticates registered credentials and displays personalized welcome or error feedback.

### Part 2: Messaging, Hashing & JSON Persistence (`Message.java`)
* **Strict Message ID Validation:** Generates a random 10-digit ID verified via strict numeric regex (`^\d{10}$`).
* **Recipient & Length Checks:** Validates recipient numbers (`+27` format) and enforces a 250-character message ceiling with exact excess character reporting.
* **Automated Message Hashing:** Generates uppercase hashes formatted as `<First2DigitsOfID>:<MessageNumber>:<FIRSTWORD><LASTWORD>` (e.g., `00:0:HITONIGHT`).
* **Message Action Workflow (`sentMessage`):** Supports sending (`1`), disregarding and deleting message state (`2`), or storing messages to disk as `message_<ID>.json` files (`3`).

### Part 3 (Final PoE): Parallel Arrays, JSON Loading & Task Reports (`MessageManager.java`, `Main.java`)
* **Dynamic Parallel Arrays:** Populates and maintains synchronized arrays for **Sent Messages**, **Disregarded Messages**, **Stored Messages**, **Message Hashes**, and **Message IDs**.
* **JSON File Reader:** Reads persisted `message_*.json` files from disk back into the `storedMessages` array.
* **Stored Messages & Task Reports Menu (Option 4):**
  * `a)` Display sender and recipient of all sent/stored messages.
  * `b)` Display the longest message across active records.
  * `c)` Search for a message by its 10-digit **Message ID**.
  * `d)` Search for all sent or stored messages associated with a specific **Recipient** number.
  * `e)` Delete a message record using its **Message Hash**.
  * `f)` Display a complete **Sent Messages Report** (Message Hash, Recipient, and Message).
  * `g)` Load stored `.json` files into memory.

---

## Repository Structure

* `Login.java` — User registration, input validation, and authentication logic.
* `Message.java` — Message validation, hash generation, action selection, and JSON file writing.
* `MessageManager.java` — Part 3 parallel array management, JSON file reader, search operations, hash-based deletion, and report generation.
* `Main.java` — Console user interface integrating Registration, Login, and the 4-option QuickChat menu.
* `LoginTest.java` — JUnit 4 unit tests for Part 1 registration and login validation.
* `MessageTest.java` — JUnit 4 unit tests covering Part 2 messaging logic and all Part 3 array, search, deletion, and reporting test cases.
* `.github/workflows/TestJava.yml` — GitHub Actions Continuous Integration workflow that automatically compiles the project and executes JUnit tests on every push and pull request.

---

## Running the Application & Unit Tests

1. **Run Application:** Open the project in Apache NetBeans (Java with Ant) and run `Main.java` (`F6`).
2. **Run Unit Tests Locally:** Right-click the `LoginApplication` project in NetBeans and select **Test** (`Alt + F6`) to execute `LoginTest` and `MessageTest`.
3. **Automated CI Testing:** Every push to `main` or `feature/part3` triggers `.github/workflows/TestJava.yml` under the **Actions** tab to verify a 100% pass rate.
