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
<%@ page isELIgnored="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
  <head><title>Bug 48668a test case</title></head>
  <body>
    <p><tags:echo echo="00-Hello world" />#{foo.bar}</p>
    <p><tags:echo echo="01-Hello world" />${foo.bar}</p>

    <p>10-<tags:bug48668 expr="Hello ${'foo.bar}" /></p>
    <p>11-Hello <tags:bug48668 expr="${'foo.bar}" /></p>
    <p>12-<tags:bug48668 expr="Hello #{'foo.bar}" /></p>
    <p>13-Hello <tags:bug48668 expr="#{'foo.bar}" /></p>

    <p>14-<tags:bug48668 expr="Hello ${'foo" />}</p>
    <p>15-Hello <tags:bug48668 expr="${'foo" />}</p>
    <p>16-<tags:bug48668 expr="Hello #{'foo" />}</p>
    <p>17-Hello <tags:bug48668 expr="#{'foo" />}</p>

    <p>18-<tags:bug48668 ><jsp:attribute name="expr">Hello ${'foo.bar}</jsp:attribute></tags:bug48668></p>
    <p>19-Hello <tags:bug48668 ><jsp:attribute name="expr">${'foo.bar}</jsp:attribute></tags:bug48668></p>
    <p>20-<tags:bug48668 ><jsp:attribute name="expr">Hello #{'foo.bar}</jsp:attribute></tags:bug48668></p>
    <p>21-Hello <tags:bug48668 ><jsp:attribute name="expr">#{'foo.bar}</jsp:attribute></tags:bug48668></p>

    <p>30-<tags:bug48668 noexpr="Hello ${'foo}"/></p>
    <p>31-Hello <tags:bug48668 noexpr="${'foo}"/></p>
    <p>32-<tags:bug48668 noexpr="Hello #{'foo}"/></p>
    <p>33-Hello <tags:bug48668 noexpr="#{'foo}"/></p>
    <p>34-<tags:bug48668><jsp:attribute name="noexpr">Hello ${'foo}</jsp:attribute></tags:bug48668></p>
    <p>35-Hello <tags:bug48668><jsp:attribute name="noexpr">${'foo}</jsp:attribute></tags:bug48668></p>
    <p>36-<tags:bug48668><jsp:attribute name="noexpr">Hello #{'foo}</jsp:attribute></tags:bug48668></p>
    <p>37-Hello <tags:bug48668><jsp:attribute name="noexpr">#{'foo}</jsp:attribute></tags:bug48668></p>

    <p>40-<tags:bug48668><jsp:attribute name="fragment">Hello ${'foo}</jsp:attribute></tags:bug48668></p>
    <p>41-Hello <tags:bug48668><jsp:attribute name="fragment">${'foo}</jsp:attribute></tags:bug48668></p>
    <p>42-<tags:bug48668><jsp:attribute name="fragment">Hello #{'foo}</jsp:attribute></tags:bug48668></p>
    <p>43-Hello <tags:bug48668><jsp:attribute name="fragment">#{'foo}</jsp:attribute></tags:bug48668></p>

    <p>50-<tags:bug48668>Hello ${'foo}</tags:bug48668></p>
    <p>51-Hello <tags:bug48668>${'foo}</tags:bug48668></p>
    <p>52-<tags:bug48668>Hello #{'foo}</tags:bug48668></p>
    <p>53-Hello <tags:bug48668>#{'foo}</tags:bug48668></p>
  </body>
</html>

