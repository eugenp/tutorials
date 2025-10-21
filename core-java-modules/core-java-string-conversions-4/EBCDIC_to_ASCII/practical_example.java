import java.io.*;
import java.nio.charset.*;

public class EbcdicToAsciiExample {

    public static void main(String[] args) throws Exception {
        // Step 1: Read raw EBCDIC bytes from file
        FileInputStream fis = new FileInputStream("input.ebc");
        byte[] ebcdicData = fis.readAllBytes();
        fis.close();

        // Step 2: Decode EBCDIC bytes to Unicode string
        String unicodeText = new String(ebcdicData, Charset.forName("Cp037"));

        // Step 3: Encode Unicode string to ASCII bytes
        byte[] asciiData = unicodeText.getBytes(StandardCharsets.US_ASCII);

        // Step 4: Print final ASCII string
        System.out.println(new String(asciiData, StandardCharsets.US_ASCII));
    }
}
