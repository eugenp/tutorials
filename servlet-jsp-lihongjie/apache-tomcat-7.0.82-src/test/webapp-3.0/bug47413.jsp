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
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="values" class="jsp2.examples.ValuesBean" />
<html>
  <head><title>Bug 47413 test case</title></head>
  <body>
    <jsp:setProperty name="values" property="stringValue" value="${'hello'} wo${'rld'}"/>
    <p>00-${values.stringValue}</p>
    <tags:echo echo="01-${'hello'} wo${'rld'}"/>

    <jsp:setProperty name="values" property="doubleValue" value="${1+2}.${220}"/>
    <p>02-${values.doubleValue}</p>
    <tags:echo-double index="03" echo="${1+2}.${220}"/>

    <jsp:setProperty name="values" property="longValue" value="000${1}${7}"/>
    <p>04-${values.longValue}</p>
    <tags:echo-long index="05" echo="000${1}${7}"/>

    <jsp:setProperty name="values" property="stringValue"
                     value="${undefinedFoo}hello world${undefinedBar}"/>
    <p>06-${values.stringValue}</p>
    <tags:echo echo="${undefinedFoo}07-hello world${undefinedBar}"/>

    <jsp:setProperty name="values" property="doubleValue"
                     value="${undefinedFoo}${undefinedBar}"/>
    <p>08-${values.doubleValue}</p>
    <tags:echo-double index="09" echo="${undefinedFoo}${undefinedBar}"/>

    <jsp:setProperty name="values" property="longValue"
                     value="${undefinedFoo}${undefinedBar}"/>
    <p>10-${values.longValue}</p>
    <tags:echo-long index="11" echo="${undefinedFoo}${undefinedBar}"/>

  </body>
</html>

