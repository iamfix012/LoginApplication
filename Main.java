package loginapplication;

import java.util.Scanner;

/**
 * Main application entry point for QuickChat.
 * Handles user authentication verification and interactive menu loops.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login userLogin = new Login();

        // Step 1: User Registration
        System.out.println("=== USER REGISTRATION ===");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        userLogin.setFirstName(firstName);

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        userLogin.setLastName(lastName);

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Cell Phone (+27...): ");
        String cellNumber = scanner.nextLine();

        String regStatus = userLogin.registerUser(username, password, cellNumber);
        System.out.println(regStatus);

        if (!regStatus.equals("Username and password successfully captured.")) {
            System.out.println("Registration failed. Program terminating.");
            return;
        }

        // Step 2: User Login Authentication
        System.out.println("\n=== USER LOGIN ===");
        System.out.print("Enter Username: ");
        String loginUser = scanner.nextLine();

        System.out.print("Enter Password: ");
        String loginPass = scanner.nextLine();

        String loginStatus = userLogin.returnLoginStatus(loginUser, loginPass);
        System.out.println(loginStatus);

        if (!userLogin.loginUser(loginUser, loginPass)) {
            System.out.println("Login failed. Program terminating.");
            return;
        }

        // Step 3: QuickChat Interactive Menu System (Part 2)
        System.out.println("\nWelcome to QuickChat.");
        boolean running = true;
        int totalMessagesSent = 0;

        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume remaining newline

            switch (choice) {
                case 1:
                    System.out.print("How many messages would you like to enter? ");
                    int numMessages = scanner.nextInt();
                    scanner.nextLine(); // Consume remaining newline

                    for (int i = 0; i < numMessages; i++) {
                        System.out.println("\n--- Entering Message " + (i + 1) + " ---");

                        System.out.print("Enter Recipient Cell Number: ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter Message Text: ");
                        String text = scanner.nextLine();

                        Message msg = new Message(i, recipient, text);

                        // Validate Recipient Cell Format
                        String cellCheck = msg.checkRecipientCell(recipient);
                        System.out.println(cellCheck);

                        // Validate Message Length Limits
                        String lenCheck = msg.checkMessageLength(text);
                        System.out.println(lenCheck);

                        if (lenCheck.equals("Message ready to send.") && cellCheck.equals("Cell phone number successfully captured.")) {
                            System.out.println("Choose action: 1) Send Message  2) Disregard Message  3) Store Message to send later");
                            int action = scanner.nextInt();
                            scanner.nextLine();

                            String actionResult = msg.SentMessage(action);
                            System.out.println(actionResult);

                            if (action == 1) {
                                totalMessagesSent++;
                                System.out.println("\n" + msg.printMessages());
                            } else if (action == 3) {
                                msg.storeMessage();
                                System.out.println("Message stored as JSON.");
                            }
                        }
                    }

                    System.out.println("\nTotal messages sent in this session: " + totalMessagesSent);
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        
        scanner.close();
    }
}
