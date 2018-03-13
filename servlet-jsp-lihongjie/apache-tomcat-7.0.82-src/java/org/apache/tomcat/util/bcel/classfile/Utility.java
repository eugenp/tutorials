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
package org.apache.tomcat.util.bcel.classfile;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

import org.apache.tomcat.util.bcel.Const;

/**
 * Utility functions that do not really belong to any class in particular.
 */
final class Utility {

    private Utility() {
        // Hide default constructor
    }

    /**
     * Shorten long class name <em>str</em>, i.e., chop off the <em>prefix</em>,
     * if the
     * class name starts with this string and the flag <em>chopit</em> is true.
     * Slashes <em>/</em> are converted to dots <em>.</em>.
     *
     * @param str The long class name
     * @return Compacted class name
     */
    static String compactClassName(final String str) {
        return str.replace('/', '.'); // Is `/' on all systems, even DOS
    }

    static String getClassName(final ConstantPool constant_pool, final int index) {
        Constant c = constant_pool.getConstant(index, Const.CONSTANT_Class);
        int i = ((ConstantClass) c).getNameIndex();

        // Finally get the string from the constant pool
        c = constant_pool.getConstant(i, Const.CONSTANT_Utf8);
        String name = ((ConstantUtf8) c).getBytes();

        return compactClassName(name);
    }

    static void skipFully(final DataInput file, final int length) throws IOException {
        int total = file.skipBytes(length);
        if (total != length) {
            throw new EOFException();
        }
    }

    static void swallowFieldOrMethod(final DataInput file)
            throws IOException {
        // file.readUnsignedShort(); // Unused access flags
        // file.readUnsignedShort(); // name index
        // file.readUnsignedShort(); // signature index
        skipFully(file, 6);

        int attributes_count = file.readUnsignedShort();
        for (int i = 0; i < attributes_count; i++) {
            swallowAttribute(file);
        }
    }

    static void swallowAttribute(final DataInput file)
            throws IOException {
        //file.readUnsignedShort();   // Unused name index
        skipFully(file, 2);
        // Length of data in bytes
        int length = file.readInt();
        skipFully(file, length);
    }
}
