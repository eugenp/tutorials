import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileConverter {
    private static final Logger logger = LoggerFactory.getLogger(FileConverter.class);
    
    public static void main(String[] args) throws Exception {
        // Step 1: Read raw EBCDIC bytes from file
        FileInputStream fis = new FileInputStream("input.ebc");
        byte[] ebcdicData = fis.readAllBytes();
        fis.close();

        // Step 2: Decode EBCDIC bytes to Unicode string
        String unicodeText = new String(ebcdicData, Charset.forName("Cp037"));

        // Step 3: Encode Unicode string to ASCII bytes
        byte[] asciiData = unicodeText.getBytes(StandardCharsets.US_ASCII);

        // Step 4: Log final ASCII string
        logger.info(new String(asciiData, StandardCharsets.US_ASCII));
    }
}
