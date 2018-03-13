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
package org.apache.tomcat.jdbc.pool;

import java.sql.Connection;

/**
 * Interface to be implemented by custom validator classes.
 *
 * @author mpassell
 */
public interface Validator {
    /**
     * Validate a connection and return a boolean to indicate if it's valid.
     *
     * @param connection the Connection object to test
     * @param validateAction the action used. One of {@link PooledConnection#VALIDATE_BORROW},
     *   {@link PooledConnection#VALIDATE_IDLE}, {@link PooledConnection#VALIDATE_INIT} or
     *   {@link PooledConnection#VALIDATE_RETURN}
     * @return true if the connection is valid
     */
    public boolean validate(Connection connection, int validateAction);
}
