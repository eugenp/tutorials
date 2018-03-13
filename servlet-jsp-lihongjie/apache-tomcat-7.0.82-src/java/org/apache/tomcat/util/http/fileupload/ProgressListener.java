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
package org.apache.tomcat.util.http.fileupload;

/**
 * The {@link ProgressListener} may be used to display a progress bar
 * or do stuff like that.
 */
public interface ProgressListener {

    /**
     * Updates the listeners status information.
     *
     * @param pBytesRead The total number of bytes, which have been read
     *   so far.
     * @param pContentLength The total number of bytes, which are being
     *   read. May be -1, if this number is unknown.
     * @param pItems The number of the field, which is currently being
     *   read. (0 = no item so far, 1 = first item is being read, ...)
     */
    void update(long pBytesRead, long pContentLength, int pItems);

}
