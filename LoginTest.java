package loginapplication;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testCorrectUsernameFormat() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testIncorrectUsernameFormat() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testPasswordComplexitySuccess() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordComplexityFailure() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

  @Test
public void testCellPhoneSuccess() {
    Login login = new Login();
    // Valid test data specified in rubric[cite: 7]
    assertTrue(login.checkCellPhoneNumber("+27838968976"));
}

    @Test
    public void testCellPhoneFailure() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}
