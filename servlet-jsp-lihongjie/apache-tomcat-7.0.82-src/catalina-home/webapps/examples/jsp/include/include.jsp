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

<font color="red">

<%@ page buffer="5kb" autoFlush="false" %>

<p>In place evaluation of another JSP which gives you the current time: <%@ include file="foo.jsp" %>

<p> <jsp:include page="foo.html" flush="true"/> by including the output of another JSP: <jsp:include page="foo.jsp" flush="true"/>
:-)

</html>
