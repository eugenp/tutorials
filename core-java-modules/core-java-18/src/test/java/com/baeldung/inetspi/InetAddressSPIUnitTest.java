package com.baeldung.inetspi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class InetAddressSPIUnitTest {

    @Ignore("JAVA-43793")
    @Test
    public void givenInetAddress_whenUsingInetAddress_thenPerformResolution() throws UnknownHostException {
        InetAddressSPI spi = new InetAddressSPI();
        Assert.assertNotNull(spi.usingGetByName("www.google.com"));
        Assert.assertTrue(spi.usingGetAllByName("www.google.com").length > 1);
        Assert.assertNotNull(spi.usingGetByIp(InetAddress.getByName("www.google.com")
          .getAddress()));
        Assert.assertNotNull(spi.usingGetByIpAndReturnsCannonName(InetAddress.getByName("www.google.com")
          .getAddress()));
    }

    @Test
    public void givenCustomInetAddressImplementation_whenUsingInetAddress_thenPerformResolution() throws UnknownHostException {
        InetAddressSPI spi = new InetAddressSPI();
        Assert.assertEquals("baeldung-local.org", spi.getHostUsingCustomImpl(new byte[] { 1, 2, 3, 4 }));
        Stream<InetAddress> response = spi.getIpUsingCustomImpl("baeldung-local.org");
        Assert.assertArrayEquals(new byte[] { 1, 2, 3, 4 }, response.findFirst()
          .get()
          .getAddress());
    }
}
