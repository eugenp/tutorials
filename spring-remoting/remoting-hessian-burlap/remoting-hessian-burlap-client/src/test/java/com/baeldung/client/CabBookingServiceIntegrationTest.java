package com.baeldung.client;

import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import com.baeldung.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.Thread.sleep;

@SpringBootTest(classes = {BurlapClient.class, HessianClient.class})
@RunWith(SpringRunner.class)
public class CabBookingServiceIntegrationTest {

    static Logger log = LoggerFactory.getLogger(CabBookingServiceIntegrationTest.class);
    @Autowired @Qualifier("burlapInvoker") CabBookingService burlapClient;
    @Autowired @Qualifier("hessianInvoker")  CabBookingService hessianClient;
    static Thread serverThread;

    @BeforeClass
    public static void startServer() throws InterruptedException {
        serverThread = serverThread();
        log.info("Starting server.");
        serverThread.start();
        // increase this enough to let the server start
        sleep(6000);
    }

    @org.junit.Test
    public void bookACabWithBurlapClient() throws InterruptedException {
        bookACab(this.burlapClient);
    }

    @org.junit.Test
    public void bookACabWithHessianClient() throws InterruptedException {
        bookACab(this.hessianClient);
    }

    private void bookACab(CabBookingService burlapClient) {
        Booking booking;
        try {
            booking = burlapClient.bookRide("Duomo place");
            log.info("Booking success: {}", booking);
        } catch (BookingException e) {
            log.info("Booking failed: {}", e.getMessage());
        }
    }

    @AfterClass
    public static void stopServer() throws InterruptedException {
        serverThread.interrupt();
        serverThread.join();
        log.info("Server terminated.");
    }

    static Thread serverThread() {
        Thread serverThread = new Thread(()-> {
            log.info("Starting Burlap and Hessian server");
            Server.main(new String[]{});
            log.info("Burlap and Hessian server terminated");
        });
        serverThread.setDaemon(true);
        return serverThread;
    }

}
