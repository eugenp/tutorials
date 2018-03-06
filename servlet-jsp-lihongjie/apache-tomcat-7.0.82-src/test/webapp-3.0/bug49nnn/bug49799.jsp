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
<jsp:element name="p">
  <jsp:attribute name="style" omit="false">color:red</jsp:attribute>
  <jsp:body>00-Red</jsp:body>
</jsp:element>
<jsp:element name="p">
  <jsp:attribute name="style" omit="true">color:red</jsp:attribute>
  <jsp:body>01-Not Red</jsp:body>
</jsp:element>
<jsp:element name="p">
  <jsp:attribute name="style" omit="${false}">color:red</jsp:attribute>
  <jsp:body>02-Red</jsp:body>
</jsp:element>
<jsp:element name="p">
  <jsp:attribute name="style" omit="${true}">color:red</jsp:attribute>
  <jsp:body>03-Not Red</jsp:body>
</jsp:element>
<jsp:element name="p">
  <jsp:attribute name="style" omit="<%=(1==2)%>">color:red</jsp:attribute>
  <jsp:body>04-Red</jsp:body>
</jsp:element>
<jsp:element name="p">
  <jsp:attribute name="style" omit="<%=(1==1)%>">color:red</jsp:attribute>
  <jsp:body>05-Not Red</jsp:body>
</jsp:element>