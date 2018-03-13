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

import java.sql.SQLException;

public class PoolExhaustedException extends SQLException {

    private static final long serialVersionUID = 3501536931777262475L;

    public PoolExhaustedException() {
    }

    public PoolExhaustedException(String reason) {
        super(reason);
    }

    public PoolExhaustedException(Throwable cause) {
        super(cause);
    }

    public PoolExhaustedException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public PoolExhaustedException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public PoolExhaustedException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public PoolExhaustedException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public PoolExhaustedException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }

}
