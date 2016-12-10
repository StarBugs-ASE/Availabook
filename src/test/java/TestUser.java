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

    @org.junit.Test
    public void UserCanSetAvailatime() {
        Availatime tester1 = new Availatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        tester1.setAvailatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        User tester = new User("Kobe", "QWEasd123!", "kobe@gmail.com");
        tester.setAvailatime(tester1);
        String str1 = tester1.getDate();
        String str2 = tester1.getStartTime();
        String str3 = tester1.getEndTime();
        String str4 = tester1.getTendency();
        String str5 = tester1.getUserName();
        assertTrue("User should be able to set availatime", (!str1.isEmpty())&&(!str2.isEmpty())&&(!str3.isEmpty())&&(!str4.isEmpty())&&(!str5.isEmpty()));
    }

    @org.junit.Test
    public void UserCanDeleteAvailatime() {
        Availatime tester1 = new Availatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        tester1.setAvailatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        User tester = new User("Kobe", "QWEasd123!", "kobe@gmail.com");
        tester.deleteAvailatime(tester1);
        String str1 = tester1.getDate();
        String str2 = tester1.getStartTime();
        String str3 = tester1.getEndTime();
        String str4 = tester1.getTendency();
        String str5 = tester1.getUserName();
        assertTrue("User should be able to delete availatime", (!str1.isEmpty())&&(!str2.isEmpty())&&(!str3.isEmpty())&&(!str4.isEmpty())&&(!str5.isEmpty()));
    }
}
