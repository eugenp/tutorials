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
<html>
  <head><title>Bug 42565 test case</title></head>
  <body>
    <tags:echo echo="00-${false?true:false}" />
    <tags:echo echo="01-${false?true: false}" />
    <tags:echo echo="02-${false?true :false}" />
    <tags:echo echo="03-${false?true : false}" />
    <tags:echo echo="04-${false? true:false}" />
    <tags:echo echo="05-${false? true: false}" />
    <tags:echo echo="06-${false? true :false}" />
    <tags:echo echo="07-${false? true : false}" />
    <tags:echo echo="08-${ false?true:false}" />
    <tags:echo echo="09-${ false?true: false}" />
    <tags:echo echo="10-${ false?true :false}" />
    <tags:echo echo="11-${ false?true : false}" />
    <tags:echo echo="12-${ false? true:false}" />
    <tags:echo echo="13-${ false? true: false}" />
    <tags:echo echo="14-${ false? true :false}" />
    <tags:echo echo="15-${ false? true : false}" />
  </body>
</html>