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
  <head>
    <title>Tag Examples - choose</title>
  </head>
  <body>
    <h1>Tag Plugin Examples - &lt;c:choose></h1>

    <hr/>
    <br/>
    <a href="notes.html">Plugin Introductory Notes</a>
    <br/>
    <a href="howto.html">Brief Instructions for Writing Plugins</a>
    <br/> <br/>
    <hr/>

    <br/>

    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <c:forEach var="index" begin="0" end="4">
      # ${index}:
      <c:choose>
        <c:when test="${index == 1}">
          One!<br/>
        </c:when>
        <c:when test="${index == 4}">
          Four!<br/>
        </c:when>
        <c:when test="${index == 3}">
          Three!<br/>
        </c:when>
        <c:otherwise>
          Huh?<br/>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </body>
</html>
