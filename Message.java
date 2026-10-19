package loginapplication;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Version 1.2 - Added complete message validation and JSON serialization logic.
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

    // Ensures Message ID is not null and <= 10 characters
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Validates cell phone number format (+27 followed by up to 9 digits)
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
        if (text == null) {
            return "Message ready to send.";
        }
        if (text.length() <= 250) {
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

    // Option handler for user action selection
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
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
                + "Message Hash: " + this.messageHash + "\n"
                + "Recipient: " + this.recipient + "\n"
                + "Message: " + this.messageText;
    }

    // Returns total count of messages
    public int returnTotalMessages(int count) {
        return count;
    }

    /**
     * Stores message details as a JSON file.
     * 
     * Reference / Attribution:
     * Code adapted from Oracle Java I/O Documentation for JSON file handling.
     * Oracle (2024) Class FileWriter - Java Platform SE 8.
     * Available at: https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html
     */
    public void storeMessage() {
        String jsonString = "{\n"
                + "  \"messageID\": \"" + this.messageID + "\",\n"
                + "  \"messageNumber\": " + this.messageNumber + ",\n"
                + "  \"recipient\": \"" + this.recipient + "\",\n"
                + "  \"messageText\": \"" + this.messageText + "\",\n"
                + "  \"messageHash\": \"" + this.messageHash + "\"\n"
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
