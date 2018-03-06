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
<%
  if (request.getParameter("logoff") != null) {
    session.invalidate();
    response.sendRedirect("index.jsp");
    return;
  }
%>
<html>
<head>
<title>Protected Page for Examples</title>
</head>
<body bgcolor="white">

You are logged in as remote user
<b><%= util.HTMLFilter.filter(request.getRemoteUser()) %></b>
in session <b><%= session.getId() %></b><br><br>

<%
  if (request.getUserPrincipal() != null) {
%>
    Your user principal name is
    <b><%= util.HTMLFilter.filter(request.getUserPrincipal().getName()) %></b>
    <br><br>
<%
  } else {
%>
    No user principal could be identified.<br><br>
<%
  }
%>

<%
  String role = request.getParameter("role");
  if (role == null)
    role = "";
  if (role.length() > 0) {
    if (request.isUserInRole(role)) {
%>
      You have been granted role
      <b><%= util.HTMLFilter.filter(role) %></b><br><br>
<%
    } else {
%>
      You have <i>not</i> been granted role
      <b><%= util.HTMLFilter.filter(role) %></b><br><br>
<%
    }
  }
%>

To check whether your username has been granted a particular role,
enter it here:
<form method="GET" action='<%= response.encodeURL("index.jsp") %>'>
<input type="text" name="role" value="<%= util.HTMLFilter.filter(role) %>">
</form>
<br><br>

If you have configured this app for form-based authentication, you can log
off by clicking
<a href='<%= response.encodeURL("index.jsp?logoff=true") %>'>here</a>.
This should cause you to be returned to the logon page after the redirect
that is performed.

</body>
</html>
