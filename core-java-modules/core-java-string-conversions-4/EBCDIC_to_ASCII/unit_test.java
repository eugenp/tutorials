import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EBCDICConversionTest {

    public static void main(String[] args) throws Exception {
        testBasicEBCDICToAsciiExample();
        testStepByStepConversion();
        testFileBasedConversion();
        testStreamingConversion();
        System.out.println("✅ All tests passed!");
    }

    static void assertEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: [" + expected + "], but got: [" + actual + "]");
        }
    }

    static void testBasicEBCDICToAsciiExample() {
        // Example: EBCDIC bytes for "ABC" (in Cp037)
        byte[] ebcdicBytes = new byte[] { (byte) 0xC1, (byte) 0xC2, (byte) 0xC3 };
        String text = new String(ebcdicBytes, Charset.forName("Cp037"));
        System.out.println("Decoded text: " + text);
        assertEquals("ABC", text);
    }

    static void testStepByStepConversion() {
        // Example EBCDIC bytes ("Hello" in Cp037)
        byte[] ebcdicData = { (byte) 0xC8, (byte) 0x85, (byte) 0x93, (byte) 0x93, (byte) 0x96 };
        String unicodeText = new String(ebcdicData, Charset.forName("Cp037"));
        assertEquals("Hello", unicodeText);

        byte[] asciiData = unicodeText.getBytes(StandardCharsets.US_ASCII);
        assertEquals("Hello", new String(asciiData, StandardCharsets.US_ASCII));

        System.out.println("Step-by-step conversion OK: " + new String(asciiData));
    }

    static void testFileBasedConversion() throws Exception {
        File tempFile = File.createTempFile("input", ".ebc");
        tempFile.deleteOnExit();

        // "TEST" in Cp037 (all uppercase)
        byte[] ebcdicData = { (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3 };
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(ebcdicData);
        }

        byte[] rawBytes = new FileInputStream(tempFile).readAllBytes();
        String unicodeText = new String(rawBytes, Charset.forName("Cp037"));
        assertEquals("TEST", unicodeText);

        byte[] asciiData = unicodeText.getBytes(StandardCharsets.US_ASCII);
        assertEquals("TEST", new String(asciiData, StandardCharsets.US_ASCII));

        System.out.println("File-based conversion OK: " + unicodeText);
    }

    static void testStreamingConversion() throws IOException {
        File input = File.createTempFile("input", ".ebc");
        File output = File.createTempFile("output", ".txt");
        input.deleteOnExit();
        output.deleteOnExit();

        // "JAVA" in Cp037 (all uppercase)
        byte[] ebcdicData = { (byte) 0xD1, (byte) 0xC1, (byte) 0xE5, (byte) 0xC1 };
        try (FileOutputStream fos = new FileOutputStream(input)) {
            fos.write(ebcdicData);
        }

        try (
            InputStreamReader reader = new InputStreamReader(
                new FileInputStream(input),
                Charset.forName("Cp037")
            );
            OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(output),
                StandardCharsets.US_ASCII
            )
        ) {
            char[] buffer = new char[1024];
            int length;
            while ((length = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, length);
            }
        }

        String result = new String(
            new FileInputStream(output).readAllBytes(),
            StandardCharsets.US_ASCII
        );
        assertEquals("JAVA", result);

        System.out.println("Streaming conversion OK: " + result);
    }
}
