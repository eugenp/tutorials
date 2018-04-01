<?xml version="1.0" encoding="ISO-8859-1"?>
<!--
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
-->
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                      http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
  version="3.0"
  metadata-complete="true">

    <description>
      Servlet and JSP Examples.
    </description>
    <display-name>Servlet and JSP Examples</display-name>

    <!-- Define example filters -->
    <filter>
        <filter-name>Timing filter</filter-name>
        <filter-class>filters.ExampleFilter</filter-class>
        <init-param>
            <param-name>attribute</param-name>
            <param-value>filters.ExampleFilter</param-value>
        </init-param>
    </filter>

    <filter>
        <filter-name>Request Dumper Filter</filter-name>
        <filter-class>org.apache.catalina.filters.RequestDumperFilter</filter-class>
    </filter>

    <!-- Example filter to set character encoding on each request -->
    <filter>
        <filter-name>Set Character Encoding</filter-name>
        <filter-class>org.apache.catalina.filters.SetCharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>EUC_JP</param-value>
        </init-param>
        <init-param>
            <param-name>ignore</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>

    <filter>
        <filter-name>Compression Filter</filter-name>
        <filter-class>compressionFilters.CompressionFilter</filter-class>
        <init-param>
            <param-name>compressionThreshold</param-name>
            <param-value>128</param-value>
        </init-param>
        <init-param>
            <param-name>compressionBuffer</param-name>
            <param-value>8192</param-value>
        </init-param>
        <init-param>
            <param-name>compressionMimeTypes</param-name>
            <param-value>text/html,text/plain,text/xml</param-value>
        </init-param>
        <init-param>
          <param-name>debug</param-name>
          <param-value>0</param-value>
        </init-param>
    </filter>

    <!-- Define filter mappings for the timing filters -->
    <!--
    <filter-mapping>
        <filter-name>Timing Filter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    -->

<!-- Example filter mapping to apply the "Set Character Encoding" filter
     to *all* requests processed by this web application -->
<!--
    <filter-mapping>
        <filter-name>Set Character Encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
-->

<!--
    <filter-mapping>
      <filter-name>Compression Filter</filter-name>
      <url-pattern>/CompressionTest</url-pattern>
    </filter-mapping>
-->

<!--
    <filter-mapping>
        <filter-name>Request Dumper Filter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
