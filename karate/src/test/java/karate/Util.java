package karate;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;

public class Util {

    public static String read(String name) {
        try {
            InputStream is = Util.class.getClassLoader().getResourceAsStream(name);
            return IOUtils.toString(is, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
