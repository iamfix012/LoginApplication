package loginapplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Part 3 Manager class handling parallel message arrays, JSON file loading,
 * searches, hash-based deletion, and report generation.
 * Student: Ovayo Mxalisa (ST10501503)
 */
public class MessageManager {
    // Required Part 3 Arrays
    private String[] sentMessages = new String[0];
    private String[] disregardedMessages = new String[0];
    private String[] storedMessages = new String[0];
    private String[] messageHashes = new String[0];
    private String[] messageIDs = new String[0];

    // Parallel tracking arrays for full message records
    private String[] allMessages = new String[0];
    private String[] recipients = new String[0];
    private String[] senders = new String[0];
    private String[] flags = new String[0];

    // Helper method to dynamically append to a standard Java String array
    private String[] appendToArray(String[] original, String newItem) {
        String[] updated = new String[original.length + 1];
        System.arraycopy(original, 0, updated, 0, original.length);
        updated[original.length] = newItem;
        return updated;
    }

    // Helper method to remove an element at a specific index from a String array
    private String[] removeFromArrayAt(String[] original, int indexToRemove) {
        if (indexToRemove < 0 || indexToRemove >= original.length) {
            return original;
        }
        String[] updated = new String[original.length - 1];
        int destPos = 0;
        for (int i = 0; i < original.length; i++) {
            if (i != indexToRemove) {
                updated[destPos++] = original[i];
            }
        }
        return updated;
    }

    // Helper method to remove the first matching string value from a category array
    private String[] removeValueFromArray(String[] original, String value) {
        for (int i = 0; i < original.length; i++) {
            if (original[i] != null && original[i].equals(value)) {
                return removeFromArrayAt(original, i);
            }
        }
        return original;
    }

    /**
     * Dynamically populates all parallel arrays based on message flag ("Sent", "Stored", "Disregard").
     */
    public void addMessageRecord(String sender, String recipient, String messageText,
                                 String messageID, String messageHash, String flag) {
        this.allMessages = appendToArray(this.allMessages, messageText);
        this.recipients = appendToArray(this.recipients, recipient);
        this.senders = appendToArray(this.senders, sender);
        this.messageIDs = appendToArray(this.messageIDs, messageID);
        this.messageHashes = appendToArray(this.messageHashes, messageHash);
        this.flags = appendToArray(this.flags, flag);

        if ("Sent".equalsIgnoreCase(flag)) {
            this.sentMessages = appendToArray(this.sentMessages, messageText);
        } else if ("Stored".equalsIgnoreCase(flag)) {
            this.storedMessages = appendToArray(this.storedMessages, messageText);
        } else if ("Disregard".equalsIgnoreCase(flag)) {
            this.disregardedMessages = appendToArray(this.disregardedMessages, messageText);
        }
    }

    /**
     * Reads stored JSON files (message_*.json) from disk into the storedMessages array.
     *
     * Reference / Attribution:
     * Adapted from Oracle Java I/O Documentation for BufferedReader and File directory listing.
     * Oracle (2024) Class BufferedReader - Java Platform SE 8.
     * Available at: https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
     */
    public String loadStoredMessagesFromJSON(String defaultSender) {
        File currentDir = new File(".");
        File[] jsonFiles = currentDir.listFiles((dir, name) -> name.startsWith("message_") && name.endsWith(".json"));

        if (jsonFiles == null || jsonFiles.length == 0) {
            return "No stored JSON message files found in directory.";
        }

        int loadedCount = 0;
        StringBuilder output = new StringBuilder("Loaded Stored Messages from JSON:\n");

        for (File file : jsonFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                String id = "";
                String recipient = "";
                String text = "";
                String hash = "";

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("\"messageID\":")) {
                        id = extractJsonValue(line);
                    } else if (line.startsWith("\"recipient\":")) {
                        recipient = extractJsonValue(line);
                    } else if (line.startsWith("\"messageText\":")) {
                        text = extractJsonValue(line);
                    } else if (line.startsWith("\"messageHash\":")) {
                        hash = extractJsonValue(line);
                    }
                }

