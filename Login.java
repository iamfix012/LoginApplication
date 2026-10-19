// PROG5121POE - Login Application Logic
package loginapplication;

// Verified compatibility with Part 2 messaging integration
public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;

    // Checks if username contains an underscore and is <= 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks password criteria: >= 8 chars, 1 capital letter, 1 number, 1 special character
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasDigit && hasSpecial;
    }

// Validates international cell phone number (+27 format)
    // Reference: Java Pattern class regex syntax (Oracle Docs)
  
public boolean checkCellPhoneNumber(String cellNumber) {
    // Regex: starts with +27 followed by 1 to 9 digits (max 12 characters total)
    String regex = "^\\+27\\d{1,9}$";
    return cellNumber != null && cellNumber.matches(regex);
}



    // Helper methods to set user details after validation
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Stores successfully validated credentials and returns success message
    public String registerUser(String username, String password, String cellNumber) {
        this.registeredUsername = username;
        this.registeredPassword = password;
        return "Username and password successfully captured.";
    }

    // Checks if entered login details match registered credentials
    public boolean loginUser(String username, String password) {
        if (this.registeredUsername == null || this.registeredPassword == null) {
            return false;
        }
        return this.registeredUsername.equals(username) && this.registeredPassword.equals(password);
    }

    // Returns formatted login output message
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
