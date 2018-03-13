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
<%@ taglib prefix="mytag" uri="/WEB-INF/jsp2/jsp2-example-taglib.tld" %>
<html>
  <head>
    <title>JSP 2.0 Examples - Hello World SimpleTag Handler</title>
  </head>
  <body>
    <h1>JSP 2.0 Examples - Hello World SimpleTag Handler</h1>
    <hr>
    <p>This tag handler simply echos "Hello, World!"  It's an example of
    a very basic SimpleTag handler with no body.</p>
    <br>
    <b><u>Result:</u></b>
    <mytag:helloWorld/>
  </body>
</html>
