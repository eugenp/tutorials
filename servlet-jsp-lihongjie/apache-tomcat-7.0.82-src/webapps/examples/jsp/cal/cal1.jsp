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

<%@ page language="java" import="cal.*" %>
<jsp:useBean id="table" scope="session" class="cal.TableBean" />

<%
    table.processRequest(request);
    if (table.getProcessError() == false) {
%>

<!-- html table goes here -->
<CENTER>
<TABLE WIDTH=60% BGCOLOR=yellow CELLPADDING=15>
<TR>
<TD ALIGN=CENTER> <A HREF=cal1.jsp?date=prev> prev </A>
<TD ALIGN=CENTER> Calendar:<%= table.getDate() %></TD>
<TD ALIGN=CENTER> <A HREF=cal1.jsp?date=next> next </A>
</TR>
</TABLE>

<!-- the main table -->
<TABLE WIDTH=60% BGCOLOR=lightblue BORDER=1 CELLPADDING=10>
<TR>
<TH> Time </TH>
<TH> Appointment </TH>
</TR>
<FORM METHOD=POST ACTION=cal1.jsp>
<%
    for(int i=0; i<table.getEntries().getRows(); i++) {
       cal.Entry entr = table.getEntries().getEntry(i);
%>
    <TR>
    <TD>
    <A HREF=cal2.jsp?time=<%= entr.getHour() %>>
        <%= entr.getHour() %> </A>
    </TD>
    <TD BGCOLOR=<%= entr.getColor() %>>
    <% out.print(util.HTMLFilter.filter(entr.getDescription())); %>
    </TD>
    </TR>
<%
    }
%>
</FORM>
</TABLE>
<BR>

<!-- footer -->
<TABLE WIDTH=60% BGCOLOR=yellow CELLPADDING=15>
<TR>
<TD ALIGN=CENTER>  <% out.print(util.HTMLFilter.filter(table.getName())); %> :
             <% out.print(util.HTMLFilter.filter(table.getEmail())); %> </TD>
</TR>
</TABLE>
</CENTER>

<%
    } else {
%>
<font size=5>
    You must enter your name and email address correctly.
</font>
<%
    }
%>


</BODY>
</HTML>


