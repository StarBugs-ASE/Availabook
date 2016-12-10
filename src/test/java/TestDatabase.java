/**
 * Created by Sophie on 12/8/16.
 */

import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDatabase {

    @Test
    public void DatabaseMain() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Database.main(new String[] {"arg1"});
    }

    @Test
    public void generatedPasswordStoredInDatabaseShouldBeEncrpted() {
        Database tester = new Database(); // Database Class is tested
        // assert statements
        assertEquals("the generated password of 123 must be",
                "202cb962ac59075b964b07152d234b70",
                tester.encryptedPasswd("123"));
    }

    @Test
    public void DatabaseCanBeOpened() {
        Database tester = new Database(); // Database Class is tested
        assertTrue("Database should be created", tester.openDatabase());
    }

    @Test
    public void DatabaseCanDetectWrongEmail() {
        Database tester = new Database(); // Database Class is tested
        assertTrue("Database should not report correct email", tester.isValidEmailAddress("zj@gmail.com"));
        assertTrue("Database should be able to report wrong email", !tester.isValidEmailAddress("xxxx.com"));
    }

    @Test
    public void DatabaseCanDetectWrongName() {
        Database tester = new Database(); // Database Class is tested
        assertTrue("Database should not report correct name", tester.isValidName("zj"));
        assertTrue("Database should be able to report wrong name", !tester.isValidName(" "));
    }

    @Test
    public void DatabaseCanDetectWrongPassword() {
        Database tester = new Database(); // Database Class is tested
        assertTrue("Database should not report correct password", tester.isValidPasswd("QWEasd123."));
        assertTrue("Database should be able to report wrong password", !tester.isValidPasswd("123123123"));
    }

    @Test
    public void DatabaseCanDetectDuplicateName() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Database tester = new Database(); // Database Class is tested
        tester.noDuplicateUsername("zj");
        tester.noDuplicateUsername("xx");
        tester.noDuplicateUsername("  ");
        assertTrue("Database should not report different username", !tester.noDuplicateUsername("Qqqqqqqq"));
    }

    @Test
    public void DatabaseCanDetectDuplicateEmail() {
        Database tester = new Database(); // Database Class is tested
        assertTrue("Database should not report different email", !tester.noDuplicateemail("Qqq@gmail.com"));
    }

    @Test
    public void DatabaseCanBeCreated() {
        Database tester = new Database(); // Database Class is tested
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        boolean b = tester.createDatabase();
        assertTrue("Database can be created", b);
    }

    @Test
    public void DatabaseCanSaveSignUpInfo() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.signUp(c, "xx", "QWEasd123.", "xx@gmail.com");
        tester.signUp(c, " ", "QWEasd123.", "xx@gmail.com");
        tester.signUp(c, "xx", "123", "xx@gmail.com");
        tester.signUp(c, "xx", "QWEasd123.", "xx@");
        tester.signUp(c, "  ", " ", " ");
        tester.signUp(c, "  ", "123", "xx@gmail.com");
        tester.signUp(c, "xx", "123", "xx@");
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanSaveAvailatime() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.addAvailatime(c, "2017-1-1", "11:00am", "11:00pm", "Hiking", "Kobe");
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanSaveAddedFriends() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.addFriend(c, 1, 2);
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanDoIDQuery() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.IDQuery(c, "zj");
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanDoNameQuery() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.NameQuery(c, 1);
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanDoPasswordQuery() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.passwdQuery(c, "123");
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanDoAvailatimeQuery() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.availaTimeQuery(c);
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanDoFriendshipQuery() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.friendshipQuery(c);
        System.out.println("Test Finish.");
    }

    @Test
    public void DatabaseCanDeleteFriends() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.exit(0);
        }
        Database tester = new Database(); // Database Class is tested
        tester.deleteFriend(c, 1, 2);
        System.out.println("Test Finish.");
    }

}

