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
<HTML>
<HEAD><TITLE>
    Calendar: A JSP APPLICATION
</TITLE></HEAD>


<BODY BGCOLOR="white">
<jsp:useBean id="table" scope="session" class="cal.TableBean" />

<%
    String time = request.getParameter ("time");
%>

<FONT SIZE=5> Please add the following event:
<BR> <h3> Date <%= table.getDate() %>
<BR> Time <%= util.HTMLFilter.filter(time) %> </h3>
</FONT>
<FORM METHOD=POST ACTION=cal1.jsp>
<BR>
<BR> <INPUT NAME="date" TYPE=HIDDEN VALUE="current">
<BR> <INPUT NAME="time" TYPE=HIDDEN VALUE="<%= util.HTMLFilter.filter(time) %>">
<BR> <h2> Description of the event <INPUT NAME="description" TYPE=TEXT SIZE=20> </h2>
<BR> <INPUT TYPE=SUBMIT VALUE="submit">
</FORM>

</BODY>
</HTML>

