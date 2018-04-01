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
<html>
<body bgcolor="white">
<h1> Request Information </h1>
<font size="4">
JSP Request Method: <%= util.HTMLFilter.filter(request.getMethod()) %>
<br>
Request URI: <%= util.HTMLFilter.filter(request.getRequestURI()) %>
<br>
Request Protocol: <%= util.HTMLFilter.filter(request.getProtocol()) %>
<br>
Servlet path: <%= util.HTMLFilter.filter(request.getServletPath()) %>
<br>
Path info: <%= util.HTMLFilter.filter(request.getPathInfo()) %>
<br>
Query string: <%= util.HTMLFilter.filter(request.getQueryString()) %>
<br>
Content length: <%= request.getContentLength() %>
<br>
Content type: <%= util.HTMLFilter.filter(request.getContentType()) %>
<br>
Server name: <%= util.HTMLFilter.filter(request.getServerName()) %>
<br>
Server port: <%= request.getServerPort() %>
<br>
Remote user: <%= util.HTMLFilter.filter(request.getRemoteUser()) %>
<br>
Remote address: <%= util.HTMLFilter.filter(request.getRemoteAddr()) %>
<br>
Remote host: <%= util.HTMLFilter.filter(request.getRemoteHost()) %>
<br>
Authorization scheme: <%= util.HTMLFilter.filter(request.getAuthType()) %>
<br>
Locale: <%= request.getLocale() %>
<hr>
The browser you are using is
<%= util.HTMLFilter.filter(request.getHeader("User-Agent")) %>
<hr>
</font>
</body>
</html>
