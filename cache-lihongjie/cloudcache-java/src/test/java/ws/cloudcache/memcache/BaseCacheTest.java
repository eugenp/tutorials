package ws.cloudcache.memcache;

import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * User: treeder
 * Date: Nov 14, 2008
 * Time: 5:32:49 PM
 */
public class BaseCacheTest {
    private BigCache bigCache;

    public BaseCacheTest(BigCache cache) {

        bigCache = cache;
    }

    @Test
    public void testSet() throws Exception {
        String key = "x";
        String s = "This is my data for the cache.";
        bigCache.put(key, s, 3600);
        String ret = (String) bigCache.get(key);
        Assert.assertEquals(s, ret);
    }

    @Test
    public void testGetNotExists() throws Exception {
        String key = "this-key-does-not-exist";
        String ret = (String) bigCache.get(key);
        Assert.assertNull(ret);
    }

    @Test
    public void testRemoveNotExists() throws Exception {
        String key = "this-key-does-not-exist";
        bigCache.remove(key);
    }

    @Test
    public void testExpired() throws Exception {
        String key = "shortExpiry";
        String s = "This is my data for the cache.";
        bigCache.put(key, s, 1);
        Thread.sleep(2000);
        String ret = (String) bigCache.get(key);
        Assert.assertNull(ret);
    }

    @Test
    public void testGetBunch() throws Exception {
          for(int i = 0; i < 50; i++){
            SomeObject2 someObject2 = new SomeObject2("name" + i);
            bigCache.put("key" + i, someObject2, 3600);
        }
        long start = System.currentTimeMillis();
        for(int i = 0; i < 50; i++) {
            Object o = bigCache.get("key" + i);
            System.out.println(o);
        }
         System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void testGetAsync() throws Exception {
        for(int i = 0; i < 50; i++){
            SomeObject2 someObject2 = new SomeObject2("name" + i);
            bigCache.put("key" + i, someObject2, 3600);
        }
        long start = System.currentTimeMillis();
        List<Future> fromCache = new ArrayList<Future>();
        for(int i = 0; i < 50; i++){
            fromCache.add(bigCache.getAsync("key" + i));
        }
        for (Future future : fromCache) {
            System.out.println("future=" + future);
            Object o = future.get();
            System.out.println("future.get=" + o);
        }
        Thread.sleep(10000); // wait for gets to happen
        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }
}