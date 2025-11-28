import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.Charset;

public class EbcdicExample {
    private static final Logger logger = LoggerFactory.getLogger(EbcdicExample.class);
    
    public static void main(String[] args) {
        // Example: EBCDIC bytes for "ABC" (in Cp037)
        byte[] ebcdicBytes = new byte[] { (byte)0xC1, (byte)0xC2, (byte)0xC3 };

        // Convert to String using EBCDIC Cp037 charset
        String text = new String(ebcdicBytes, Charset.forName("Cp037"));
        logger.info(text);
    }
}
