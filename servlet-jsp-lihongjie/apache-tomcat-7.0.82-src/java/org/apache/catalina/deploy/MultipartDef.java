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


package org.apache.catalina.deploy;

import java.io.Serializable;


/**
 * Representation of a the multipart configuration for a servlet.
 */
public class MultipartDef implements Serializable {

    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------- Properties
    private String location;
   
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
    private String maxFileSize;

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
    
    
    private String maxRequestSize;

    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    
    private String fileSizeThreshold;
    
    public String getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    public void setFileSizeThreshold(String fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }


    // ---------------------------------------------------------- Object methods

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((fileSizeThreshold == null) ? 0 : fileSizeThreshold
                        .hashCode());
        result = prime * result
                + ((location == null) ? 0 : location.hashCode());
        result = prime * result
                + ((maxFileSize == null) ? 0 : maxFileSize.hashCode());
        result = prime * result
                + ((maxRequestSize == null) ? 0 : maxRequestSize.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MultipartDef)) {
            return false;
        }
        MultipartDef other = (MultipartDef) obj;
        if (fileSizeThreshold == null) {
            if (other.fileSizeThreshold != null) {
                return false;
            }
        } else if (!fileSizeThreshold.equals(other.fileSizeThreshold)) {
            return false;
        }
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        if (maxFileSize == null) {
            if (other.maxFileSize != null) {
                return false;
            }
        } else if (!maxFileSize.equals(other.maxFileSize)) {
            return false;
        }
        if (maxRequestSize == null) {
            if (other.maxRequestSize != null) {
                return false;
            }
        } else if (!maxRequestSize.equals(other.maxRequestSize)) {
            return false;
        }
        return true;
    }

}
