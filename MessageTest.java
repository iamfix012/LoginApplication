package loginapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Complete JUnit 4 Test Suite covering Part 2 and Part 3 (Final PoE) requirements
public class MessageTest {

    private MessageManager manager;

    @Before
    public void setUp() {
        manager = new MessageManager();

        // Populate Test Data Messages 1 - 5 from PROG5121 PoE Brief
        // Message 1: Sent
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        manager.addMessageRecord("Developer", "+27834557896", "Did you get the cake?",
                m1.getMessageID(), m1.createMessageHash(), "Sent");

        // Message 2: Stored
        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.setMessageID("1122334455");
        manager.addMessageRecord("Developer", "+27838884567", "Where are you? You are late! I have asked you to be on time.",
                m2.getMessageID(), m2.createMessageHash(), "Stored");

        // Message 3: Disregard
        Message m3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        manager.addMessageRecord("Developer", "+27834484567", "Yohoooo, I am at your gate.",
                m3.getMessageID(), m3.createMessageHash(), "Disregard");

        // Message 4: Sent (Developer / ID: 0838884567)
        Message m4 = new Message(4, "+27838884567", "It is dinner time!");
        m4.setMessageID("0838884567");
        manager.addMessageRecord("0838884567", "+27838884567", "It is dinner time!",
                "0838884567", m4.createMessageHash(), "Sent");

        // Message 5: Stored
        Message m5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        manager.addMessageRecord("Developer", "+27838884567", "Ok, I am leaving without you.",
                m5.getMessageID(), m5.createMessageHash(), "Stored");
    }

    // ==================== PART 2 UNIT TESTS ====================

    @Test
    public void testCheckMessageLengthSuccess() {
        Message msg = new Message();
        assertEquals("Message ready to send.", msg.checkMessageLength("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    public void testCheckMessageLengthFailure() {
        Message msg = new Message();
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("A");
        }
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                msg.checkMessageLength(longText.toString()));
    }

    @Test
    public void testCheckRecipientCellSuccess() {
        Message msg = new Message();
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testCheckRecipientCellFailure() {
        Message msg = new Message();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell("08575975889"));
    }

    @Test
    public void testCreateMessageHashTestCase1() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        msg.setMessageID("0012345678");
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testSentMessageOptions() {
        Message msg = new Message(1, "+27718693002", "Test message");
        assertEquals("Message successfully sent.", msg.sentMessage(1));
        assertEquals("Message disregarded and deleted successfully.", msg.sentMessage(2));
        assertNull(msg.getMessageText());
        assertEquals("Message successfully stored.", msg.sentMessage(3));
    }

    @Test
    public void testCheckMessageID() {
        Message validMsg = new Message(0, "+27718693002", "Hello World");
        assertTrue(validMsg.checkMessageID());

        Message invalidMsg = new Message();
        invalidMsg.setMessageID("12345");
        assertFalse(invalidMsg.checkMessageID());
    }

    // ==================== PART 3 UNIT TESTS ====================

    // 1. Sent Messages array correctly populated
    @Test
    public void testSentMessagesArrayPopulated() {
        String[] expectedSent = {"Did you get the cake?", "It is dinner time!"};
        assertArrayEquals(expectedSent, manager.getSentMessages());
    }

    // 2. Display the longest Message
    @Test
    public void testGetLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, manager.getLongestMessage());
    }

    // 3. Search for messageID (Test Data: Message 4 -> "0838884567")
    @Test
    public void testSearchByMessageID() {
        assertEquals("It is dinner time!", manager.searchByMessageID("0838884567"));
    }

    // 4. Search all messages sent or stored regarding a particular recipient (+27838884567)
    @Test
    public void testSearchByRecipient() {
        String[] results = manager.searchByRecipient("+27838884567");
        assertTrue(contains(results, "Where are you? You are late! I have asked you to be on time."));
        assertTrue(contains(results, "Ok, I am leaving without you."));
    }

    // 5. Delete a message using a message hash (Test Message 2)
    @Test
    public void testDeleteMessageByHash() {
        // Message 2 hash with ID "1122334455" and number 2 -> "11:2:WHERETIME"
        String response = manager.deleteMessageByHash("11:2:WHERETIME");
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time\" successfully deleted.", response);
    }

    // 6. Display Report showing all sent messages (Message Hash, Recipient, Message)
    @Test
    public void testDisplayReport() {
        String report = manager.displaySentMessagesReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
        assertTrue(report.contains("Message: It is dinner time!"));
    }

    private boolean contains(String[] array, String target) {
        for (String item : array) {
            if (item != null && item.equals(target)) {
                return true;
            }
        }
        return false;
    }
}
