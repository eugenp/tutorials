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
<font size=5 color="red">
<%! String[] fruits; %>
<jsp:useBean id="foo" scope="page" class="checkbox.CheckTest" />

<jsp:setProperty name="foo" property="fruit" param="fruit" />
<hr>
The checked fruits (got using request) are: <br>
<%
    fruits = request.getParameterValues("fruit");
%>
<ul>
<%
    if (fruits != null) {
      for (int i = 0; i < fruits.length; i++) {
%>
<li>
<%
          out.println (util.HTMLFilter.filter(fruits[i]));
      }
    } else out.println ("none selected");
%>
</ul>
<br>
<hr>

The checked fruits (got using beans) are <br>

<%
        fruits = foo.getFruit();
%>
<ul>
<%
    if (!fruits[0].equals("1")) {
      for (int i = 0; i < fruits.length; i++) {
%>
<li>
<%
          out.println (util.HTMLFilter.filter(fruits[i]));
      }
    } else out.println ("none selected");
%>
</ul>
</font>
</body>
</html>
