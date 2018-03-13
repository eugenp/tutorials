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
package org.apache.naming.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.apache.naming.StringManager;

public class ClasspathURLStreamHandler extends URLStreamHandler {

    private static final StringManager sm =
            StringManager.getManager(Constants.Package);


    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        String path = u.getPath();

        // Thread context class loader first
        URL classpathUrl = Thread.currentThread().getContextClassLoader().getResource(path);
        if (classpathUrl == null) {
            // This class's class loader if no joy with the tccl
            classpathUrl = ClasspathURLStreamHandler.class.getResource(path);
        }

        if (classpathUrl == null) {
            throw new FileNotFoundException(sm.getString("classpathUrlStreamHandler.notFound", u));
        }

        return classpathUrl.openConnection();
    }
}
