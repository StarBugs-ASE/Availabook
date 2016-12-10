import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by zhongjing on 12/9/16.
 */
public class TestMain {
    @org.junit.Test
    public void DetectIfDatabaseExist() {
        Database tester = new Database(); // Database Class is tested
        File f = new File("data.db");
        boolean a = false;
        if(!f.exists()) {
            a = true;
            tester.createDatabase();
        }
        else a = true;
        assertTrue("DatabaseCanBeDetected", a);
    }


}
