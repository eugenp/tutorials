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
package org.apache.catalina.core;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * TODO SERVLET3 - async 
 * @author fhanik
 *
 */
public class AsyncListenerWrapper {

    private AsyncListener listener = null;
    private ServletRequest servletRequest = null;
    private ServletResponse servletResponse = null;


    public void fireOnStartAsync(AsyncEvent event) throws IOException {
        listener.onStartAsync(customizeEvent(event));
    }

    
    public void fireOnComplete(AsyncEvent event) throws IOException {
        listener.onComplete(customizeEvent(event));
    }


    public void fireOnTimeout(AsyncEvent event) throws IOException {
        listener.onTimeout(customizeEvent(event));
    }

    
    public void fireOnError(AsyncEvent event) throws IOException {
        listener.onError(customizeEvent(event));
    }


    public AsyncListener getListener() {
        return listener;
    }

    
    public void setListener(AsyncListener listener) {
        this.listener = listener;
    }


    public void setServletRequest(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }


    public void setServletResponse(ServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }


    private AsyncEvent customizeEvent(AsyncEvent event) {
        if (servletRequest != null && servletResponse != null) {
            return new AsyncEvent(event.getAsyncContext(), servletRequest, servletResponse,
                    event.getThrowable());
        } else {
            return event;
        }
    }
}
