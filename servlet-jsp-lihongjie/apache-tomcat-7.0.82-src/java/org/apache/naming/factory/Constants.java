/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 


package org.apache.naming.factory;


/**
 * Static constants for this package.
 */

public final class Constants {

    public static final String Package = "org.apache.naming.factory";

    public static final String DEFAULT_RESOURCE_FACTORY = 
        Package + ".ResourceFactory";

    public static final String DEFAULT_RESOURCE_LINK_FACTORY = 
        Package + ".ResourceLinkFactory";

    public static final String DEFAULT_TRANSACTION_FACTORY = 
        Package + ".TransactionFactory";

    public static final String DEFAULT_RESOURCE_ENV_FACTORY = 
        Package + ".ResourceEnvFactory";

    public static final String DEFAULT_EJB_FACTORY = 
        Package + ".EjbFactory";

    public static final String DEFAULT_SERVICE_FACTORY = 
        Package + ".webservices.ServiceRefFactory";

    public static final String DEFAULT_HANDLER_FACTORY = 
        Package + ".HandlerFactory";

    public static final String DBCP_DATASOURCE_FACTORY = 
        "org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory";

    public static final String OPENEJB_EJB_FACTORY = 
        Package + ".OpenEjbFactory";

    public static final String FACTORY = "factory";

}
