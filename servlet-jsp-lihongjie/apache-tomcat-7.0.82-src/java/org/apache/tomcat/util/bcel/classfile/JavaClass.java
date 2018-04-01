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
 *
 */
package org.apache.tomcat.util.bcel.classfile;

/**
 * Represents a Java class, i.e., the data structures, constant pool,
 * fields, methods and commands contained in a Java .class file.
 * See <a href="http://docs.oracle.com/javase/specs/">JVM specification</a> for details.
 * The intent of this class is to represent a parsed or otherwise existing
 * class file.  Those interested in programatically generating classes
 * should see the <a href="../generic/ClassGen.html">ClassGen</a> class.
 */
public class JavaClass {

    private final int access_flags;
    private final String class_name;
    private final String superclass_name;
    private final String[] interface_names;
    private final Annotations runtimeVisibleAnnotations; // "RuntimeVisibleAnnotations" attribute defined in the class

    /**
     * Constructor gets all contents as arguments.
     *
     * @param class_name Name of this class.
     * @param superclass_name Name of this class's superclass.
     * @param access_flags Access rights defined by bit flags
     * @param constant_pool Array of constants
     * @param interfaces Implemented interfaces
     * @param runtimeVisibleAnnotations "RuntimeVisibleAnnotations" attribute defined on the Class, or null
     */
    JavaClass(final String class_name, final String superclass_name,
            final int access_flags, final ConstantPool constant_pool, final String[] interface_names,
            final Annotations runtimeVisibleAnnotations) {
        this.access_flags = access_flags;
        this.runtimeVisibleAnnotations = runtimeVisibleAnnotations;
        this.class_name = class_name;
        this.superclass_name = superclass_name;
        this.interface_names = interface_names;
    }

    /**
     * @return Access flags of the object aka. "modifiers".
     */
    public final int getAccessFlags() {
        return access_flags;
    }

    /**
     * Return annotations entries from "RuntimeVisibleAnnotations" attribute on
     * the class, if there is any.
     *
     * @return An array of entries or {@code null}
     */
    public AnnotationEntry[] getAnnotationEntries() {
        if (runtimeVisibleAnnotations != null) {
            return runtimeVisibleAnnotations.getAnnotationEntries();
        }
        return null;
    }

    /**
     * @return Class name.
     */
    public String getClassName() {
        return class_name;
    }


    /**
     * @return Names of implemented interfaces.
     */
    public String[] getInterfaceNames() {
        return interface_names;
    }


    /**
     * returns the super class name of this class. In the case that this class is
     * java.lang.Object, it will return itself (java.lang.Object). This is probably incorrect
     * but isn't fixed at this time to not break existing clients.
     *
     * @return Superclass name.
     */
    public String getSuperclassName() {
        return superclass_name;
    }
}
