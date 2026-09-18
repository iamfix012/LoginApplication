package loginapplication;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Login userLogin = new Login();
            
            System.out.println("=== USER REGISTRATION ===");
            
            System.out.print("Enter First Name: ");
            userLogin.setFirstName(input.nextLine());
            
            System.out.print("Enter Last Name: ");
            userLogin.setLastName(input.nextLine());
            
            // Username Validation Loop 
            System.out.print("Enter Username: ");
            String username = input.nextLine();
            while (!userLogin.checkUserName(username)) {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
                System.out.print("Please try entering your Username again: ");
                username = input.nextLine();
            }
            System.out.println("Username successfully captured.");
            
            // Password Validation Loop
            System.out.print("Enter Password: ");
            String password = input.nextLine();
            while (!userLogin.checkPasswordComplexity(password)) {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                System.out.print("Please try entering your Password again: ");
                password = input.nextLine();
            }
            System.out.println("Password successfully captured.");
            
            // Cell Phone Number Validation Loop 
            System.out.print("Enter Cell Phone Number (e.g. +27838968976): ");
            String cellNumber = input.nextLine();
            while (!userLogin.checkCellPhoneNumber(cellNumber)) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
                System.out.print("Please correct the number and try again: ");
                cellNumber = input.nextLine();
            }
            System.out.println("Cell phone number successfully added.");
            
            // Complete registration once all individual inputs pass validation
            userLogin.registerUser(username, password, cellNumber);
            System.out.println("\nRegistration Completed Successfully!\n");
            
            // Login Section with Loop
            System.out.println("=== USER LOGIN ===");
            boolean loggedIn = false;
            
            while (!loggedIn) {
                System.out.print("Enter Login Username: ");
                String loginUser = input.nextLine();
                
                System.out.print("Enter Login Password: ");
                String loginPass = input.nextLine();
                
                String loginStatus = userLogin.returnLoginStatus(loginUser, loginPass);
                System.out.println(loginStatus);
                
                if (userLogin.loginUser(loginUser, loginPass)) {
                    loggedIn = true;
                } else {
                    System.out.println("Login failed. Please try again.\n");
                }
            }
        }
    }
}