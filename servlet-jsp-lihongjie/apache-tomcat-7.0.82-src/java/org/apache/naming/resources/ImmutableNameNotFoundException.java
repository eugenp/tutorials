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

package org.apache.naming.resources;

import javax.naming.Name;
import javax.naming.NameNotFoundException;

/**
 * Immutable exception to avoid useless object creation by the proxy context.
 * This should be used only by the proxy context. Actual contexts should return
 * properly populated exceptions.
 * 
 * @author <a href="mailto:remm@apache.org">Remy Maucherat</a>
 */
public class ImmutableNameNotFoundException
    extends NameNotFoundException {

    private static final long serialVersionUID = 1L;
    
    @Override
    public void appendRemainingComponent(String name) {/*NOOP*/}
    @Override
    public void appendRemainingName(Name name) {/*NOOP*/}
    @Override
    public void setRemainingName(Name name) {/*NOOP*/}
    @Override
    public void setResolvedName(Name name) {/*NOOP*/}
    @Override
    public void setRootCause(Throwable e) {/*NOOP*/}

    @Override
    public synchronized Throwable fillInStackTrace() {
        // This class does not provide a stack trace
        return this;
    }
}
