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
<html><body>
<%@ taglib prefix="tags11" uri="http://tomcat.apache.org/tags11" %>
<%@ taglib prefix="tags12" uri="http://tomcat.apache.org/tags12" %>
<%@ taglib prefix="tags20" uri="http://tomcat.apache.org/tags20" %>
<%@ taglib prefix="tags21" uri="http://tomcat.apache.org/tags21" %>
<tags11:Echo echo="${'00-hello world'}"/>
<tags11:Echo echo="#{'01-hello world'}"/>
<tags12:Echo echo="${'02-hello world'}"/>
<tags12:Echo echo="#{'03-hello world'}"/>
<tags20:Echo echo="${'04-hello world'}"/>
<tags20:Echo echo="#{'05-hello world'}"/>
<tags21:Echo echo="${'06-hello world'}"/>
</body></html>