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
  <head><title>Bug 45427 test case</title></head>
  <body>
    <p>00-${'hello world'}</p>
    <p>01-${'hello \'world'}</p>
    <p>02-${'hello "world'}</p>
    <p>03-${'hello \"world'}</p>
    <p>04-${"hello world"}</p>
    <p>05-${"hello 'world"}</p>
    <p>06-${"hello \'world"}</p>
    <p>07-${"hello \"world"}</p>

    <tags:echo echo="08-${'hello world'}" />
    <tags:echo echo="09-${'hello \\'world'}" />
    <tags:echo echo="10-${'hello \"world'}" />
    <tags:echo echo="11-${'hello \\\"world'}" />
    <tags:echo echo='12-${"hello world"}' />
    <tags:echo echo='13-${"hello \'world"}' />
    <tags:echo echo='14-${"hello \\\'world"}' />
    <tags:echo echo='15-${"hello \\"world"}' />
  </body>
</html>