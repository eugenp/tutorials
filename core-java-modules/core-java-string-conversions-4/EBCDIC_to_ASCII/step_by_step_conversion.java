import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EbcdicToAsciiConverter {
    private static final Logger logger = LoggerFactory.getLogger(EbcdicToAsciiConverter.class);
    
    public static void main(String[] args) { 
        // Step 0: Example EBCDIC bytes ("HELLO" in Cp037) 
        byte[] ebcdicData = { (byte)0xC8, (byte)0x85, (byte)0x93, (byte)0x93, (byte)0x96 }; 
        // Step 1: Decode from EBCDIC (Cp037) to Unicode string 
        String unicodeText = new String(ebcdicData, Charset.forName("Cp037")); 
        // Step 2: Encode from Unicode string to ASCII bytes 
        byte[] asciiData = unicodeText.getBytes(StandardCharsets.US_ASCII); 
        // Step 3: Log final ASCII string 
        logger.info(new String(asciiData, StandardCharsets.US_ASCII)); 
    }
}
