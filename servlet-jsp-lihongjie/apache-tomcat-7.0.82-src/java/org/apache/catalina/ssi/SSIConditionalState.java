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
package org.apache.catalina.ssi;


/**
 * This class is used by SSIMediator and SSIConditional to keep track of state
 * information necessary to process the nested conditional commands ( if, elif,
 * else, endif ).
 * 
 * @author Dan Sandberg
 * @author Paul Speed
 */
class SSIConditionalState {
    /**
     * Set to true if the current conditional has already been completed, i.e.:
     * a branch was taken.
     */
    boolean branchTaken = false;
    /**
     * Counts the number of nested false branches.
     */
    int nestingCount = 0;
    /**
     * Set to true if only conditional commands ( if, elif, else, endif )
     * should be processed.
     */
    boolean processConditionalCommandsOnly = false;
}