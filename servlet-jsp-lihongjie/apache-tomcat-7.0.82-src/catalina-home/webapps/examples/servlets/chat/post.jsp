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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
   <title>JSP Chat</title>
</head>

<body bgcolor="#FFFFFF">

<form method="POST" action='chat' name="postForm">
<input type="hidden" name="action" value="post">
Message: <input type="text" name="message">
<input type="submit">
</form>

<br>
<%
  String serverName = request.getServerName();
  if ("localhost".equals(serverName)) {
      serverName = "127.0.0.1";
  } else if ("127.0.0.1".equals(serverName)) {
      serverName = "localhost";
  }

  String chatUrl = request.getScheme() + "://" + serverName + ":"
    + request.getServerPort() + request.getContextPath()
    + request.getServletPath();

  // strip "post.jsp" from the address
  chatUrl = chatUrl.substring(0, chatUrl.lastIndexOf("/") + 1);
%>
<a target="_blank" href="<%=chatUrl %>">Click to open a new chat window</a>
<em>Note</em>: To avoid hitting the limit on the count of simultaneous
connections to the same host, imposed by the
<a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec8.html#sec8.1.4">HTTP specification</a>,
the second chat window should be opened using a different URL, e.g. with
an IP address instead of the host name.
</body>
</html>
