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
package javax.annotation.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @since Common Annotations 1.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceDefinition {
    String className();
    String name();
    String description() default "";
    String url() default "";
    String user() default "";
    String password() default "";
    String databaseName() default "";
    int portNumber() default -1;
    String serverName() default "localhost";
    int isolationLevel() default -1;
    boolean transactional() default true;
    int initialPoolSize() default -1;
    int maxPoolSize() default -1;
    int minPoolSize() default -1;
    int maxIdleTime() default -1;
    int maxStatements() default -1;
    String[] properties() default {};
    int loginTimeout() default 0;
}
