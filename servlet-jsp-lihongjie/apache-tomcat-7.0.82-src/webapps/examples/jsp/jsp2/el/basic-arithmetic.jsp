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
    <title>JSP 2.0 Expression Language - Basic Arithmetic</title>
  </head>
  <body>
    <h1>JSP 2.0 Expression Language - Basic Arithmetic</h1>
    <hr>
    This example illustrates basic Expression Language arithmetic.
    Addition (+), subtraction (-), multiplication (*), division (/ or div),
    and modulus (% or mod) are all supported.  Error conditions, like
    division by zero, are handled gracefully.
    <br>
    <blockquote>
      <code>
        <table border="1">
          <thead>
        <td><b>EL Expression</b></td>
        <td><b>Result</b></td>
      </thead>
      <tr>
        <td>\${1}</td>
        <td>${1}</td>
      </tr>
      <tr>
        <td>\${1 + 2}</td>
        <td>${1 + 2}</td>
      </tr>
      <tr>
        <td>\${1.2 + 2.3}</td>
        <td>${1.2 + 2.3}</td>
      </tr>
      <tr>
        <td>\${1.2E4 + 1.4}</td>
        <td>${1.2E4 + 1.4}</td>
      </tr>
      <tr>
        <td>\${-4 - 2}</td>
        <td>${-4 - 2}</td>
      </tr>
      <tr>
        <td>\${21 * 2}</td>
        <td>${21 * 2}</td>
      </tr>
      <tr>
        <td>\${3/4}</td>
        <td>${3/4}</td>
      </tr>
      <tr>
        <td>\${3 div 4}</td>
        <td>${3 div 4}</td>
      </tr>
      <tr>
        <td>\${3/0}</td>
        <td>${3/0}</td>
      </tr>
      <tr>
        <td>\${10%4}</td>
        <td>${10%4}</td>
      </tr>
      <tr>
        <td>\${10 mod 4}</td>
        <td>${10 mod 4}</td>
      </tr>
    <tr>
      <td>\${(1==2) ? 3 : 4}</td>
      <td>${(1==2) ? 3 : 4}</td>
    </tr>
    </table>
      </code>
    </blockquote>
  </body>
</html>
