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
package org.apache.tomcat.websocket;

import java.util.List;

import javax.websocket.Extension;

import org.apache.tomcat.util.res.StringManager;

public class TransformationFactory {

    private static final StringManager sm = StringManager.getManager(Constants.PACKAGE_NAME);

    private static final TransformationFactory factory = new TransformationFactory();

    private TransformationFactory() {
        // Hide default constructor
    }

    public static TransformationFactory getInstance() {
        return factory;
    }

    public Transformation create(String name, List<List<Extension.Parameter>> preferences,
            boolean isServer) {
        if (PerMessageDeflate.NAME.equals(name)) {
            return PerMessageDeflate.negotiate(preferences, isServer);
        }
        if (Constants.ALLOW_UNSUPPORTED_EXTENSIONS) {
            return null;
        } else {
            throw new IllegalArgumentException(
                    sm.getString("transformerFactory.unsupportedExtension", name));
        }
    }
}
