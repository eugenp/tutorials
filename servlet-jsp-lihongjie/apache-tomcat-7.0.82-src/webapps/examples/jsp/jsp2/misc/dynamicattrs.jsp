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
<html>
  <head>
    <title>JSP 2.0 Examples - Dynamic Attributes</title>
  </head>
  <body>
    <h1>JSP 2.0 Examples - Dynamic Attributes</h1>
    <hr>
    <p>This JSP page invokes a custom tag that accepts a dynamic set
    of attributes.  The tag echoes the name and value of all attributes
    passed to it.</p>
    <hr>
    <h2>Invocation 1 (six attributes)</h2>
    <ul>
      <my:echoAttributes x="1" y="2" z="3" r="red" g="green" b="blue"/>
    </ul>
    <h2>Invocation 2 (zero attributes)</h2>
    <ul>
      <my:echoAttributes/>
    </ul>
    <h2>Invocation 3 (three attributes)</h2>
    <ul>
      <my:echoAttributes dogName="Scruffy"
                         catName="Fluffy"
                         blowfishName="Puffy"/>
    </ul>
  </body>
</html>
