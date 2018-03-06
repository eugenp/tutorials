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


package org.apache.catalina.ant;


import org.apache.tools.ant.BuildException;


/**
 * Ant task that implements the <code>/sessions</code> command
 * supported by the Tomcat manager application.
 *
 * @author Vivek Chopra
 */
public class SessionsTask extends AbstractCatalinaCommandTask {


    protected String idle = null;
    
    public String getIdle() {
        return this.idle;
    }
    
    public void setIdle(String idle) {
        this.idle = idle;
    }
    
    @Override
    public StringBuilder createQueryString(String command) {
        StringBuilder buffer = super.createQueryString(command);
        if (path != null && idle != null) {
            buffer.append("&idle=");
            buffer.append(this.idle);
        }
        return buffer;
    }
    
    /**
     * Execute the requested operation.
     *
     * @exception BuildException if an error occurs
     */
    @Override
    public void execute() throws BuildException {

        super.execute();
        execute(createQueryString("/sessions").toString());
        
    }

}
