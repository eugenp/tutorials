package com.spring.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.webflux.reactive.client.HighwayWebClient;

import reactor.core.Disposable;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive", webEnvironment = WebEnvironment.DEFINED_PORT)
public class HighwayApplicationTest  {
    
    
    @Test
    public void testVehiclesFlowingAll()
    {
        HighwayWebClient vehiclesWebClient = new HighwayWebClient();
        Disposable disposable = vehiclesWebClient.vehicleDetected();
        try {
            Thread.sleep(32000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }
    
    @Test
    public void testVehiclesFlowing120KmHigher()
    {
        HighwayWebClient vehiclesWebClient = new HighwayWebClient();
        Disposable disposable = vehiclesWebClient.vehicleHigherThen120Detected();
        try {
            Thread.sleep(32000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }
}
