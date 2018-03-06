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

/**
 *    <p>
 *      An implementation of
 *      {@link org.apache.tomcat.util.http.fileupload.FileUpload FileUpload}
 *      for use in servlets conforming to JSR 53. This implementation requires
 *      only access to the servlet's current <code>HttpServletRequest</code>
 *      instance, and a suitable
 *      {@link org.apache.tomcat.util.http.fileupload.FileItemFactory FileItemFactory}
 *      implementation, such as
 *      {@link org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory DiskFileItemFactory}.
 *    </p>
 *    <p>
 *      The following code fragment demonstrates typical usage.
 *    </p>
 * <pre>
 *        DiskFileItemFactory factory = new DiskFileItemFactory();
 *        // Configure the factory here, if desired.
 *        ServletFileUpload upload = new ServletFileUpload(factory);
 *        // Configure the uploader here, if desired.
 *        List fileItems = upload.parseRequest(request);
 * </pre>
 *    <p>
 *      Please see the FileUpload
 *      <a href="http://commons.apache.org/fileupload/using.html" target="_top">User Guide</a>
 *      for further details and examples of how to use this package.
 *    </p>
 */
package org.apache.tomcat.util.http.fileupload.servlet;
