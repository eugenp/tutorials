import java.io.InputStream;

public interface InboundChannel {

    void sendData(InputStream input);
}
