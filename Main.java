package loginapplication;
/**
 * Version 1.2 - Added complete message validation and JSON serialization logic.
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login userLogin = new Login();

        System.out.println("=== USER REGISTRATION ===");
        System.out.print("Enter First Name: ");
        userLogin.setFirstName(input.nextLine());

        System.out.print("Enter Last Name: ");
        userLogin.setLastName(input.nextLine());

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

        // --- LOGIN PHASE ---[cite: 3]
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

        // --- QUICKCHAT APPLICATION MENU ---[cite: 3]
        System.out.println("\nWelcome to QuickChat.");
        boolean exitApp = false;

        while (!exitApp) {
            System.out.println("\nPlease select an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter option (1-3): ");

            int option = input.nextInt();
            input.nextLine(); // Clear buffer

            switch (option) {
                case 1:
                    System.out.print("How many messages would you like to send? ");
                    int numMessages = input.nextInt();
                    input.nextLine(); // Clear buffer

                    int totalSentCount = 0;

                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\n--- Message " + i + " of " + numMessages + " ---");

                        // Recipient input validation
                        Message msg = new Message();
                        System.out.print("Enter Recipient Cell Number (e.g. +27718693002): ");
                        String recipient = input.nextLine();
                        while (!msg.checkRecipientCell(recipient).equals("Cell phone number successfully captured.")) {
                            System.out.println(msg.checkRecipientCell(recipient));
                            System.out.print("Please re-enter Recipient Cell Number: ");
                            recipient = input.nextLine();
                        }

                        // Message text input validation
                        System.out.print("Enter Message (max 250 chars): ");
                        String text = input.nextLine();
                        while (!msg.checkMessageLength(text).equals("Message ready to send.")) {
                            System.out.println(msg.checkMessageLength(text));
                            System.out.print("Please re-enter Message: ");
                            text = input.nextLine();
                        }

                        // Construct message object
                        Message activeMsg = new Message(i, recipient, text);

                        System.out.println("\nChoose an action for this message:");
                        System.out.println("1: Send Message");
                        System.out.println("2: Disregard Message");
                        System.out.println("3: Store Message to send later");
                        System.out.print("Select choice (1-3): ");
                        int choice = input.nextInt();
                        input.nextLine(); // Clear buffer

                        String statusResponse = activeMsg.SentMessage(choice);
                        System.out.println("\n" + statusResponse);

                        if (choice == 1) {
                            totalSentCount++;
                        } else if (choice == 3) {
                            activeMsg.storeMessage();
                        }

                        // Display formatted summary[cite: 3]
                        System.out.println("\n" + activeMsg.printMessages());
                    }

                    System.out.println("\nTotal messages sent in this session: " + totalSentCount);
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println("Exiting QuickChat. Goodbye!");
                    exitApp = true;
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 3.");
            }
        }

        input.close();
    }
}
