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
<%@page session="false"%>

<pre>
Use cases:

1. Simple dispatch
 - servlet does startAsync()
 - background thread calls ctx.dispatch()
   <a href="<%=response.encodeURL("/examples/async/async0")%>"> Async 0 </a>

2. Simple dispatch
 - servlet does startAsync()
 - background thread calls dispatch(/path/to/jsp)
   <a href="<%=response.encodeURL("/examples/async/async1")%>"> Async 1 </a>

3. Simple dispatch
 - servlet does startAsync()
 - background thread calls writes and calls complete()
   <a href="<%=response.encodeURL("/examples/async/async2")%>"> Async 2 </a>

4. Simple dispatch
 - servlet does a startAsync()
 - servlet calls dispatch(/path/to/jsp)
 - servlet calls complete()
   <a href="<%=response.encodeURL("/examples/async/async3")%>"> Async 3 </a>

3. Timeout s1
 - servlet does a startAsync()
 - servlet does a setAsyncTimeout
 - returns - waits for timeout to happen should return error page

4. Timeout s2
 - servlet does a startAsync()
 - servlet does a setAsyncTimeout
 - servlet does a addAsyncListener
 - returns - waits for timeout to happen and listener invoked

5. Dispatch to asyncSupported=false servlet
 - servlet1 does a startAsync()
 - servlet1 dispatches to dispatch(/servlet2)
 - the container calls complete() after servlet2 is complete
 - TODO

6. Chained dispatch
 - servlet1 does a startAsync
 - servlet1 does a dispatch to servlet2 (asyncsupported=true)
 - servlet2 does a dispatch to servlet3 (asyncsupported=true)
 - servlet3 does a dispatch to servlet4 (asyncsupported=false)


7. Stock ticker
   <a href="<%=response.encodeURL("/examples/async/stockticker")%>"> StockTicker </a>
</pre>