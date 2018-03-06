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
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%
request.setAttribute("text", "World <&>");
request.setAttribute("textQuote", "'World <&>'");
%>
<html>
  <head><title>Bug 56265 test case</title></head>
  <body>
    <p>[1: <tags:bug56265 data-test="window.alert('Hello World <&>!')"/>]</p>
    <p>[2: <tags:bug56265 data-test="window.alert('Hello ${text}!')"/>]</p>
    <p>[3: <tags:bug56265 data-test="window.alert('Hello &apos;World <&>&apos;!')"/>]</p>
    <p>[4: <tags:bug56265 data-test="window.alert('Hello ${textQuote}!')"/>]</p>
  </body>
</html>