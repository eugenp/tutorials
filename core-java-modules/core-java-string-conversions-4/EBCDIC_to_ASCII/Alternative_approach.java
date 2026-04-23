import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StreamingConverter {
    private static final Logger logger = LoggerFactory.getLogger(StreamingConverter.class);
    
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

            logger.info("Conversion complete! See output.txt");

        } catch (IOException e) {
            logger.error("Error during conversion", e);
        }
    }
}
