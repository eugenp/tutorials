package com.baeldung.jhipster.gateway.security.oauth2;

import com.baeldung.jhipster.gateway.config.oauth2.OAuth2Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Tests helper functions around OAuth2 Cookies.
 *
 * @see OAuth2CookieHelper
 */
public class OAuth2CookieHelperTest {
    public static final String GET_COOKIE_DOMAIN_METHOD = "getCookieDomain";
    private OAuth2Properties oAuth2Properties;
    private OAuth2CookieHelper cookieHelper;

    @Before
    public void setUp() throws NoSuchMethodException {
        oAuth2Properties = new OAuth2Properties();
        cookieHelper = new OAuth2CookieHelper(oAuth2Properties);
    }

    @Test
    public void testLocalhostDomain() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertNull(name);
    }

    @Test
    public void testComDomain() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("test.com");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertNull(name);        //already top-level domain
    }

    @Test
    public void testWwwDomainCom() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.test.com");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertNull(name);
    }

    @Test
    public void testComSubDomain() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("abc.test.com");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertEquals(".test.com", name);
    }

    @Test
    public void testWwwSubDomainCom() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.abc.test.com");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertEquals(".test.com", name);
    }


    @Test
    public void testCoUkDomain() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("test.co.uk");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertNull(name);            //already top-level domain
    }

    @Test
    public void testCoUkSubDomain() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("abc.test.co.uk");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertEquals(".test.co.uk", name);
    }

    @Test
    public void testNestedDomain() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("abc.xyu.test.co.uk");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertEquals(".test.co.uk", name);
    }

    @Test
    public void testIpAddress() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("127.0.0.1");
        String name = ReflectionTestUtils.invokeMethod(cookieHelper, GET_COOKIE_DOMAIN_METHOD, request);
        Assert.assertNull(name);
    }
}
