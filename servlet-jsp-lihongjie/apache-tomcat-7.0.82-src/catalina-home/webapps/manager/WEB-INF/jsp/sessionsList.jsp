<?xml version="1.0" encoding="ISO-8859-1"?>
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
<%@page session="false" contentType="text/html; charset=ISO-8859-1" %>
<%@page import="java.util.Collection" %>
<%@page import="java.util.Iterator" %>
<%@page import="org.apache.catalina.manager.JspHelper" %>
<%@page import="org.apache.catalina.Session" %>
<%@page import="org.apache.catalina.ha.session.DeltaSession" %>
<%@page import="org.apache.catalina.util.ContextName" %>
<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@page import="org.apache.catalina.manager.DummyProxySession"%><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<% String path = (String) request.getAttribute("path");
   String version = (String) request.getAttribute("version");
   ContextName cn = new ContextName(path, version);
   String submitUrl = JspHelper.escapeXml(response.encodeURL(
           ((HttpServletRequest) pageContext.getRequest()).getRequestURI() +
           "?path=" + path + "&version=" + version));
   Collection activeSessions = (Collection) request.getAttribute("activeSessions");
%>
<head>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>
    <meta http-equiv="pragma" content="no-cache"/><!-- HTTP 1.0 -->
    <meta http-equiv="cache-control" content="no-cache,must-revalidate"/><!-- HTTP 1.1 -->
    <meta http-equiv="expires" content="0"/><!-- 0 is an invalid value and should be treated as 'now' -->
    <meta http-equiv="content-language" content="en"/>
    <meta name="author" content="Cedrik LIME"/>
    <meta name="copyright" content="copyright 2005-2017 the Apache Software Foundation"/>
    <meta name="robots" content="noindex,nofollow,noarchive"/>
    <title>Sessions Administration for <%= JspHelper.escapeXml(cn.getDisplayName()) %></title>
</head>
<body>
<h1>Sessions Administration for <%= JspHelper.escapeXml(cn.getDisplayName()) %></h1>

<p>Tips:</p>
<ul>
    <li>Click on a column to sort.</li>
    <li>To view a session details and/or remove a session attributes, click on its id.</li>
</ul>

<div class="error"><%= JspHelper.escapeXml(request.getAttribute("error")) %></div>
<div class="message"><%= JspHelper.escapeXml(request.getAttribute("message")) %></div>

<form action="<%= submitUrl %>" method="post" id="sessionsForm">
    <fieldset><legend>Active HttpSessions informations</legend>
        <input type="hidden" name="action" id="sessionsFormAction" value="injectSessions"/>
        <input type="hidden" name="sort" id="sessionsFormSort" value="<%= JspHelper.escapeXml(request.getAttribute("sort")) %>"/>
        <% String order = (String) request.getAttribute("order");
           if (order == null || "".equals(order)) {
               order = "ASC";
           }
        %>
        <input type="hidden" name="order" id="sessionsFormSortOrder" value="<%= JspHelper.escapeXml(order) %>"/>
        <input type="submit" name="refresh" id="refreshButton" value="Refresh Sessions list" onclick="document.getElementById('sessionsFormAction').value='refreshSessions'; return true;"/>
        <%= JspHelper.formatNumber(activeSessions.size()) %> active Sessions<br/>
        <table border="1" cellpadding="2" cellspacing="2" width="100%">
            <thead>
                <tr>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;">Session Id</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;">Type</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='locale'; document.getElementById('refreshButton').click(); return true;">Guessed Locale</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='user'; document.getElementById('refreshButton').click(); return true;">Guessed User name</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='CreationTime'; document.getElementById('refreshButton').click(); return true;">Creation Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='LastAccessedTime'; document.getElementById('refreshButton').click(); return true;">Last Accessed Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='UsedTime'; document.getElementById('refreshButton').click(); return true;">Used Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='InactiveTime'; document.getElementById('refreshButton').click(); return true;">Inactive Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='TTL'; document.getElementById('refreshButton').click(); return true;"><span title="Time To Live">TTL</span></a></th>
                </tr>
            </thead>
            <% if (activeSessions.size() > 10) { %>
            <tfoot><%-- <tfoot> is the same as <thead> --%>
                <tr>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;">Session Id</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;">Type</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='locale'; document.getElementById('refreshButton').click(); return true;">Guessed Locale</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='user'; document.getElementById('refreshButton').click(); return true;">Guessed User name</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='CreationTime'; document.getElementById('refreshButton').click(); return true;">Creation Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='LastAccessedTime'; document.getElementById('refreshButton').click(); return true;">Last Accessed Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='UsedTime'; document.getElementById('refreshButton').click(); return true;">Used Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='InactiveTime'; document.getElementById('refreshButton').click(); return true;">Inactive Time</a></th>
                    <th><a onclick="document.getElementById('sessionsFormSort').value='TTL'; document.getElementById('refreshButton').click(); return true;"><span title="Time To Live">TTL</span></a></th>
                </tr>
            </tfoot>
            <% } // end if %>
            <tbody>
