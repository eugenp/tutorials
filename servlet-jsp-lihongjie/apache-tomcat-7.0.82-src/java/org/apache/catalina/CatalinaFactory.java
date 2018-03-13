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

package org.apache.catalina;

import org.apache.catalina.core.StandardPipeline;

/**
 * Factory class used whenever a default implementation of a component is
 * required. It provides both class names (for the digester) and objects for
 * other components.  The current implementation is as simple as possible. If
 * there is demand it can be extended to support alternative factories and/or
 * alternative defaults.
 *
 * @deprecated There was no demand for this capability and it will be removed in
 *             Tomcat 8.0.x
 */
@Deprecated
public class CatalinaFactory {

    private static CatalinaFactory factory = new CatalinaFactory();

    public static CatalinaFactory getFactory() {
        return factory;
    }

    private CatalinaFactory() {
        // Hide the default constructor
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public String getDefaultPipelineClassName() {
        return StandardPipeline.class.getName();
    }

    public Pipeline createPipeline(Container container) {
        Pipeline pipeline = new StandardPipeline();
        pipeline.setContainer(container);
        return pipeline;
    }
}
