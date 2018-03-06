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

<jsp:useBean id="cb" scope="session" class="colors.ColorGameBean" />
<jsp:setProperty name="cb" property="*" />

<%
    cb.processRequest();
%>

<body bgcolor=<%= cb.getColor1() %>>
<font size=6 color=<%= cb.getColor2() %>>
<p>

<% if (cb.getHint()==true) { %>

    <p> Hint #1: Vampires prey at night!
    <p>  <p> Hint #2: Nancy without the n.

<% } %>

<% if  (cb.getSuccess()==true) { %>

    <p> CONGRATULATIONS!!
    <% if  (cb.getHintTaken()==true) { %>

        <p> ( although I know you cheated and peeked into the hints)

    <% } %>

<% } %>

<p> Total attempts so far: <%= cb.getAttempts() %>
<p>

<p>

<form method=POST action=colrs.jsp>

Color #1: <input type=text name= color1 size=16>

<br>

Color #2: <input type=text name= color2 size=16>

<p>

<input type=submit name=action value="Submit">
<input type=submit name=action value="Hint">

</form>

</font>
</body>
</html>
