<?xml version="1.0"?>
<!--
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
-->
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
  version="1.2">
<jsp:directive.page contentType="text/html"/>
<jsp:directive.page import="java.util.Date, java.util.Locale"/>
<jsp:directive.page import="java.text.*"/>

<jsp:declaration>
  String getDateTimeStr(Locale l) {
    DateFormat df = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, l);
    return df.format(new Date());
  }
</jsp:declaration>

<html>
<head>
  <title>Example JSP in XML format</title>
</head>

<body>
This is the output of a simple JSP using XML format.
<br />

<div>Use a jsp:scriptlet to loop from 1 to 10: </div>
<jsp:scriptlet>
// Note we need to declare CDATA because we don't escape the less than symbol
<![CDATA[
  for (int i = 1; i<=10; i++) {
    out.println(i);
    if (i < 10) {
      out.println(", ");
    }
  }
]]>
</jsp:scriptlet>

<!-- Because I omit br's end tag, declare it as CDATA -->
<![CDATA[
  <br><br>
]]>

<div align="left">
  Use a jsp:expression to write the date and time in the browser's locale:
  <jsp:expression>getDateTimeStr(request.getLocale())</jsp:expression>
</div>


<jsp:text>
  &lt;p&gt;This sentence is enclosed in a jsp:text element.&lt;/p&gt;
</jsp:text>

</body>
</html>
</jsp:root>
