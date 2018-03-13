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
<%!
  class Bug54241 {
    public String toString() {
      return null;
    }
  }
%>
<html>
  <body>
    <%
      Bug54241 bug54241 = new Bug54241();
    %>
    <!-- JspWriter.print(Object obj) is defined to print String.valueOf(obj)
         which is obj.toString() if obj is non-null. If obj.toString is null
         then this will trigger a NullPointerException -->
    <p>01: <%= bug54241 %></p>
  </body>
</html>