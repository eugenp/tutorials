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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="my" uri="http://tomcat.apache.org/jsp2-example-taglib"%>

<html>
  <head>
    <title>JSP 2.0 Expression Language - Functions</title>
  </head>
  <body>
    <h1>JSP 2.0 Expression Language - Functions</h1>
    <hr>
    An upgrade from the JSTL expression language, the JSP 2.0 EL also
    allows for simple function invocation.  Functions are defined
    by tag libraries and are implemented by a Java programmer as
    static methods.

    <blockquote>
      <u><b>Change Parameter</b></u>
      <form action="functions.jsp" method="GET">
          foo = <input type="text" name="foo" value="${fn:escapeXml(param["foo"])}">
          <input type="submit">
      </form>
      <br>
      <code>
        <table border="1">
          <thead>
            <td><b>EL Expression</b></td>
            <td><b>Result</b></td>
          </thead>
          <tr>
            <td>\${param["foo"]}</td>
            <td>${fn:escapeXml(param["foo"])}&nbsp;</td>
          </tr>
          <tr>
            <td>\${my:reverse(param["foo"])}</td>
            <td>${my:reverse(fn:escapeXml(param["foo"]))}&nbsp;</td>
          </tr>
          <tr>
            <td>\${my:reverse(my:reverse(param["foo"]))}</td>
            <td>${my:reverse(my:reverse(fn:escapeXml(param["foo"])))}&nbsp;</td>
          </tr>
          <tr>
            <td>\${my:countVowels(param["foo"])}</td>
            <td>${my:countVowels(fn:escapeXml(param["foo"]))}&nbsp;</td>
          </tr>
        </table>
      </code>
    </blockquote>
  </body>
</html>

