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
<%@ taglib prefix="my" uri="http://tomcat.apache.org/example-taglib" %>

<html>
  <head>
    <title>JSP 2.0 Expression Language - Composite Expressions</title>
  </head>
  <body>
    <h1>JSP 2.0 Expression Language - Composite Expressions</h1>
    <hr>
    This example illustrates EL composite expressions. Composite expressions
    are formed by grouping together multiple EL expressions. Each of them is
    evaluated from left to right, coerced to String, all those strings are
    concatenated, and the result is coerced to the expected type.

    <jsp:useBean id="values" class="jsp2.examples.ValuesBean" />

    <blockquote>
      <code>
        <table border="1">
          <thead>
        <td><b>EL Expression</b></td>
        <td><b>Type</b></td>
        <td><b>Result</b></td>
      </thead>
      <tr>
        <td>\${'hello'} wo\${'rld'}</td>
        <td>String</td>
        <td><jsp:setProperty name="values" property="stringValue" value="${'hello'} wo${'rld'}"/>${values.stringValue}</td>
      </tr>
      <tr>
        <td>\${'hello'} wo\${'rld'}</td>
        <td>String</td>
        <td><my:values string="${'hello'} wo${'rld'}"/></td>
      </tr>
      <tr>
        <td>\${1+2}.\${220}</td>
        <td>Double</td>
        <td><jsp:setProperty name="values" property="doubleValue" value="${1+2}.${220}"/>${values.doubleValue}</td>
      </tr>
      <tr>
        <td>\${1+2}.\${220}</td>
        <td>Double</td>
        <td><my:values double="${1+2}.${220}"/></td>
      </tr>
      <tr>
        <td>000\${1}\${7}</td>
        <td>Long</td>
        <td><jsp:setProperty name="values" property="longValue" value="000${1}${7}"/>${values.longValue}</td>
      </tr>
      <tr>
        <td>000\${1}\${7}</td>
        <td>Long</td>
        <td><my:values long="000${1}${7}"/></td>
      </tr>
      <!--
         Undefined values are to be coerced to String, to be "",
         https://bz.apache.org/bugzilla/show_bug.cgi?id=47413
       -->
      <tr>
        <td>\${undefinedFoo}hello world\${undefinedBar}</td>
        <td>String</td>
        <td><jsp:setProperty name="values" property="stringValue" value="${undefinedFoo}hello world${undefinedBar}"/>${values.stringValue}</td>
      </tr>
      <tr>
        <td>\${undefinedFoo}hello world\${undefinedBar}</td>
        <td>String</td>
        <td><my:values string="${undefinedFoo}hello world${undefinedBar}"/></td>
      </tr>
      <tr>
        <td>\${undefinedFoo}\${undefinedBar}</td>
        <td>Double</td>
        <td><jsp:setProperty name="values" property="doubleValue" value="${undefinedFoo}${undefinedBar}"/>${values.doubleValue}</td>
      </tr>
      <tr>
        <td>\${undefinedFoo}\${undefinedBar}</td>
        <td>Double</td>
        <td><my:values double="${undefinedFoo}${undefinedBar}"/></td>
      </tr>
      <tr>
        <td>\${undefinedFoo}\${undefinedBar}</td>
        <td>Long</td>
        <td><jsp:setProperty name="values" property="longValue" value="${undefinedFoo}${undefinedBar}"/>${values.longValue}</td>
      </tr>
      <tr>
        <td>\${undefinedFoo}\${undefinedBar}</td>
        <td>Long</td>
        <td><my:values long="${undefinedFoo}${undefinedBar}"/></td>
      </tr>
    </table>
      </code>
    </blockquote>
  </body>
</html>

