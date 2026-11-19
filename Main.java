package loginapplication;

import java.util.Scanner;

/**
 * Main QuickChat application integrating User Registration/Login (Part 1),
 * Message Sending & JSON Persistence (Part 2), and Array Reporting (Part 3).
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login userLogin = new Login();
        MessageManager manager = new MessageManager();

        // --- PART 1: USER REGISTRATION ---
        System.out.println("=== USER REGISTRATION ===");
        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();
        userLogin.setFirstName(firstName);

        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();
        userLogin.setLastName(lastName);

        System.out.print("Enter Username: ");
        String username = input.nextLine();
        while (!userLogin.checkUserName(username)) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            System.out.print("Please try entering your Username again: ");
            username = input.nextLine();
        }

        System.out.print("Enter Password: ");
        String password = input.nextLine();
        while (!userLogin.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            System.out.print("Please try entering your Password again: ");
            password = input.nextLine();
        }

        System.out.print("Enter Cell Phone Number: ");
        String cellNumber = input.nextLine();
        while (!userLogin.checkCellPhoneNumber(cellNumber)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.print("Please correct the number and try again: ");
            cellNumber = input.nextLine();
        }

        userLogin.registerUser(username, password, cellNumber);
        System.out.println("\nRegistration Completed Successfully!\n");

        // --- PART 1: USER LOGIN ---
        System.out.println("=== USER LOGIN ===");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter Login Username: ");
            String loginUser = input.nextLine();
            System.out.print("Enter Login Password: ");
            String loginPass = input.nextLine();

            if (userLogin.loginUser(loginUser, loginPass)) {
                System.out.println(userLogin.returnLoginStatus(loginUser, loginPass));
                loggedIn = true;
            } else {
                System.out.println("Username or password incorrect, please try again.\n");
            }
        }

        String currentSender = firstName + " " + lastName;
        int sessionMessageCounter = 0;

        // --- PART 2 & 3: QUICKCHAT MAIN MENU ---
        System.out.println("\nWelcome to QuickChat.");
        boolean exitApp = false;
        while (!exitApp) {
            System.out.println("\nPlease select an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages & Task Reports");
            System.out.print("Enter option (1-4): ");

            int option = Integer.parseInt(input.nextLine().trim());

            switch (option) {
                case 1:
                    System.out.print("How many messages would you like to send? ");
                    int numMessages = Integer.parseInt(input.nextLine().trim());
                    int totalSentCount = 0;

                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\n--- Message " + i + " of " + numMessages + " ---");

                        Message validator = new Message();
                        System.out.print("Enter Recipient Cell Number (e.g. +27718693002): ");
                        String recipient = input.nextLine();
                        while (!validator.checkRecipientCell(recipient).equals("Cell phone number successfully captured.")) {
                            System.out.println(validator.checkRecipientCell(recipient));
                            System.out.print("Please re-enter Recipient Cell Number: ");
                            recipient = input.nextLine();
                        }

                        System.out.print("Enter Message (max 250 chars): ");
                        String text = input.nextLine();
                        while (!validator.checkMessageLength(text).equals("Message ready to send.")) {
                            System.out.println(validator.checkMessageLength(text));
                            System.out.print("Please re-enter Message: ");
                            text = input.nextLine();
                        }

                        sessionMessageCounter++;
                        Message activeMsg = new Message(sessionMessageCounter, recipient, text);
                        String capturedID = activeMsg.getMessageID();
                        String capturedHash = activeMsg.getMessageHash();

                        System.out.println("\nChoose an action for this message:");
                        System.out.println("1: Send Message");
                        System.out.println("2: Disregard Message");
                        System.out.println("3: Store Message to send later");
                        System.out.print("Select choice (1-3): ");
                        int choice = Integer.parseInt(input.nextLine().trim());

                        String statusResponse = activeMsg.sentMessage(choice);
                        System.out.println("\n" + statusResponse);

                        if (choice == 1) {
                            totalSentCount++;
                            manager.addMessageRecord(currentSender, recipient, text, capturedID, capturedHash, "Sent");
                        } else if (choice == 2) {
                            manager.addMessageRecord(currentSender, recipient, text, capturedID, capturedHash, "Disregard");
                        } else if (choice == 3) {
                            activeMsg.storeMessage();
                            manager.addMessageRecord(currentSender, recipient, text, capturedID, capturedHash, "Stored");
                        }

                        System.out.println("\n" + activeMsg.printMessages());
                    }
                    System.out.println("\nTotal messages sent in this session: " + totalSentCount);
                    break;

                case 2:
                    System.out.println("\n" + manager.displaySentMessagesReport());
                    break;

                case 3:
                    System.out.println("Exiting QuickChat. Goodbye!");
                    exitApp = true;
                    break;

                case 4:
                    handleStoredMessagesMenu(input, manager, currentSender);
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 4.");
            }
        }
        input.close();
    }

    // Sub-menu for Part 3 Array Operations & JSON Loading
    private static void handleStoredMessagesMenu(Scanner input, MessageManager manager, String currentSender) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== STORED MESSAGES & REPORTS ===");
            System.out.println("a) Display sender and recipient of all messages");
            System.out.println("b) Display the longest message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search all messages for a particular recipient");
            System.out.println("e) Delete a message using a message hash");
            System.out.println("f) Display full report of all sent messages");
            System.out.println("g) Load stored JSON files into array");
            System.out.println("x) Return to Main Menu");
            System.out.print("Select sub-option: ");
            String subChoice = input.nextLine().trim().toLowerCase();

            switch (subChoice) {
                case "a":
                    System.out.println("\n" + manager.displaySendersAndRecipients());
                    break;
                case "b":
                    String longest = manager.getLongestMessage();
                    System.out.println("\nLongest Message: " + (longest.isEmpty() ? "No messages available." : longest));
                    break;
                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String searchID = input.nextLine().trim();
                    System.out.println("\n" + manager.searchMessageDetailsByID(searchID));
                    break;
                case "d":
                    System.out.print("Enter Recipient Cell Number (e.g. +27838884567): ");
                    String targetRecipient = input.nextLine().trim();
                    String[] matches = manager.searchByRecipient(targetRecipient);
                    if (matches.length == 0) {
                        System.out.println("No messages found for recipient: " + targetRecipient);
                    } else {
                        System.out.println("\nMessages for " + targetRecipient + ":");
                        for (String m : matches) {
                            System.out.println("- " + m);
                        }
                    }
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String hashToDelete = input.nextLine().trim();
                    System.out.println("\n" + manager.deleteMessageByHash(hashToDelete));
                    break;
                case "f":
                    System.out.println("\n" + manager.displaySentMessagesReport());
                    break;
                case "g":
                    System.out.println("\n" + manager.loadStoredMessagesFromJSON(currentSender));
                    break;
                case "x":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }
}
