/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.valves;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.junit.Test;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

public class TestCrawlerSessionManagerValve {

    @Test
    public void testCrawlerIpsPositive() throws Exception {
        CrawlerSessionManagerValve valve = new CrawlerSessionManagerValve();
        valve.setCrawlerIps("216\\.58\\.206\\.174");
        valve.setNext(EasyMock.createMock(Valve.class));
        HttpSession session = createSessionExpectations(valve, true);
        Request request = createRequestExpectations("216.58.206.174", session, true);

        EasyMock.replay(request, session);

        valve.invoke(request, EasyMock.createMock(Response.class));

        EasyMock.verify(request, session);
    }

    @Test
    public void testCrawlerIpsNegative() throws Exception {
        CrawlerSessionManagerValve valve = new CrawlerSessionManagerValve();
        valve.setCrawlerIps("216\\.58\\.206\\.174");
        valve.setNext(EasyMock.createMock(Valve.class));
        HttpSession session = createSessionExpectations(valve, false);
        Request request = createRequestExpectations("127.0.0.1", session, false);

        EasyMock.replay(request, session);

        valve.invoke(request, EasyMock.createMock(Response.class));

        EasyMock.verify(request, session);
    }

    private HttpSession createSessionExpectations(CrawlerSessionManagerValve valve, boolean isBot) {
        HttpSession session = EasyMock.createMock(HttpSession.class);
        if (isBot) {
            EasyMock.expect(session.getId()).andReturn("id").times(2);
            session.setAttribute(valve.getClass().getName(), valve);
            EasyMock.expectLastCall();
            session.setMaxInactiveInterval(60);
            EasyMock.expectLastCall();
        }
        return session;
    }

    private Request createRequestExpectations(String ip, HttpSession session, boolean isBot) {
        Request request = EasyMock.createMock(Request.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn(ip);
        IExpectationSetters<HttpSession> setter = EasyMock.expect(request.getSession(false))
                .andReturn(null);
        if (isBot) {
            setter.andReturn(session);
        }
        EasyMock.expect(request.getHeaders("user-agent"))
                .andReturn(Collections.enumeration(Collections.<String>emptyList()));
        return request;
    }
}
