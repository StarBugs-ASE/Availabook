/**
 * Created by Sophie on 12/8/16.
 */
import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class TestClass {


    @org.junit.Test
    public void generatedPasswordStoredInDatabaseShouldBeEncrpted() {
        Database tester = new Database(); // Database Class is tested

        // assert statements
        assertEquals("the generated password of 123 must be",
                "202cb962ac59075b964b07152d234b70",
                tester.encryptedPasswd("123"));

    }
}

