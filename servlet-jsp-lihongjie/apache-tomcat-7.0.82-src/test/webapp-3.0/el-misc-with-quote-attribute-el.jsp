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
  <head><title>Misc EL test cases</title></head>
  <body>
    <p>00-\\\"\${'hello world'}</p>
    <p>01-\\\"\\${'hello world'}</p>
    <tags:echo echo="02-\\\"\${'hello world'}" />
    <tags:echo echo="03-\\\"\\${'hello world'}" />
    <tags:echo echo="${'2'}az-04" />
    <tags:echo echo="05-a${'2'}z" />
    <tags:echo echo="06-az${'2'}" />
    <tags:echo echo="${\"2\"}az-07" />
    <tags:echo echo="08-a${\"2\"}z" />
    <tags:echo echo="09-az${\"2\"}" />
    <tags:echo echo="10-\${'foo'}${'bar'}" />
    <tags:echo echo="11-${\"\\\\\"}\"}" />
    <tags:echo echo="12-${'foo'}\\${'bar'}\\${'baz'}" />
    <tags:echo echo="13-${'foo'}\\${\"bar\"}\\${'baz'}" />
    <tags:echo echo="14-${\"foo\"}\\${'bar'}\\${\"baz\"}" />
    <tags:echo echo='15-${\'foo\'}\\${"bar"}\\${\'baz\'}' />
    <tags:echo echo='16-${"foo"}\\${\'bar\'}\\${"baz"}' />
    <tags:echo echo='17-${"foo"}\\${"&"}${"apos;bar"}${"&"}${"apos;"}\\${"&"}${"quot;baz"}${"&"}${"quot;"}' />
  </body>
</html>