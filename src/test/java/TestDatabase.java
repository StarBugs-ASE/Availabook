/**
 * Created by Sophie on 12/8/16.
 */

import org.junit.Test;

import static org.junit.Assert.*;

public class TestDatabase {

    @Test
    public void generatedPasswordStoredInDatabaseShouldBeEncrpted() {
        Database tester = new Database(); // Database Class is tested
        // assert statements
        assertEquals("the generated password of 123 must be",
                "202cb962ac59075b964b07152d234b70",
                tester.encryptedPasswd("123"));
    }

    @Test
    public void DatabaseShouldExist() {
        Database tester = new Database(); // Database Class is tested
        assertNotNull("Database should not be null", tester);
    }

    @Test
    public void DatabaseCanBeOpened() {
        Database tester = new Database(); // Database Class is tested
        assertTrue("Database should be created", tester.openDatabase());
    }

}

