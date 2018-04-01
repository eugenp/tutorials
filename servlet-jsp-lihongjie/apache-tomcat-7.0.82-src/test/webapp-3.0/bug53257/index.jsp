<%--
 Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@page contentType="text/plain; charset=UTF-8"
%><%@page import="java.net.URL,java.net.URLConnection"%><%
    String[] testFiles = new String[] {"foo;bar.txt", "foo&bar.txt",
            "foo#bar.txt", "foo%bar.txt", "foo+bar.txt", "foo bar.txt",
            "foo bar/foobar.txt"};
    for (String testFile : testFiles) {
        URL url = application.getResource("/bug53257/" + testFile);
        if (url == null) {
            out.print("FAIL (url) - " + testFile + "\n");
        } else {
            URLConnection conn = url.openConnection();
            long lastModified = conn.getLastModified();
            if (lastModified == -1) {
                out.print("FAIL (last modified)- " + testFile + "\n");
            } else {
                out.print("PASS - " + testFile + "\n");
            }
        }
    }
%>