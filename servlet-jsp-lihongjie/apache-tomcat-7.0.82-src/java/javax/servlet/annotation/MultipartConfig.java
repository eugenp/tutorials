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
package javax.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to indicate that the {@link javax.servlet.Servlet} on
 * which it is declared expects requests to made using the {@code
 * multipart/form-data} MIME type. <br>
 * <br>
 * 
 * {@link javax.servlet.http.Part} components of a given {@code
 * multipart/form-data} request are retrieved by a Servlet annotated with
 * {@code MultipartConfig} by calling
 * {@link javax.servlet.http.HttpServletRequest#getPart} or
 * {@link javax.servlet.http.HttpServletRequest#getParts}.<br>
 * <br>
 * 
 * E.g. <code>@WebServlet("/upload")}</code><br>
 * 
 * <code>@MultipartConfig()</code> <code>public class UploadServlet extends
 * HttpServlet ... } </code><br>
 * 
 * @since Servlet 3.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartConfig {

    /**
     * @return location in which the Container stores temporary files
     */
    String location() default "";

    /**
     * @return the maximum size allowed for uploaded files (in bytes)
     */
    long maxFileSize() default -1L;

    /**
     * @return the maximum size of the request allowed for {@code
     *         multipart/form-data}
     */
    long maxRequestSize() default -1L;

    /**
     * @return the size threshold at which the file will be written to the disk
     */
    int fileSizeThreshold() default 0;
}
