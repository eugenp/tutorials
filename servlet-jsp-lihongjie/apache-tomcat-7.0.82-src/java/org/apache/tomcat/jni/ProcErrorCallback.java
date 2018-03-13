/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.tomcat.jni;

/** ProcErrorCallback Interface
 *
 * @author Mladen Turk
 */
public interface ProcErrorCallback {

    /**
     * Called in the child process if APR encounters an error
     * in the child prior to running the specified program.
     * @param pool Pool associated with the apr_proc_t.  If your child
     *             error function needs user data, associate it with this
     *             pool.
     * @param err APR error code describing the error
     * @param description Text description of type of processing which failed
     */
    public void callback(long pool, int err, String description);
}
