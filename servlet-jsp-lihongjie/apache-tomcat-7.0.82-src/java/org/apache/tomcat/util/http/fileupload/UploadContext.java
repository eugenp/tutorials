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
 * Enhanced access to the request information needed for file uploads,
 * which fixes the Content Length data access in {@link RequestContext}.
 *
 * The reason of introducing this new interface is just for backward compatibility
 * and it might vanish for a refactored 2.x version moving the new method into
 * RequestContext again.
 *
 * @since 1.3
 */
public interface UploadContext extends RequestContext {

    /**
     * Retrieve the content length of the request.
     *
     * @return The content length of the request.
     * @since 1.3
     */
    long contentLength();

}