-->

    <!-- Define example application events listeners -->
    <listener>
        <listener-class>listeners.ContextListener</listener-class>
    </listener>
    <listener>
        <listener-class>listeners.SessionListener</listener-class>
    </listener>

    <!-- Define servlets that are included in the example application -->

    <servlet>
      <servlet-name>ServletToJsp</servlet-name>
      <servlet-class>ServletToJsp</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>ChatServlet</servlet-name>
        <servlet-class>chat.ChatServlet</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>CompressionFilterTestServlet</servlet-name>
        <servlet-class>compressionFilters.CompressionFilterTestServlet</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>HelloWorldExample</servlet-name>
        <servlet-class>HelloWorldExample</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>RequestInfoExample</servlet-name>
        <servlet-class>RequestInfoExample</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>RequestHeaderExample</servlet-name>
        <servlet-class>RequestHeaderExample</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>RequestParamExample</servlet-name>
        <servlet-class>RequestParamExample</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>CookieExample</servlet-name>
        <servlet-class>CookieExample</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>SessionExample</servlet-name>
        <servlet-class>SessionExample</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>ChatServlet</servlet-name>
        <url-pattern>/servlets/chat/chat</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>CompressionFilterTestServlet</servlet-name>
        <url-pattern>/CompressionTest</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>HelloWorldExample</servlet-name>
        <url-pattern>/servlets/servlet/HelloWorldExample</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>RequestInfoExample</servlet-name>
        <url-pattern>/servlets/servlet/RequestInfoExample/*</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>RequestHeaderExample</servlet-name>
        <url-pattern>/servlets/servlet/RequestHeaderExample</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>RequestParamExample</servlet-name>
        <url-pattern>/servlets/servlet/RequestParamExample</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>CookieExample</servlet-name>
        <url-pattern>/servlets/servlet/CookieExample</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>SessionExample</servlet-name>
        <url-pattern>/servlets/servlet/SessionExample</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>ServletToJsp</servlet-name>
        <url-pattern>/servletToJsp</url-pattern>
    </servlet-mapping>

    <jsp-config>
        <taglib>
            <taglib-uri>
               http://tomcat.apache.org/debug-taglib
            </taglib-uri>
            <taglib-location>
               /WEB-INF/jsp/debug-taglib.tld
            </taglib-location>
        </taglib>

        <taglib>
            <taglib-uri>
               http://tomcat.apache.org/example-taglib
            </taglib-uri>
            <taglib-location>
               /WEB-INF/jsp/example-taglib.tld
            </taglib-location>
        </taglib>

        <taglib>
            <taglib-uri>
               http://tomcat.apache.org/jsp2-example-taglib
            </taglib-uri>
            <taglib-location>
               /WEB-INF/jsp2/jsp2-example-taglib.tld
            </taglib-location>
        </taglib>

        <jsp-property-group>
            <description>
                Special property group for JSP Configuration JSP example.
            </description>
            <display-name>JSPConfiguration</display-name>
            <url-pattern>/jsp/jsp2/misc/config.jsp</url-pattern>
            <el-ignored>true</el-ignored>
            <page-encoding>ISO-8859-1</page-encoding>
            <scripting-invalid>true</scripting-invalid>
            <include-prelude>/jsp/jsp2/misc/prelude.jspf</include-prelude>
            <include-coda>/jsp/jsp2/misc/coda.jspf</include-coda>
        </jsp-property-group>
    </jsp-config>

   <security-constraint>
      <display-name>Example Security Constraint</display-name>
      <web-resource-collection>
         <web-resource-name>Protected Area</web-resource-name>
         <!-- Define the context-relative URL(s) to be protected -->
         <url-pattern>/jsp/security/protected/*</url-pattern>
         <!-- If you list http methods, only those methods are protected -->
         <http-method>DELETE</http-method>
         <http-method>GET</http-method>
         <http-method>POST</http-method>
         <http-method>PUT</http-method>
      </web-resource-collection>
      <auth-constraint>
         <!-- Anyone with one of the listed roles may access this area -->
         <role-name>tomcat</role-name>
         <role-name>role1</role-name>
      </auth-constraint>
    </security-constraint>

    <!-- Default login configuration uses form-based authentication -->
    <login-config>
      <auth-method>FORM</auth-method>
      <realm-name>Example Form-Based Authentication Area</realm-name>
      <form-login-config>
        <form-login-page>/jsp/security/protected/login.jsp</form-login-page>
        <form-error-page>/jsp/security/protected/error.jsp</form-error-page>
      </form-login-config>
    </login-config>

    <!-- Security roles referenced by this web application -->
    <security-role>
      <role-name>role1</role-name>
    </security-role>
    <security-role>
      <role-name>tomcat</role-name>
    </security-role>

    <!-- Environment entry examples -->
    <!--env-entry>
      <env-entry-description>
         The maximum number of tax exemptions allowed to be set.
      </env-entry-description>
      <env-entry-name>maxExemptions</env-entry-name>
      <env-entry-type>java.lang.Integer</env-entry-type>
      <env-entry-value>15</env-entry-value>
    </env-entry-->
    <env-entry>
      <env-entry-name>minExemptions</env-entry-name>
      <env-entry-type>java.lang.Integer</env-entry-type>
      <env-entry-value>1</env-entry-value>
    </env-entry>
    <env-entry>
      <env-entry-name>foo/name1</env-entry-name>
      <env-entry-type>java.lang.String</env-entry-type>
      <env-entry-value>value1</env-entry-value>
    </env-entry>
    <env-entry>
      <env-entry-name>foo/bar/name2</env-entry-name>
      <env-entry-type>java.lang.Boolean</env-entry-type>
      <env-entry-value>true</env-entry-value>
    </env-entry>
    <env-entry>
      <env-entry-name>name3</env-entry-name>
      <env-entry-type>java.lang.Integer</env-entry-type>
      <env-entry-value>1</env-entry-value>
    </env-entry>
    <env-entry>
      <env-entry-name>foo/name4</env-entry-name>
      <env-entry-type>java.lang.Integer</env-entry-type>
      <env-entry-value>10</env-entry-value>
    </env-entry>

    <!-- Async examples -->
    <servlet>
      <servlet-name>async0</servlet-name>
      <servlet-class>async.Async0</servlet-class>
      <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
      <servlet-name>async0</servlet-name>
      <url-pattern>/async/async0</url-pattern>
    </servlet-mapping>
    <servlet>
      <servlet-name>async1</servlet-name>
      <servlet-class>async.Async1</servlet-class>
      <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
      <servlet-name>async1</servlet-name>
      <url-pattern>/async/async1</url-pattern>
    </servlet-mapping>
    <servlet>
      <servlet-name>async2</servlet-name>
      <servlet-class>async.Async2</servlet-class>
      <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
      <servlet-name>async2</servlet-name>
      <url-pattern>/async/async2</url-pattern>
    </servlet-mapping>
    <servlet>
      <servlet-name>async3</servlet-name>
      <servlet-class>async.Async3</servlet-class>
      <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
      <servlet-name>async3</servlet-name>
      <url-pattern>/async/async3</url-pattern>
    </servlet-mapping>
    <servlet>
      <servlet-name>stock</servlet-name>
      <servlet-class>async.AsyncStockServlet</servlet-class>
      <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
      <servlet-name>stock</servlet-name>
      <url-pattern>/async/stockticker</url-pattern>
    </servlet-mapping>
    
    <!-- WebSocket Examples using Deprecated Tomcat 7 API-->
    <servlet>
      <servlet-name>wsEchoStream</servlet-name>
      <servlet-class>websocket.tc7.echo.EchoStream</servlet-class>
    </servlet>
    <servlet-mapping>
      <servlet-name>wsEchoStream</servlet-name>
      <url-pattern>/websocket/tc7/echoStream</url-pattern>
    </servlet-mapping>
    <servlet>
      <servlet-name>wsEchoMessage</servlet-name>
      <servlet-class>websocket.tc7.echo.EchoMessage</servlet-class>
      <!-- Uncomment the following block to increase the default maximum
           WebSocket buffer size from 2MB to 20MB which is required for the
           Autobahn test suite to pass fully. -->
      <!--
      <init-param>
        <param-name>byteBufferMaxSize</param-name>
        <param-value>20971520</param-value>
      </init-param>
      <init-param>
        <param-name>charBufferMaxSize</param-name>
        <param-value>20971520</param-value>
      </init-param>
      -->
    </servlet>
    <servlet-mapping>
      <servlet-name>wsEchoMessage</servlet-name>
      <url-pattern>/websocket/tc7/echoMessage</url-pattern>
    </servlet-mapping>
    <servlet>
        <servlet-name>wsChat</servlet-name>
        <servlet-class>websocket.tc7.chat.ChatWebSocketServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>wsChat</servlet-name>
        <url-pattern>/websocket/tc7/chat</url-pattern>
    </servlet-mapping>
    <servlet>
      <servlet-name>wsSnake</servlet-name>
      <servlet-class>websocket.tc7.snake.SnakeWebSocketServlet</servlet-class>
    </servlet>
    <servlet-mapping>
      <servlet-name>wsSnake</servlet-name>
      <url-pattern>/websocket/tc7/snake</url-pattern>
    </servlet-mapping>
    <!-- Websocket examples -->
    <listener>
        <listener-class>websocket.drawboard.DrawboardContextListener</listener-class>
    </listener>

    <welcome-file-list>
        <welcome-file>index.html</welcome-file>
        <welcome-file>index.xhtml</welcome-file>
        <welcome-file>index.htm</welcome-file>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>

</web-app>
