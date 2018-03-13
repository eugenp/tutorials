/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.catalina.deploy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link WebXml} fragment ordering.
 */
public class TestWebXmlOrdering {
    private WebXml app;
    private WebXml a;
    private WebXml b;
    private WebXml c;
    private WebXml d;
    private WebXml e;
    private WebXml f;
    private Map<String,WebXml> fragments;
    private int posA;
    private int posB;
    private int posC;
    private int posD;
    private int posE;
    private int posF;

    @Before
    public void setUp() {
        app = new WebXml();
        a = new WebXml();
        a.setName("a");
        b = new WebXml();
        b.setName("b");
        c = new WebXml();
        c.setName("c");
        d = new WebXml();
        d.setName("d");
        e = new WebXml();
        e.setName("e");
        f = new WebXml();
        f.setName("f");
        // Control the input order
        fragments = new LinkedHashMap<String,WebXml>();
        fragments.put("a",a);
        fragments.put("b",b);
        fragments.put("c",c);
        fragments.put("d",d);
        fragments.put("e",e);
        fragments.put("f",f);
    }

    @Test
    public void testOrderWebFragmentsAbsolute() {
        app.addAbsoluteOrdering("c");
        app.addAbsoluteOrdering("a");
        app.addAbsoluteOrdering("b");
        app.addAbsoluteOrdering("e");
        app.addAbsoluteOrdering("d");

        Set<WebXml> ordered = WebXml.orderWebFragments(app, fragments, null);

        Iterator<WebXml> iter = ordered.iterator();
        assertEquals(c,iter.next());
        assertEquals(a,iter.next());
        assertEquals(b,iter.next());
        assertEquals(e,iter.next());
        assertEquals(d,iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testOrderWebFragmentsAbsolutePartial() {
        app.addAbsoluteOrdering("c");
        app.addAbsoluteOrdering("a");

        Set<WebXml> ordered = WebXml.orderWebFragments(app, fragments, null);

        Iterator<WebXml> iter = ordered.iterator();
        assertEquals(c,iter.next());
        assertEquals(a,iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testOrderWebFragmentsAbsoluteOthersStart() {
        app.addAbsoluteOrdering(WebXml.ORDER_OTHERS);
        app.addAbsoluteOrdering("b");
        app.addAbsoluteOrdering("d");

        Set<WebXml> others = new HashSet<WebXml>();
        others.add(a);
        others.add(c);
        others.add(e);
        others.add(f);

        Set<WebXml> ordered = WebXml.orderWebFragments(app, fragments, null);

        Iterator<WebXml> iter = ordered.iterator();
        while (others.size() > 0) {
            WebXml o = iter.next();
            assertTrue(others.contains(o));
            others.remove(o);
        }
        assertEquals(b,iter.next());
        assertEquals(d,iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testOrderWebFragmentsAbsoluteOthersMiddle() {
        app.addAbsoluteOrdering("b");
        app.addAbsoluteOrdering(WebXml.ORDER_OTHERS);
        app.addAbsoluteOrdering("d");

        Set<WebXml> others = new HashSet<WebXml>();
        others.add(a);
        others.add(c);
        others.add(e);
        others.add(f);

        Set<WebXml> ordered = WebXml.orderWebFragments(app, fragments, null);

        Iterator<WebXml> iter = ordered.iterator();
        assertEquals(b,iter.next());

        while (others.size() > 0) {
            WebXml o = iter.next();
            assertTrue(others.contains(o));
            others.remove(o);
        }
        assertEquals(d,iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testWebFragmentsAbsoluteWrongFragmentName() {
        app.addAbsoluteOrdering("a");
        app.addAbsoluteOrdering("z");
        Set<WebXml> ordered = WebXml.orderWebFragments(app, fragments, null);
        assertEquals(1,ordered.size());
        assertEquals(fragments.get("a"),ordered.toArray()[0]);
    }

    @Test
    public void testOrderWebFragmentsAbsoluteOthersEnd() {
        app.addAbsoluteOrdering("b");
        app.addAbsoluteOrdering("d");
        app.addAbsoluteOrdering(WebXml.ORDER_OTHERS);

        Set<WebXml> others = new HashSet<WebXml>();
        others.add(a);
        others.add(c);
        others.add(e);
        others.add(f);

        Set<WebXml> ordered = WebXml.orderWebFragments(app, fragments, null);

        Iterator<WebXml> iter = ordered.iterator();
        assertEquals(b,iter.next());
        assertEquals(d,iter.next());

        while (others.size() > 0) {
            WebXml o = iter.next();
            assertTrue(others.contains(o));
            others.remove(o);
        }
        assertFalse(iter.hasNext());
    }

    private void doRelativeOrderingTest(RelativeOrderingTestRunner runner) {
        // Confirm we have all 720 possible input orders
        // Set<String> orders = new HashSet<>();

        // Test all possible input orders since some bugs were discovered that
        // depended on input order
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 3; l++) {
                        for (int m = 0; m < 2; m++) {
                            setUp();
                            runner.init();
                            ArrayList<WebXml> source = new ArrayList<WebXml>();
                            source.addAll(fragments.values());
                            Map<String,WebXml> input =
                                    new LinkedHashMap<String,WebXml>();

                            WebXml one = source.remove(i);
                            input.put(one.getName(), one);

                            WebXml two = source.remove(j);
                            input.put(two.getName(), two);

                            WebXml three = source.remove(k);
                            input.put(three.getName(), three);

                            WebXml four = source.remove(l);
                            input.put(four.getName(), four);

                            WebXml five = source.remove(m);
                            input.put(five.getName(), five);

                            WebXml six = source.remove(0);
                            input.put(six.getName(), six);

                            /*
                            String order = one.getName() + two.getName() +
                                    three.getName() + four.getName() +
                                    five.getName() + six.getName();
                            orders.add(order);
                            */

                            Set<WebXml> ordered =
                                    WebXml.orderWebFragments(app, input, null);
                            populatePositions(ordered);

                            runner.validate(getOrder(ordered));
                        }
                    }
                }
            }
        }
        // System.out.println(orders.size());
    }

    private String getOrder(Set<WebXml> ordered) {
        StringBuilder sb = new StringBuilder(ordered.size());
        for (WebXml webXml : ordered) {
            sb.append(webXml.getName());
        }
        return sb.toString();
    }

    private void populatePositions(Set<WebXml> ordered) {
        List<WebXml> indexed = new ArrayList<WebXml>();
        indexed.addAll(ordered);

        posA = indexed.indexOf(a);
        posB = indexed.indexOf(b);
        posC = indexed.indexOf(c);
        posD = indexed.indexOf(d);
        posE = indexed.indexOf(e);
        posF = indexed.indexOf(f);
    }

    @Test
    public void testOrderWebFragmentsRelative1() {
        // First example from servlet spec
        doRelativeOrderingTest(new RelativeTestRunner1());
    }

    @Test
    public void testOrderWebFragmentsRelative2() {
        // Second example - use fragment a for no-id fragment
        doRelativeOrderingTest(new RelativeTestRunner2());
    }

    @Test
    public void testOrderWebFragmentsRelative3() {
        // Third example from spec with e & f added
        doRelativeOrderingTest(new RelativeTestRunner3());
    }

    @Test
    public void testOrderWebFragmentsRelative4Bug54068() {
        // Simple sequence that failed for some inputs
        doRelativeOrderingTest(new RelativeTestRunner4());
    }

    @Test
    public void testOrderWebFragmentsRelative5Bug54068() {
        // Simple sequence that failed for some inputs
        doRelativeOrderingTest(new RelativeTestRunner5());
    }

    @Test
    public void testOrderWebFragmentsRelative6Bug54068() {
        // Simple sequence that failed for some inputs
        doRelativeOrderingTest(new RelativeTestRunner6());
    }

    @Test
    public void testOrderWebFragmentsRelative7() {
        // Reference loop (but not circular dependencies)
        doRelativeOrderingTest(new RelativeTestRunner7());
    }

    @Test
    public void testOrderWebFragmentsRelative8() {
        // More complex, trying to break the algorithm
        doRelativeOrderingTest(new RelativeTestRunner8());
    }

    @Test
    public void testOrderWebFragmentsRelative9() {
        // Variation on bug 54068
        doRelativeOrderingTest(new RelativeTestRunner9());
    }

    @Test
    public void testOrderWebFragmentsRelative10() {
        // Variation on bug 54068
        doRelativeOrderingTest(new RelativeTestRunner10());
    }

    @Test
    public void testOrderWebFragmentsRelative11() {
        // Test references to non-existant fragments
        doRelativeOrderingTest(new RelativeTestRunner11());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testOrderWebFragmentsrelativeCircular1() {
        a.addBeforeOrdering("b");
        b.addBeforeOrdering("a");

        WebXml.orderWebFragments(app, fragments, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testOrderWebFragmentsrelativeCircular2() {
        a.addBeforeOrderingOthers();
        b.addAfterOrderingOthers();
        c.addBeforeOrdering("a");
        c.addAfterOrdering("b");

        WebXml.orderWebFragments(app, fragments, null);
    }

    private interface RelativeOrderingTestRunner {
        void init();
        void validate(String order);
    }

    private class RelativeTestRunner1 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            a.addAfterOrderingOthers();
            a.addAfterOrdering("c");
            b.addBeforeOrderingOthers();
            c.addAfterOrderingOthers();
            f.addBeforeOrderingOthers();
            f.addBeforeOrdering("b");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            //a.addAfterOrderingOthers();
            assertTrue(order, posA > posB);
            assertTrue(order, posA > posC);
            assertTrue(order, posA > posD);
            assertTrue(order, posA > posE);
            assertTrue(order, posA > posF);

            // a.addAfterOrdering("c");
            assertTrue(order, posA > posC);

            // b.addBeforeOrderingOthers();
            assertTrue(order, posB < posC);

            // c.addAfterOrderingOthers();
            assertTrue(order, posC > posB);
            assertTrue(order, posC > posD);
            assertTrue(order, posC > posE);
            assertTrue(order, posC > posF);

            // f.addBeforeOrderingOthers();
            assertTrue(order, posF < posA);
            assertTrue(order, posF < posB);
            assertTrue(order, posF < posC);
            assertTrue(order, posF < posD);
            assertTrue(order, posF < posE);

            // f.addBeforeOrdering("b");
            assertTrue(order, posF < posB);
        }
    }

    private class RelativeTestRunner2 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            a.addAfterOrderingOthers();
            a.addBeforeOrdering("c");
            b.addBeforeOrderingOthers();
            d.addAfterOrderingOthers();
            e.addBeforeOrderingOthers();
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // a.addAfterOrderingOthers();
            assertTrue(order, posA > posB);
            assertTrue(order, posA > posE);
            assertTrue(order, posA > posF);

            // a.addBeforeOrdering("c");
            assertTrue(order, posC > posA);
            assertTrue(order, posC > posB);
            assertTrue(order, posC > posE);
            assertTrue(order, posC > posF);

            // b.addBeforeOrderingOthers();
            assertTrue(order, posB < posA);
            assertTrue(order, posB < posC);
            assertTrue(order, posB < posD);
            assertTrue(order, posB < posF);

            // d.addAfterOrderingOthers();
            assertTrue(order, posD > posB);
            assertTrue(order, posD > posE);
            assertTrue(order, posD > posF);

            // e.addBeforeOrderingOthers();
            assertTrue(order, posE < posA);
            assertTrue(order, posE < posC);
            assertTrue(order, posE < posD);
            assertTrue(order, posE < posF);
        }
    }

    private class RelativeTestRunner3 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            a.addAfterOrdering("b");
            c.addBeforeOrderingOthers();
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // a.addAfterOrdering("b");
            assertTrue(order, posA > posB);

            // c.addBeforeOrderingOthers();
            assertTrue(order, posC < posA);
            assertTrue(order, posC < posB);
            assertTrue(order, posC < posD);
            assertTrue(order, posC < posE);
            assertTrue(order, posC < posF);
        }
    }

    private class RelativeTestRunner4 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            b.addAfterOrdering("a");
            c.addAfterOrdering("b");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // b.addAfterOrdering("a");
            assertTrue(order, posB > posA);

            // c.addAfterOrdering("b");
            assertTrue(order, posC > posB);
        }
    }

    private class RelativeTestRunner5 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            b.addBeforeOrdering("a");
            c.addBeforeOrdering("b");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // b.addBeforeOrdering("a");
            assertTrue(order, posB < posA);

            // c.addBeforeOrdering("b");
            assertTrue(order, posC < posB);
        }
    }

    private class RelativeTestRunner6 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            b.addBeforeOrdering("a");
            b.addAfterOrdering("c");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // b.addBeforeOrdering("a");
            assertTrue(order, posB < posA);

            //b.addAfterOrdering("c");
            assertTrue(order, posB > posC);
        }
    }

    private class RelativeTestRunner7 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            b.addBeforeOrdering("a");
            c.addBeforeOrdering("b");
            a.addAfterOrdering("c");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // b.addBeforeOrdering("a");
            assertTrue(order, posB < posA);

            // c.addBeforeOrdering("b");
            assertTrue(order, posC < posB);

            // a.addAfterOrdering("c");
            assertTrue(order, posA > posC);
        }
    }

    private class RelativeTestRunner8 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            a.addBeforeOrderingOthers();
            a.addBeforeOrdering("b");
            b.addBeforeOrderingOthers();
            c.addAfterOrdering("b");
            d.addAfterOrdering("c");
            e.addAfterOrderingOthers();
            f.addAfterOrderingOthers();
            f.addAfterOrdering("e");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // a.addBeforeOrderingOthers();
            assertTrue(order, posA < posB);
            assertTrue(order, posA < posC);
            assertTrue(order, posA < posD);
            assertTrue(order, posA < posE);
            assertTrue(order, posA < posF);

            // a.addBeforeOrdering("b");
            assertTrue(order, posA < posB);

            // b.addBeforeOrderingOthers();
            assertTrue(order, posB < posC);
            assertTrue(order, posB < posD);
            assertTrue(order, posB < posE);
            assertTrue(order, posB < posF);

            // c.addAfterOrdering("b");
            assertTrue(order, posC > posB);

            // d.addAfterOrdering("c");
            assertTrue(order, posD > posC);

            // e.addAfterOrderingOthers();
            assertTrue(order, posE > posA);
            assertTrue(order, posE > posB);
            assertTrue(order, posE > posC);
            assertTrue(order, posE > posD);

            // f.addAfterOrderingOthers();
            assertTrue(order, posF > posA);
            assertTrue(order, posF > posB);
            assertTrue(order, posF > posC);
            assertTrue(order, posF > posD);
            assertTrue(order, posF > posE);

            // f.addAfterOrdering("e");
            assertTrue(order, posF > posE);
        }
    }

    private class RelativeTestRunner9 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            a.addBeforeOrderingOthers();
            b.addBeforeOrdering("a");
            c.addBeforeOrdering("b");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // a.addBeforeOrderingOthers();
            assertTrue(order, posA < posD);
            assertTrue(order, posA < posE);
            assertTrue(order, posA < posF);

            // b.addBeforeOrdering("a");
            assertTrue(order, posB < posA);

            // c.addBeforeOrdering("b");
            assertTrue(order, posC < posB);
        }
    }

    private class RelativeTestRunner10 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            a.addAfterOrderingOthers();
            b.addAfterOrdering("a");
            c.addAfterOrdering("b");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // a.addAfterOrderingOthers();
            assertTrue(order, posA > posD);
            assertTrue(order, posA > posE);
            assertTrue(order, posA > posF);

            // b.addAfterOrdering("a");
            assertTrue(order, posB > posA);

            // c.addAfterOrdering("b");
            assertTrue(order, posC > posB);
        }
    }

    private class RelativeTestRunner11 implements RelativeOrderingTestRunner {

        @Override
        public void init() {
            a.addAfterOrdering("b");
            b.addAfterOrdering("z");
            b.addBeforeOrdering("y");
        }

        @Override
        public void validate(String order) {
            // There is some duplication in the tests below - it is easier to
            // check the tests are complete this way.

            // a.addAfterOrdering("b");
            assertTrue(order, posA > posB);
        }
    }
}
