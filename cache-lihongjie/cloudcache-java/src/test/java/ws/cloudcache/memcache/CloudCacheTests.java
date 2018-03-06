package ws.cloudcache.memcache;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: treeder
 * Date: Jan 13, 2009
 * Time: 7:34:33 PM
 */
public class CloudCacheTests extends BaseCacheTest {
    public static CloudCache cache;

    @BeforeClass
    public static void setupCache() throws IOException {
//        System.out.println("Setting up cache...");
        Properties props = new Properties();
        InputStream is = CloudCacheTests.class.getResourceAsStream("/cloudcache.properties");
        if (is == null) {
            throw new CloudCacheRuntimeException("No cloudcache.properties file found.");
        }
        props.load(is);
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        cache = new CloudCache(props.getProperty("accessKey"), props.getProperty("secretKey"), executorService);
    }

    @AfterClass
    public static void shutdown() {
        cache.shutdown();
    }

    public CloudCacheTests() {
        super(cache);
    }

    @Test
    public void testAuth() throws Exception {
        cache.auth();
    }

    @Test
    public void testBasicOps() throws Exception {
        String toPut = "I am a testing string. Take me apart and put me back together again.";
        cache.put("s1", toPut, 0);

        Thread.sleep(5000);

        String response = (String) cache.get("s1");
        Assert.assertEquals(toPut, response);

    }

    @Test
    public void testListKeys() throws Exception {
        List<String> keys = cache.listKeys();
//        Assert.assertEquals(toPut, response);
        System.out.println("PRINTING KEYS:");
        for (String key : keys) {
            System.out.println(key);
        }
    }

    @Test
    public void testCounters() throws Exception {
        long val = 0;
        String key = "counter1";
        cache.put(key, val, 50000);
        for (int i = 0; i < 10; i++) {
            val = cache.increment(key);
        }
        Assert.assertEquals(10L, val);

        for (int i = 0; i < 10; i++) {
            val = cache.decrement(key);
        }
        Assert.assertEquals(0L, val);

        // One more to make sure it stays at 0
        val = cache.decrement(key);
        Assert.assertEquals(0L, val);

    }

}