<% Iterator iter = activeSessions.iterator();
   while (iter.hasNext()) {
       Session currentSession = (Session) iter.next();
       String currentSessionId = JspHelper.escapeXml(currentSession.getId());
       String type;
       if (currentSession instanceof DeltaSession) {
           if (((DeltaSession) currentSession).isPrimarySession()) {
               type = "Primary";
           } else {
               type = "Backup";
           }
       } else if (currentSession instanceof DummyProxySession) {
           type = "Proxy";
       } else {
           type = "Primary";
       }
%>
                <tr>
                    <td><input type="checkbox" name="sessionIds" value="<%= currentSessionId %>" />
                      <%
                        if ("Proxy".equals(type)) {
                            out.print(currentSessionId);
                        } else {
                      %>
                      <a href="<%= submitUrl %>&amp;action=sessionDetail&amp;sessionId=<%= currentSessionId %>&amp;sessionType=<%= type %>"><%= currentSessionId %></a>
                      <%
                        }
                      %>
                    </td>
                    <td style="text-align: center;"><%= type %></td>
                    <td style="text-align: center;"><%= JspHelper.guessDisplayLocaleFromSession(currentSession) %></td>
                    <td style="text-align: center;"><%= JspHelper.guessDisplayUserFromSession(currentSession) %></td>
                    <td style="text-align: center;"><%= JspHelper.getDisplayCreationTimeForSession(currentSession) %></td>
                    <td style="text-align: center;"><%= JspHelper.getDisplayLastAccessedTimeForSession(currentSession) %></td>
                    <td style="text-align: center;"><%= JspHelper.getDisplayUsedTimeForSession(currentSession) %></td>
                    <td style="text-align: center;"><%= JspHelper.getDisplayInactiveTimeForSession(currentSession) %></td>
                    <td style="text-align: center;"><%= JspHelper.getDisplayTTLForSession(currentSession) %></td>
                </tr>
<% } // end while %>
            </tbody>
        </table>
        <p style="text-align: center;">
            <input type="submit" name="invalidate" value="Invalidate selected Sessions" onclick="document.getElementById('sessionsFormAction').value='invalidateSessions'; return true;"/>
        </p>
    </fieldset>
</form>

<form method="get" action="<%=request.getContextPath()%>/html">
  <p style="text-align: center;">
    <input type="submit" value="Return to main page" />
  </p>
</form>

<%--div style="display: none;">
<p>
    <a href="http://validator.w3.org/check?uri=referer"><img
        src="http://www.w3.org/Icons/valid-html401"
        alt="Valid HTML 4.01!" height="31" width="88"></a>
    <a href="http://validator.w3.org/check?uri=referer"><img
        src="http://www.w3.org/Icons/valid-xhtml10"
        alt="Valid XHTML 1.0!" height="31" width="88" /></a>
    <a href="http://validator.w3.org/check?uri=referer"><img
        src="http://www.w3.org/Icons/valid-xhtml11"
        alt="Valid XHTML 1.1!" height="31" width="88" /></a>
</p>
</div--%>

</body>
</html>
