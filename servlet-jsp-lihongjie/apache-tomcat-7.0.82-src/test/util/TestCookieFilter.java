/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package util;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.authenticator.Constants;

public class TestCookieFilter {

    @Test
    public void test01() {
        // Single cookie
        Assert.assertEquals("a=b", CookieFilter.filter("a=b", null));
    }

    @Test
    public void test02() {
        // Two cookies
        Assert.assertEquals("a=b;c=d", CookieFilter.filter("a=b;c=d", null));
    }

    @Test
    public void test03() {
        // Cookies with leading and trailing whitespace
        Assert.assertEquals(" a=b  ;   c=d    ",
                CookieFilter.filter(" a=b  ;   c=d    ", null));
    }

    @Test
    public void test04() {
        // Empty name (not necessarily valid but checking edge cases in filter)
        Assert.assertEquals("=b", CookieFilter.filter("=b", null));
    }

    @Test
    public void test05() {
        // Empty value (not necessarily valid but checking edge cases in filter)
        Assert.assertEquals("a=", CookieFilter.filter("a=", null));
    }

    @Test
    public void test06() {
        // Simple case
        Assert.assertEquals("JSESSIONID=[obfuscated]",
                CookieFilter.filter("JSESSIONID=0123456789", null));
    }

    @Test
    public void test07() {
        // Simple SSO case
        Assert.assertEquals(Constants.SINGLE_SIGN_ON_COOKIE + "=[obfuscated]",
                CookieFilter.filter(Constants.SINGLE_SIGN_ON_COOKIE + "=0123456789", null));
    }


    @Test
    public void test08() {
        // Simple case
        String id = "0123456789";
        String cookie = "JSESSIONID=" + id;
        Assert.assertEquals(cookie, CookieFilter.filter(cookie, id));
    }

    @Test
    public void test09() {
        // Simple SSO case
        String id = "0123456789";
        String cookie = Constants.SINGLE_SIGN_ON_COOKIE + "=" + id;
        Assert.assertEquals(cookie, CookieFilter.filter(cookie, id));
    }

    @Test
    public void test10() {
        // Single cookie
        Assert.assertEquals("a=\"xx;x\"", CookieFilter.filter("a=\"xx;x\"", null));
    }
}
