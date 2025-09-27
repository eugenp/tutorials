import java.io.*;
import java.nio.charset.*;

public class EbcdicStreamConverter {

    public static void main(String[] args) {
        try (
            InputStreamReader reader = new InputStreamReader(
                new FileInputStream("input.ebc"), 
                Charset.forName("Cp037")
            );
            OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream("output.txt"), 
                StandardCharsets.US_ASCII
            )
        ) {
            char[] buffer = new char[1024];
            int length;

            while ((length = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, length);
            }

            System.out.println("Conversion complete! See output.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
