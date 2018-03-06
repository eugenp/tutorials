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
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
 <head>
  <title>401 Unauthorized</title>
  <style type="text/css">
    <!--
    BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;font-size:12px;}
    H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;}
    PRE, TT {border: 1px dotted #525D76}
    A {color : black;}A.name {color : black;}
    -->
  </style>
 </head>
 <body>
   <h1>401 Unauthorized</h1>
   <p>
    You are not authorized to view this page. If you have not changed
    any configuration files, please examine the file
    <tt>conf/tomcat-users.xml</tt> in your installation. That
    file must contain the credentials to let you use this webapp.
   </p>
   <p>
    For example, to add the <tt>manager-gui</tt> role to a user named
    <tt>tomcat</tt> with a password of <tt>s3cret</tt>, add the following to the
    config file listed above.
   </p>
<pre>
&lt;role rolename="manager-gui"/&gt;
&lt;user username="tomcat" password="s3cret" roles="manager-gui"/&gt;
</pre>
   <p>
    Note that for Tomcat 7 onwards, the roles required to use the manager
    application were changed from the single <tt>manager</tt> role to the
    following four roles. You will need to assign the role(s) required for
    the functionality you wish to access.
   </p>
    <ul>
      <li><tt>manager-gui</tt> - allows access to the HTML GUI and the status
          pages</li>
      <li><tt>manager-script</tt> - allows access to the text interface and the
          status pages</li>
      <li><tt>manager-jmx</tt> - allows access to the JMX proxy and the status
          pages</li>
      <li><tt>manager-status</tt> - allows access to the status pages only</li>
    </ul>
   <p>
    The HTML interface is protected against CSRF but the text and JMX interfaces
    are not. To maintain the CSRF protection:
   </p>
   <ul>
    <li>Users with the <tt>manager-gui</tt> role should not be granted either
        the <tt>manager-script</tt> or <tt>manager-jmx</tt> roles.</li>
    <li>If the text or jmx interfaces are accessed through a browser (e.g. for
        testing since these interfaces are intended for tools not humans) then
        the browser must be closed afterwards to terminate the session.</li>
   </ul>
   <p>
    For more information - please see the
    <a href="/docs/manager-howto.html">Manager App HOW-TO</a>.
   </p>
 </body>

</html>
