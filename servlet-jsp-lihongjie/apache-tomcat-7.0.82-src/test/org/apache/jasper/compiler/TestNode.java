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
package org.apache.jasper.compiler;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.apache.jasper.compiler.Node.PageDirective;

public class TestNode {

    /*
     * https://bz.apache.org/bugzilla/show_bug.cgi?id=57099
     */
    @Test(expected=IllegalArgumentException.class)
    public void testPageDirectiveImport01() {
        doTestPageDirectiveImport("java.io.*;\r\n\timport java.net.*");
    }

    @Test
    public void testPageDirectiveImport02() {
        doTestPageDirectiveImport("a,b,c");
    }

    @Test
    public void testPageDirectiveImport03() {
        doTestPageDirectiveImport(" a , b , c ");
    }

    @Test
    public void testPageDirectiveImport04() {
        doTestPageDirectiveImport(" a\n , \r\nb , \nc\r ");
    }

    @Test
    public void testPageDirectiveImport05() {
        doTestPageDirectiveImport("java.util.List,java.util.ArrayList,java.util.Set");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPageDirectiveImport06() {
        doTestPageDirectiveImport("java.util.List;import java.util.ArrayList; import java.util.Set");
    }

    @Test
    public void testPageDirectiveImport07() {
        doTestPageDirectiveImport("java .\nutil.List,java.util.ArrayList,java.util.Set");
    }

    private void doTestPageDirectiveImport(String importDirective) {
        PageDirective pd = new PageDirective(null, null, null);
        pd.addImport(importDirective);
        List<String> imports = pd.getImports();

        Assert.assertEquals(3, imports.size());
    }
}
