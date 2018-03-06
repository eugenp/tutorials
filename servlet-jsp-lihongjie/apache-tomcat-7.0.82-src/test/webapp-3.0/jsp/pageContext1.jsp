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
<%@ page import="java.io.IOException" contentType="text/plain"%>
<%
    boolean flush = Boolean.valueOf(request.getParameter("flush"));
    if (pageContext != null) {
        try {
            if (flush) {
                out.println("Flush");
                pageContext.include("/jsp/pageContext2.jsp", true);
            } else {
                pageContext.include("/jsp/pageContext2.jsp");
            }
        } catch (IOException e) {
            out.println("OK");
            return;
        } catch (Throwable t) {
            out.println("FAILED. Expected IOException, received: " + t.getClass().getName());
            return;
        }
        out.println("FAILED. Expected IOException.");
    } else {
        out.println("FAILED. Expected IOException.");
    }
%>