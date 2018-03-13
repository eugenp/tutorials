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

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.bcel.Const;

/**
 * represents one annotation in the annotation table
 */
public class AnnotationEntry {

    private final int type_index;
    private final ConstantPool constant_pool;

    private final List<ElementValuePair> element_value_pairs;

    /*
     * Creates an AnnotationEntry from a DataInputStream
     *
     * @param input
     * @param constant_pool
     * @throws IOException
     */
    AnnotationEntry(final DataInput input, final ConstantPool constant_pool) throws IOException {

        this.constant_pool = constant_pool;

        type_index = input.readUnsignedShort();
        final int num_element_value_pairs = input.readUnsignedShort();

        element_value_pairs = new ArrayList<ElementValuePair>(num_element_value_pairs);
        for (int i = 0; i < num_element_value_pairs; i++) {
            element_value_pairs.add(new ElementValuePair(input, constant_pool));
        }
    }

    /**
     * @return the annotation type name
     */
    public String getAnnotationType() {
        final ConstantUtf8 c = (ConstantUtf8) constant_pool.getConstant(type_index, Const.CONSTANT_Utf8);
        return c.getBytes();
    }

    /**
     * @return the element value pairs in this annotation entry
     */
    public List<ElementValuePair> getElementValuePairs() {
        return element_value_pairs;
    }
}
