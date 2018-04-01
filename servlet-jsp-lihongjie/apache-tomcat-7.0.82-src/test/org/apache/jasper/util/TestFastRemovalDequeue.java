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
package org.apache.jasper.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class TestFastRemovalDequeue {

    @Test
    public void testSinglePushPop() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();

        q.push(o1);

        Object r = q.pop();

        assertEquals(o1, r);
        assertNull(q.first);
        assertNull(q.last);
    }


    @Test
    public void testDoublePushPop() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();
        Object o2 = new Object();

        q.push(o1);
        q.push(o2);

        assertEquals(o2, q.first.getContent());
        assertEquals(o1, q.last.getContent());

        Object r1 = q.pop();

        assertEquals(o1, r1);
        assertEquals(o2, q.first.getContent());
        assertEquals(o2, q.last.getContent());


        Object r2 = q.pop();
        assertEquals(o2, r2);
        assertNull(q.first);
        assertNull(q.last);
    }


    @Test
    public void testSingleUnpopPop() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();

        q.unpop(o1);

        Object r = q.pop();

        assertEquals(o1, r);
        assertNull(q.first);
        assertNull(q.last);
    }


    @Test
    public void testDoubleUnpopPop() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();
        Object o2 = new Object();

        q.unpop(o1);
        q.unpop(o2);

        assertEquals(o1, q.first.getContent());
        assertEquals(o2, q.last.getContent());

        Object r2 = q.pop();

        assertEquals(o2, r2);
        assertEquals(o1, q.first.getContent());
        assertEquals(o1, q.last.getContent());


        Object r1 = q.pop();
        assertEquals(o1, r1);
        assertNull(q.first);
        assertNull(q.last);
    }


    @Test
    public void testSinglePushUnpush() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();

        q.push(o1);

        Object r = q.unpush();

        assertEquals(o1, r);
        assertNull(q.first);
        assertNull(q.last);
    }


    @Test
    public void testDoublePushUnpush() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();
        Object o2 = new Object();

        q.push(o1);
        q.push(o2);

        assertEquals(o2, q.first.getContent());
        assertEquals(o1, q.last.getContent());

        Object r2 = q.unpush();

        assertEquals(o2, r2);
        assertEquals(o1, q.first.getContent());
        assertEquals(o1, q.last.getContent());


        Object r1 = q.unpush();
        assertEquals(o1, r1);
        assertNull(q.first);
        assertNull(q.last);
    }


    @Test
    public void testSinglePushRemove() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();

        FastRemovalDequeue<Object>.Entry e1 = q.push(o1);

        assertEquals(o1, e1.getContent());

        q.remove(e1);

        assertNull(q.first);
        assertNull(q.last);
    }


    @Test
    public void testDoublePushRemove() throws Exception {
        FastRemovalDequeue<Object> q = new FastRemovalDequeue<Object>(2);

        Object o1 = new Object();
        Object o2 = new Object();

        FastRemovalDequeue<Object>.Entry e1 = q.push(o1);
        FastRemovalDequeue<Object>.Entry e2 = q.push(o2);

        assertEquals(o1, e1.getContent());
        assertEquals(o2, e2.getContent());

        assertEquals(o2, q.first.getContent());
        assertEquals(o1, q.last.getContent());

        q.remove(e1);

        assertEquals(o2, q.first.getContent());
        assertEquals(o2, q.last.getContent());

        q.remove(e2);

        assertNull(q.first);
        assertNull(q.last);
    }
}
