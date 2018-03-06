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
<title> Plugin example </title>
<body bgcolor="white">
<h3> Current time is : </h3>
<jsp:plugin type="applet" code="Clock2.class" codebase="applet" jreversion="1.2" width="160" height="150" >
    <jsp:fallback>
        Plugin tag OBJECT or EMBED not supported by browser.
    </jsp:fallback>
</jsp:plugin>
<p>
<h4>
<font color=red>
The above applet is loaded using the Java Plugin from a jsp page using the
plugin tag.
</font>
</h4>
</body>
</html>
