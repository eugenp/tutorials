import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QueueInboundChannel implements InboundChannel {

    TechnologyProcessor technologyProcessor = new TechnologyProcessor();

    public void sendData(InputStream input) {
        List<String> data = new ArrayList<String>(); // adapt data from input stream to an appropriate format
        technologyProcessor.process(data);
    }
}
