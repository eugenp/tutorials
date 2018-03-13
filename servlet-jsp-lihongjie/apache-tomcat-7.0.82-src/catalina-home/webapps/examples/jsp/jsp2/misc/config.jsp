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
<%@ taglib prefix="my" uri="http://tomcat.apache.org/jsp2-example-taglib"%>
    <h1>JSP 2.0 Examples - JSP Configuration</h1>
    <hr>
    <p>Using a &lt;jsp-property-group&gt; element in the web.xml
    deployment descriptor, this JSP page has been configured in the
    following ways:</p>
    <ul>
      <li>Uses &lt;include-prelude&gt; to include the top banner.</li>
      <li>Uses &lt;include-coda&gt; to include the bottom banner.</li>
      <li>Uses &lt;scripting-invalid&gt; true to disable
          &lt;% scripting %&gt; elements</li>
      <li>Uses &lt;el-ignored&gt; true to disable ${EL} elements</li>
      <li>Uses &lt;page-encoding&gt; ISO-8859-1 to set the page encoding (though this is the default anyway)</li>
    </ul>
    There are various other configuration options that can be used.

