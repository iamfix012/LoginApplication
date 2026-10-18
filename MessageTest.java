package loginapplication;

import org.junit.Test;
import static org.junit.Assert.*;


public class MessageTest {

    // 1. MESSAGE LENGTH TESTS 

    @Test
    public void testCheckMessageLengthSuccess() {
        Message msg = new Message();
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testCheckMessageLengthFailure() {
        Message msg = new Message();
        // Create string of 260 characters (10 excess)
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("A");
        }
        String result = msg.checkMessageLength(longText.toString());
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }

    // 2. RECIPIENT CELL FORMAT TESTS 

    @Test
    public void testCheckRecipientCellSuccess() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testCheckRecipientCellFailure() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    // 3. MESSAGE HASH GENERATION TESTS 

    @Test
    public void testCreateMessageHashTestCase1() {
        // Test Data 1: Message 0, Recipient +27718693002, Text "Hi Mike, can you join us for dinner tonight?"
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        msg.setMessageID("0012345678"); // Hardcode ID prefix '00' to match test specification

        String expectedHash = "00:0:HITONIGHT";
        String actualHash = msg.createMessageHash();
        assertEquals(expectedHash, actualHash);
    }

    // 4. SENT MESSAGE ACTION OPTIONS TESTS

    @Test
    public void testSentMessageOption1() {
        Message msg = new Message();
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    @Test
    public void testSentMessageOption2() {
        Message msg = new Message();
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }

    @Test
    public void testSentMessageOption3() {
        Message msg = new Message();
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }

    // 5. MESSAGE ID CHECK TEST

    @Test
    public void testCheckMessageID() {
        Message msg = new Message(0, "+27718693002", "Hello World");
        assertTrue(msg.checkMessageID());
        assertNotNull(msg.getMessageID());
        assertEquals(10, msg.getMessageID().length());
    }
}
