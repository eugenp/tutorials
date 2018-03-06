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
package async;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class Async0 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(Async0.class);

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        if (Boolean.TRUE.equals(req.getAttribute("dispatch"))) {
            log.info("Received dispatch, completing on the worker thread.");
            log.info("After complete called started:"+req.isAsyncStarted());
            resp.getWriter().write("Async dispatch worked:+"+System.currentTimeMillis()+"\n");
        } else {
            resp.setContentType("text/plain");
            final AsyncContext actx = req.startAsync();
            actx.setTimeout(Long.MAX_VALUE);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        req.setAttribute("dispatch", Boolean.TRUE);
                        Thread.currentThread().setName("Async0-Thread");
                        log.info("Putting AsyncThread to sleep");
                        Thread.sleep(2*1000);
                        log.info("Dispatching");
                        actx.dispatch();
                    }catch (InterruptedException x) {
                        log.error("Async1",x);
                    }catch (IllegalStateException x) {
                        log.error("Async1",x);
                    }
                }
            };
            Thread t = new Thread(run);
            t.start();
        }
    }
}
