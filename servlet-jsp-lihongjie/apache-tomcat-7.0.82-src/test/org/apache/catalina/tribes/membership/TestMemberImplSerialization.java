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
package org.apache.catalina.tribes.membership;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TestMemberImplSerialization {
    private MemberImpl m1, m2, p1,p2;
    private byte[] payload = null;
    private int udpPort = 3445;

    @Before
    public void setUp() throws Exception {
        payload = new byte[333];
        Arrays.fill(payload,(byte)1);
        m1 = new MemberImpl("localhost",3333,1,payload);
        m2 = new MemberImpl("localhost",3333,1);
        payload = new byte[333];
        Arrays.fill(payload,(byte)2);
        p1 = new MemberImpl("127.0.0.1",3333,1,payload);
        p2 = new MemberImpl("localhost",3331,1,payload);
        m1.setDomain(new byte[] {1,2,3,4,5,6,7,8,9});
        m2.setDomain(new byte[] {1,2,3,4,5,6,7,8,9});
        m1.setCommand(new byte[] {1,2,4,5,6,7,8,9});
        m2.setCommand(new byte[] {1,2,4,5,6,7,8,9});
        m1.setUdpPort(udpPort);
        m2.setUdpPort(m1.getUdpPort());
    }

    @Test
    public void testCompare() throws Exception {
        assertTrue(m1.equals(m2));
        assertTrue(m2.equals(m1));
        assertTrue(p1.equals(m2));
        assertFalse(m1.equals(p2));
        assertFalse(m1.equals(p2));
        assertFalse(m2.equals(p2));
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testUdpPort() throws Exception {
        byte[] md1 = m1.getData();
        byte[] md2 = m2.getData();

        MemberImpl a1 = MemberImpl.getMember(md1);
        MemberImpl a2 = MemberImpl.getMember(md2);

        assertTrue(a1.getUdpPort()==a2.getUdpPort());
        assertTrue(a1.getUdpPort()==udpPort);
    }

    @Test
    public void testSerializationOne() throws Exception {
        MemberImpl m = m1;
        byte[] md1 = m.getData(false,true);
        byte[] mda1 = m.getData(false,false);
        assertTrue(Arrays.equals(md1,mda1));
        assertTrue(md1==mda1);
        mda1 = m.getData(true,true);
        MemberImpl ma1 = MemberImpl.getMember(mda1);
        assertTrue(compareMembers(m,ma1));
        mda1 = p1.getData(false);
        assertFalse(Arrays.equals(md1,mda1));
        ma1 = MemberImpl.getMember(mda1);
        assertTrue(compareMembers(p1,ma1));

        md1 = m.getData(true,true);
        Thread.sleep(50);
        mda1 = m.getData(true,true);
        MemberImpl a1 = MemberImpl.getMember(md1);
        MemberImpl a2 = MemberImpl.getMember(mda1);
        assertTrue(a1.equals(a2));
        assertFalse(Arrays.equals(md1,mda1));


    }

    public boolean compareMembers(MemberImpl impl1, MemberImpl impl2) {
        boolean result = true;
        result = result && Arrays.equals(impl1.getHost(),impl2.getHost());
        result = result && Arrays.equals(impl1.getPayload(),impl2.getPayload());
        result = result && Arrays.equals(impl1.getUniqueId(),impl2.getUniqueId());
        result = result && impl1.getPort() == impl2.getPort();
        return result;
    }
}
