import com.baeldung.jaxws.introduction.HelloService;
import com.baeldung.jaxws.introduction.HelloServiceImpl;
import org.junit.Before;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eunice A. Obugyei on 09/02/2017.
 */
public class HelloServiceTest {

    private static QName SERVICE_NAME = new QName("http://introduction.jaxws.baeldung.com/", "HelloServiceImplService");

    private URL wsdlUrl;
    private Service service;
    private HelloService helloServiceProxy;
    private HelloServiceImpl helloServiceImpl;

    {
        try {
            wsdlUrl = new URL("http://localhost:8080/helloservice?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        service = Service.create(wsdlUrl, SERVICE_NAME);
    }


    @Before
    public void instantiateHelloService() {
        helloServiceImpl = new HelloServiceImpl();
        helloServiceProxy = service.getPort(HelloService.class);
    }

    @Test
    public void whenUsingGetGreetingMethod_thenCorrect() {
        final String endpointResponse = helloServiceProxy.getGreeting("HelloService");
        final String localResponse = helloServiceImpl.getGreeting("HelloService");
        assertEquals(localResponse, endpointResponse);
    }
}
