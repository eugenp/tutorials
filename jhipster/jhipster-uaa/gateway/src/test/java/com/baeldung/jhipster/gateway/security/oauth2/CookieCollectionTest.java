package com.baeldung.jhipster.gateway.security.oauth2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Test the CookieCollection.
 *
 * @see CookieCollection
 */
public class CookieCollectionTest {
    public static final String COOKIE_NAME = "chocolate";
    public static final String COOKIE_VALUE = "yummy";
    public static final String BROWNIE_NAME = "brownie";
    private Cookie cookie;
    private Cookie cupsCookie;
    private Cookie brownieCookie;

    @Before
    public void setUp() throws Exception {
        cookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        cupsCookie = new Cookie("cups", "delicious");
        brownieCookie = new Cookie(BROWNIE_NAME, "mmh");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void size() throws Exception {
        CookieCollection cookies = new CookieCollection();
        Assert.assertEquals(0, cookies.size());
        cookies.add(cookie);
        Assert.assertEquals(1, cookies.size());
    }

    @Test
    public void isEmpty() throws Exception {
        CookieCollection cookies = new CookieCollection();
        Assert.assertTrue(cookies.isEmpty());
        cookies.add(cookie);
        Assert.assertFalse(cookies.isEmpty());
    }

    @Test
    public void contains() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie);
        Assert.assertTrue(cookies.contains(cookie));
        Assert.assertTrue(cookies.contains(COOKIE_NAME));
        Assert.assertFalse(cookies.contains("yuck"));
    }

    @Test
    public void iterator() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie);
        Iterator<Cookie> it = cookies.iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(cookie, it.next());
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void toArray() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie);
        Cookie[] array = cookies.toArray();
        Assert.assertEquals(cookies.size(), array.length);
        Assert.assertEquals(cookie, array[0]);
    }

    @Test
    public void toArray1() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie);
        Cookie[] array = new Cookie[cookies.size()];
        cookies.toArray(array);
        Assert.assertEquals(cookies.size(), array.length);
        Assert.assertEquals(cookie, array[0]);
    }

    @Test
    public void add() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie);
        Cookie newCookie = new Cookie(BROWNIE_NAME, "mmh");
        cookies.add(newCookie);
        Assert.assertEquals(2, cookies.size());
        Assert.assertTrue(cookies.contains(newCookie));
        Assert.assertTrue(cookies.contains(BROWNIE_NAME));
    }

    @Test
    public void addAgain() {
        CookieCollection cookies = new CookieCollection(cookie, brownieCookie, cupsCookie);
        Cookie white = new Cookie(COOKIE_NAME, "white");
        boolean modified = cookies.add(white);
        Assert.assertTrue(modified);
        Assert.assertEquals(white, cookies.get(COOKIE_NAME));
        Assert.assertTrue(cookies.contains(white));
        Assert.assertFalse(cookies.contains(cookie));
        Assert.assertTrue(cookies.contains(COOKIE_NAME));
    }

    @Test
    public void get() {
        CookieCollection cookies = new CookieCollection(cookie, brownieCookie, cupsCookie);
        Cookie c = cookies.get(COOKIE_NAME);
        Assert.assertNotNull(c);
        Assert.assertEquals(cookie, c);
    }

    @Test
    public void remove() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie, brownieCookie, cupsCookie);
        cookies.remove(cookie);
        Assert.assertEquals(2, cookies.size());
        Assert.assertFalse(cookies.contains(cookie));
        Assert.assertFalse(cookies.contains(COOKIE_NAME));
        Assert.assertTrue(cookies.contains(brownieCookie));
        Assert.assertTrue(cookies.contains(BROWNIE_NAME));
    }

    @Test
    public void containsAll() throws Exception {
        List<Cookie> content = Arrays.asList(cookie, brownieCookie);
        CookieCollection cookies = new CookieCollection(content);
        Assert.assertTrue(cookies.containsAll(content));
        Assert.assertTrue(cookies.containsAll(Collections.singletonList(cookie)));
        Assert.assertFalse(cookies.containsAll(Arrays.asList(cookie, brownieCookie, cupsCookie)));
        Assert.assertTrue(cookies.containsAll(Arrays.asList(COOKIE_NAME, BROWNIE_NAME)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addAll() throws Exception {
        CookieCollection cookies = new CookieCollection();
        List<Cookie> content = Arrays.asList(cookie, brownieCookie, cupsCookie);
        boolean modified = cookies.addAll(content);
        Assert.assertTrue(modified);
        Assert.assertEquals(3, cookies.size());
        Assert.assertTrue(cookies.containsAll(content));
        Assert.assertFalse(cookies.addAll(Collections.EMPTY_LIST));
    }

    @Test
    public void removeAll() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie, brownieCookie, cupsCookie);
        boolean modified = cookies.removeAll(Arrays.asList(brownieCookie, cupsCookie));
        Assert.assertTrue(modified);
        Assert.assertEquals(1, cookies.size());
        Assert.assertFalse(cookies.contains(brownieCookie));
        Assert.assertFalse(cookies.contains(cupsCookie));
        Assert.assertFalse(cookies.removeAll(Collections.EMPTY_LIST));
    }

    @Test
    public void removeAllByName() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie, brownieCookie, cupsCookie);
        boolean modified = cookies.removeAll(Arrays.asList(COOKIE_NAME, BROWNIE_NAME));
        Assert.assertTrue(modified);
        Assert.assertEquals(1, cookies.size());
        Assert.assertFalse(cookies.contains(brownieCookie));
        Assert.assertFalse(cookies.contains(cookie));
        Assert.assertFalse(cookies.removeAll(Collections.EMPTY_LIST));
    }

    @Test
    public void retainAll() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie, brownieCookie, cupsCookie);
        List<Cookie> content = Arrays.asList(cookie, brownieCookie);
        boolean modified = cookies.retainAll(content);
        Assert.assertTrue(modified);
        Assert.assertEquals(2, cookies.size());
        Assert.assertTrue(cookies.containsAll(content));
        Assert.assertFalse(cookies.contains(cupsCookie));
        Assert.assertFalse(cookies.retainAll(content));
    }

    @Test
    public void clear() throws Exception {
        CookieCollection cookies = new CookieCollection(cookie);
        cookies.clear();
        Assert.assertTrue(cookies.isEmpty());
    }
}
