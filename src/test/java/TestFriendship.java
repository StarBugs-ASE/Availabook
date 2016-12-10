import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by zhongjing on 12/9/16.
 */
public class TestFriendship {
    @org.junit.Test
    public void UserCanSetFriendship() {
        Friendship tester = new Friendship(1, 2);
        tester.setFriendship(1, 2);
        int a = tester.getUserID1();
        int b = tester.getUserID2();
        assertEquals("User should be able to set friendship", 1, a);
        assertEquals("User should be able to set friendship", 2, b);
    }

    @org.junit.Test
    public void CanDetectIsFriendOrNot() {
        Friendship tester = new Friendship(1, 2);
        int a = 1;
        int b = 2;
        tester.setFriendship(a, b);
        boolean c = tester.isFriendOrNot(a, b);
        assertTrue("a and b should be friends", c);
    }
}
