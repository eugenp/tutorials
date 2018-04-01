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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" import="java.util.Map"%>
<%@ taglib prefix="bug55807" uri="http://tomcat.apache.org/bug55807" %>
<html>
  <body>
    <bug55807:Echo echo="OK"/>
    <p>DependenciesCount: <%=_jspx_dependants.size() %></p>
    <%
    for (Map.Entry<String,Long> entry : _jspx_dependants.entrySet()) {
        out.println("<p>" + entry.getKey() + " : " + entry.getValue() + "</p>");
    }
    %>
  </body>
</html>