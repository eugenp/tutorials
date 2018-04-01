/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat;

import java.lang.instrument.ClassFileTransformer;

/**
 * Specifies a class loader capable of being decorated with
 * {@link ClassFileTransformer}s. These transformers can instrument
 * (or weave) the byte code of classes loaded through this class loader
 * to alter their behavior. Currently only
 * {@link org.apache.catalina.loader.WebappClassLoaderBase} implements this
 * interface. This allows web application frameworks or JPA providers
 * bundled with a web application to instrument web application classes
 * as necessary.
 * <p>
 * You should always program against the methods of this interface
 * (whether using reflection or otherwise). The methods in
 * {@code WebappClassLoaderBase} are protected by the default security
 * manager if one is in use.
 *
 * @since 8.0, 7.0.64
 */
public interface InstrumentableClassLoader {

    /**
     * Adds the specified class file transformer to this class loader. The
     * transformer will then be able to instrument the bytecode of any
     * classes loaded by this class loader after the invocation of this
     * method.
     *
     * @param transformer The transformer to add to the class loader
     * @throws IllegalArgumentException if the {@literal transformer} is null.
     */
    void addTransformer(ClassFileTransformer transformer);

    /**
     * Removes the specified class file transformer from this class loader.
     * It will no longer be able to instrument the byte code of any classes
     * loaded by the class loader after the invocation of this method.
     * However, any classes already instrumented by this transformer before
     * this method call will remain in their instrumented state.
     *
     * @param transformer The transformer to remove
     */
    void removeTransformer(ClassFileTransformer transformer);

    /**
     * Returns a copy of this class loader without any class file
     * transformers. This is a tool often used by Java Persistence API
     * providers to inspect entity classes in the absence of any
     * instrumentation, something that can't be guaranteed within the
     * context of a {@link ClassFileTransformer}'s
     * {@link ClassFileTransformer#transform(ClassLoader, String, Class,
     * java.security.ProtectionDomain, byte[]) transform} method.
     * <p>
     * The returned class loader's resource cache will have been cleared
     * so that classes already instrumented will not be retained or
     * returned.
     *
     * @return the transformer-free copy of this class loader.
     */
    ClassLoader copyWithoutTransformers();

}
