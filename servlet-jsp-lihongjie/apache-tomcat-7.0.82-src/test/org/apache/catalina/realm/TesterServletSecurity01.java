/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.catalina.realm;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;

@ServletSecurity(value=@HttpConstraint,
        httpMethodConstraints={
                @HttpMethodConstraint(value="POST",
                        rolesAllowed=TestRealmBase.ROLE1),
                @HttpMethodConstraint(value="PUT", rolesAllowed="*")})
public class TesterServletSecurity01 {
    // Class is NO-OP. It is only used to 'host' the annotation.
}
