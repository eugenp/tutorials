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
    <title>JSP 2.0 Expression Language - Basic Comparisons</title>
  </head>
  <body>
    <h1>JSP 2.0 Expression Language - Basic Comparisons</h1>
    <hr>
    This example illustrates basic Expression Language comparisons.
    The following comparison operators are supported:
    <ul>
      <li>Less-than (&lt; or lt)</li>
      <li>Greater-than (&gt; or gt)</li>
      <li>Less-than-or-equal (&lt;= or le)</li>
      <li>Greater-than-or-equal (&gt;= or ge)</li>
      <li>Equal (== or eq)</li>
      <li>Not Equal (!= or ne)</li>
    </ul>
    <blockquote>
      <u><b>Numeric</b></u>
      <code>
        <table border="1">
          <thead>
        <td><b>EL Expression</b></td>
        <td><b>Result</b></td>
      </thead>
      <tr>
        <td>\${1 &lt; 2}</td>
        <td>${1 < 2}</td>
      </tr>
      <tr>
        <td>\${1 lt 2}</td>
        <td>${1 lt 2}</td>
      </tr>
      <tr>
        <td>\${1 &gt; (4/2)}</td>
        <td>${1 > (4/2)}</td>
      </tr>
      <tr>
        <td>\${1 gt (4/2)}</td>
        <td>${1 gt (4/2)}</td>
      </tr>
      <tr>
        <td>\${4.0 &gt;= 3}</td>
        <td>${4.0 >= 3}</td>
      </tr>
      <tr>
        <td>\${4.0 ge 3}</td>
        <td>${4.0 ge 3}</td>
      </tr>
      <tr>
        <td>\${4 &lt;= 3}</td>
        <td>${4 <= 3}</td>
      </tr>
      <tr>
        <td>\${4 le 3}</td>
        <td>${4 le 3}</td>
      </tr>
      <tr>
        <td>\${100.0 == 100}</td>
        <td>${100.0 == 100}</td>
      </tr>
      <tr>
        <td>\${100.0 eq 100}</td>
        <td>${100.0 eq 100}</td>
      </tr>
      <tr>
        <td>\${(10*10) != 100}</td>
        <td>${(10*10) != 100}</td>
      </tr>
      <tr>
        <td>\${(10*10) ne 100}</td>
        <td>${(10*10) ne 100}</td>
      </tr>
    </table>
      </code>
      <br>
      <u><b>Alphabetic</b></u>
      <code>
        <table border="1">
          <thead>
            <td><b>EL Expression</b></td>
            <td><b>Result</b></td>
          </thead>
          <tr>
            <td>\${'a' &lt; 'b'}</td>
            <td>${'a' < 'b'}</td>
          </tr>
          <tr>
            <td>\${'hip' &gt; 'hit'}</td>
            <td>${'hip' > 'hit'}</td>
          </tr>
          <tr>
            <td>\${'4' &gt; 3}</td>
            <td>${'4' > 3}</td>
          </tr>
        </table>
      </code>
    </blockquote>
  </body>
</html>
