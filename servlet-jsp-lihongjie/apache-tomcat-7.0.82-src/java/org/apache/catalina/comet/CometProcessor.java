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


package org.apache.catalina.comet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 * This interface should be implemented by servlets which would like to handle
 * asynchronous IO, receiving events when data is available for reading, and
 * being able to output data without the need for being invoked by the container.
 * Note: When this interface is implemented, the service method of the servlet will
 * never be called, and will be replaced with a begin event.
 */
public interface CometProcessor extends Servlet{

    /**
     * Process the given Comet event.
     *
     * @param event The Comet event that will be processed
     * @throws IOException
     * @throws ServletException
     */
    public void event(CometEvent event)
        throws IOException, ServletException;

}
