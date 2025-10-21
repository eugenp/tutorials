import java.nio.charset.Charset; 

class Main {
    public static void main(String[] args) {
        // Example: EBCDIC bytes for "ABC" (in Cp037)
        byte[] ebcdicBytes = new byte[] { (byte)0xC1, (byte)0xC2, (byte)0xC3 };

        // Convert to String using EBCDIC Cp037 charset
        String text = new String(ebcdicBytes, Charset.forName("Cp037"));
        System.out.println(text);
    }
}