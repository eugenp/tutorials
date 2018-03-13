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
package javax.servlet;

import javax.servlet.annotation.MultipartConfig;

/**
 * @since Servlet 3.0
 * TODO SERVLET3 - Add comments
 */
public class MultipartConfigElement {
    
    private final String location;// = "";
    private final long maxFileSize;// = -1;
    private final long maxRequestSize;// = -1;
    private final int fileSizeThreshold;// = 0;
    
    public MultipartConfigElement(String location) {
        // Keep empty string default if location is null
        if (location != null) {
            this.location = location;
        } else {
            this.location = "";
        }
        this.maxFileSize = -1;
        this.maxRequestSize = -1;
        this.fileSizeThreshold = 0;
    }
    
    public MultipartConfigElement(String location, long maxFileSize,
            long maxRequestSize, int fileSizeThreshold) {
        // Keep empty string default if location is null
        if (location != null) {
            this.location = location;
        } else {
            this.location = "";
        }
        this.maxFileSize = maxFileSize;
        this.maxRequestSize = maxRequestSize;
        // Avoid threshold values of less than zero as they cause trigger NPEs
        // in the Commons FileUpload port for fields that have no data.
        if (fileSizeThreshold > 0) {
            this.fileSizeThreshold = fileSizeThreshold;
        } else {
            this.fileSizeThreshold = 0;
        }
    }
    
    public MultipartConfigElement(MultipartConfig annotation) {
        location = annotation.location();
        maxFileSize = annotation.maxFileSize();
        maxRequestSize = annotation.maxRequestSize();
        fileSizeThreshold = annotation.fileSizeThreshold();
    }
    
    public String getLocation() {
        return location;
    }
    
    public long getMaxFileSize() {
        return maxFileSize;
    }
    
    public long getMaxRequestSize() {
        return maxRequestSize;
    }
    
    public int getFileSizeThreshold() {
        return fileSizeThreshold;
    }
}