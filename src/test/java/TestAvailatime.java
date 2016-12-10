import static org.junit.Assert.assertTrue;

/**
 * Created by zhongjing on 12/9/16.
 */
public class TestAvailatime {
    @org.junit.Test
    public void UserCanSetAvailatime() {
        Availatime tester = new Availatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        tester.setAvailatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        String str1 = tester.getDate();
        String str2 = tester.getStartTime();
        String str3 = tester.getEndTime();
        String str4 = tester.getTendency();
        String str5 = tester.getUserName();
        assertTrue("User should be able to set availatime", (!str1.isEmpty())&&(!str2.isEmpty())&&(!str3.isEmpty())&&(!str4.isEmpty())&&(!str5.isEmpty()));
    }

    @org.junit.Test
    public void AvailatimeShouldBeCorrect() {
        Availatime tester = new Availatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        tester.setAvailatime("2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        Boolean a = tester.isValidAvailatime();
        tester.setAvailatime("2016-1-1", "11:00pm", "11:00am", "Hiking", "Kobe");
        Boolean b = tester.isValidAvailatime();
        assertTrue("Availatime should be correct", a);
        assertTrue("Availatime should be correct", !b);
    }
}
