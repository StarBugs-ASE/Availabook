import static org.junit.Assert.assertTrue;

/**
 * Created by zhongjing on 12/9/16.
 */
public class TestUser {

    @org.junit.Test
    public void UserCanBeCreated() {
        User tester = new User("Kobe", "QWEasd123!", "kobe@gmail.com");
        tester.setUser("Kobe", "QWEasd123!", "kobe@gmail.com");
        String str1 = tester.getName();
        String str2 = tester.getPasswd();
        String str3 = tester.getEmail();
        assertTrue("User should be created", (!str1.isEmpty())&&(!str2.isEmpty())&&(!str3.isEmpty()));
    }
}
