import org.junit.Test;

import java.io.File;

/**
 * Created by zhongjing on 12/10/16.
 */
public class TestMain {
    @Test
    public void Main() {
        File f = new File("data.db");
        if (f.exists()) {
            f.delete();
        }
        Main.main(new String[] {"arg1"});
    }
}
