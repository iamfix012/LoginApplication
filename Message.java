package loginapplication;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Manages individual message properties, validation, hash generation, and JSON storage.
 * Student: Ovayo Mxalisa (ST10501503)
 */
public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Default constructor
    public Message() {
    }

    // Parameterized constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit Message ID
    public String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Lecturer Fix 1: Ensures Message ID is non-null and strictly 10 numeric digits
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.matches("^\\d{10}$");
    }

    // Validates cell phone number format (+27 followed by 1 to 9 digits)
    public String checkRecipientCell(String cellNumber) {
        String regex = "^\\+27\\d{1,9}$";
        if (cellNumber != null && cellNumber.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Validates message length (<= 250 characters)
    public String checkMessageLength(String text) {
        if (text == null || text.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // Generates message hash in format: 00:0:HITHANKS
    public String createMessageHash() {
        if (this.messageID == null || this.messageText == null || this.messageText.trim().isEmpty()) {
            return "";
        }
        String firstTwoDigits = this.messageID.substring(0, 2);
        String[] words = this.messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        String rawHash = firstTwoDigits + ":" + this.messageNumber + ":" + firstWord + lastWord;
        return rawHash.toUpperCase();
    }

    // Lecturer Fix 2 & 3: Standard camelCase naming & full disregard deletion workflow
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                return "Message successfully sent.";
            case 2:
                this.messageText = null;
                this.messageHash = null;
                return "Message disregarded and deleted successfully.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid choice selection.";
        }
    }

    // Displays full details in required sequence: Message ID, Message Hash, Recipient, Message
    public String printMessages() {
        return "Message Details:\n"
                + "Message ID: " + this.messageID + "\n"
                + "Message Hash: " + (this.messageHash != null ? this.messageHash : "[N/A - DELETED]") + "\n"
                + "Recipient: " + this.recipient + "\n"
                + "Message: " + (this.messageText != null ? this.messageText : "[DELETED]");
    }

    // Returns total count of messages
    public int returnTotalMessages(int count) {
        return count;
    }

    //Stores message details as a JSON file.
     
     
    public void storeMessage() {
        String safeText = this.messageText != null ? this.messageText.replace("\"", "\\\"") : "";
        String jsonString = "{\n"
                + "  \"messageID\": \"" + this.messageID + "\",\n"
                + "  \"messageNumber\": " + this.messageNumber + ",\n"
                + "  \"recipient\": \"" + this.recipient + "\",\n"
                + "  \"messageText\": \"" + safeText + "\",\n"
                + "  \"messageHash\": \"" + (this.messageHash != null ? this.messageHash : "") + "\"\n"
                + "}";
        try (FileWriter file = new FileWriter("message_" + this.messageID + ".json")) {
            file.write(jsonString);
            file.flush();
        } catch (IOException e) {
            System.out.println("Error storing JSON file: " + e.getMessage());
        }
    }

    // Getters and Setters
    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public int getMessageNumber() { return messageNumber; }
    public void setMessageNumber(int messageNumber) { this.messageNumber = messageNumber; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public String getMessageHash() { return messageHash; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }
}
