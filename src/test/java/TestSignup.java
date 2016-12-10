import static org.junit.Assert.assertTrue;

/**
 * Created by zhongjing on 12/9/16.
 */
public class TestSignup {
    @org.junit.Test
    public void PasswordShouldHaveCorrectFormat() {
        String str1 = "QWEasd123.";
        String str2 = "123";
        assertTrue("Password Should Have Correct Format)", str1.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};’:”\\\\|,.<>\\/?]).{6,15}$"));
        assertTrue("Password Should Have Correct Format)", !str2.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};’:”\\\\|,.<>\\/?]).{6,15}$"));
    }

    @org.junit.Test
    public void UsernameShouldHaveCorrectFormat() {
        String str1 = "James";
        String str2 = "12jjj";
        assertTrue("Username Should Have Correct Format)", str1.matches("[a-z|A-Z]+"));
        assertTrue("Username Should Have Correct Format)", !str2.matches("[a-z|A-Z]+"));
    }

    @org.junit.Test
    public void EmailShouldHaveCorrectFormat() {
        String str1 = "xxx@gmail.com";
        String str2 = "xxxxxx";
        assertTrue("Email Should Have Correct Format)", str1.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"));
        assertTrue("Email Should Have Correct Format)", !str2.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"));
    }
}