                if (!text.isEmpty() && !containsValue(this.messageIDs, id)) {
                    addMessageRecord(defaultSender, recipient, text, id, hash, "Stored");
                    loadedCount++;
                    output.append("- [ID: ").append(id).append("] To: ").append(recipient)
                          .append(" | Message: ").append(text).append("\n");
                }
            } catch (IOException e) {
                return "Error reading JSON file " + file.getName() + ": " + e.getMessage();
            }
        }

        if (loadedCount == 0) {
            return "All JSON files are already loaded into the Stored Messages array.";
        }
        return output.toString().trim();
    }

    private String extractJsonValue(String jsonLine) {
        int colonIndex = jsonLine.indexOf(":");
        String val = jsonLine.substring(colonIndex + 1).trim();
        if (val.endsWith(",")) {
            val = val.substring(0, val.length() - 1).trim();
        }
        if (val.startsWith("\"") && val.endsWith("\"") && val.length() >= 2) {
            val = val.substring(1, val.length() - 1);
        }
        return val.replace("\\\"", "\"");
    }

    private boolean containsValue(String[] array, String target) {
        for (String item : array) {
            if (item != null && item.equals(target)) {
                return true;
            }
        }
        return false;
    }

    // 2a. Display the sender and recipient of all sent/stored messages
    public String displaySendersAndRecipients() {
        if (allMessages.length == 0) {
            return "No messages available.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allMessages.length; i++) {
            if ("Sent".equalsIgnoreCase(flags[i]) || "Stored".equalsIgnoreCase(flags[i])) {
                sb.append("Sender: ").append(senders[i])
                  .append(" | Recipient: ").append(recipients[i])
                  .append(" (").append(flags[i]).append(")\n");
            }
        }
        return sb.length() > 0 ? sb.toString().trim() : "No sent or stored messages found.";
    }

    // 2b. Display the longest message across active messages
    public String getLongestMessage() {
        String longest = "";
        for (int i = 0; i < allMessages.length; i++) {
            if (allMessages[i] != null && allMessages[i].length() > longest.length()) {
                longest = allMessages[i];
            }
        }
        return longest;
    }

    // 2c. Search for a Message ID and return the corresponding message text
    public String searchByMessageID(String searchID) {
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(searchID)) {
                return allMessages[i];
            }
        }
        return "Message ID not found.";
    }

    // 2c (Menu Display Helper). Search by Message ID and return Recipient + Message
    public String searchMessageDetailsByID(String searchID) {
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(searchID)) {
                return "Recipient: " + recipients[i] + "\nMessage: " + allMessages[i];
            }
        }
        return "Message ID not found.";
    }

    // 2d. Search for all messages sent or stored for a particular recipient
    public String[] searchByRecipient(String targetRecipient) {
        String[] matches = new String[0];
        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i] != null && recipients[i].equals(targetRecipient)
                    && ("Sent".equalsIgnoreCase(flags[i]) || "Stored".equalsIgnoreCase(flags[i]))) {
                matches = appendToArray(matches, allMessages[i]);
            }
        }
        return matches;
    }

    // 2e. Delete a message using its Message Hash
    public String deleteMessageByHash(String targetHash) {
        for (int i = 0; i < messageHashes.length; i++) {
            if (messageHashes[i] != null && messageHashes[i].equalsIgnoreCase(targetHash)) {
                String deletedText = allMessages[i];
                String deletedFlag = flags[i];

                // Remove from category array
                if ("Sent".equalsIgnoreCase(deletedFlag)) {
                    this.sentMessages = removeValueFromArray(this.sentMessages, deletedText);
                } else if ("Stored".equalsIgnoreCase(deletedFlag)) {
                    this.storedMessages = removeValueFromArray(this.storedMessages, deletedText);
                } else if ("Disregard".equalsIgnoreCase(deletedFlag)) {
                    this.disregardedMessages = removeValueFromArray(this.disregardedMessages, deletedText);
                }

                // Remove from parallel arrays
                this.allMessages = removeFromArrayAt(this.allMessages, i);
                this.recipients = removeFromArrayAt(this.recipients, i);
                this.senders = removeFromArrayAt(this.senders, i);
                this.messageIDs = removeFromArrayAt(this.messageIDs, i);
                this.messageHashes = removeFromArrayAt(this.messageHashes, i);
                this.flags = removeFromArrayAt(this.flags, i);

                String cleanText = deletedText.endsWith(".")
                        ? deletedText.substring(0, deletedText.length() - 1)
                        : deletedText;
                return "Message \"" + cleanText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // 2f. Display full report listing all sent messages (Message Hash, Recipient, Message)
    public String displaySentMessagesReport() {
        StringBuilder report = new StringBuilder("=== SENT MESSAGES REPORT ===\n");
        boolean found = false;
        for (int i = 0; i < allMessages.length; i++) {
            if ("Sent".equalsIgnoreCase(flags[i])) {
                found = true;
                report.append("Message Hash: ").append(messageHashes[i]).append("\n")
                      .append("Recipient: ").append(recipients[i]).append("\n")
                      .append("Message: ").append(allMessages[i]).append("\n")
                      .append("----------------------------\n");
            }
        }
        if (!found) {
            return "No sent messages to display in report.";
        }
        return report.toString().trim();
    }

    // Array Getters for Unit Testing & Verification
    public String[] getSentMessages() { return sentMessages; }
    public String[] getDisregardedMessages() { return disregardedMessages; }
    public String[] getStoredMessages() { return storedMessages; }
    public String[] getMessageHashes() { return messageHashes; }
    public String[] getMessageIDs() { return messageIDs; }
}
