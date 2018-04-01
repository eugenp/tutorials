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

package org.apache.el;

public class TesterBeanC {
    public String sayHello(TesterBeanA a, TesterBeanB b) {
        return "AB: Hello " + a.getName() + " from " + b.getName();
    }
    public String sayHello(TesterBeanAA a, TesterBeanB b) {
        return "AAB: Hello " + a.getName() + " from " + b.getName();
    }
    public String sayHello(TesterBeanA a, TesterBeanBB b) {
        return "ABB: Hello " + a.getName() + " from " + b.getName();
    }
    public String sayHello(TesterBeanA a, TesterBeanBB... b) {
        StringBuilder result =
            new StringBuilder("ABB[]: Hello " + a.getName() + " from ");
        for (int i = 0; i < b.length; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(b[i].getName());
        }
        return result.toString();
    }
}
