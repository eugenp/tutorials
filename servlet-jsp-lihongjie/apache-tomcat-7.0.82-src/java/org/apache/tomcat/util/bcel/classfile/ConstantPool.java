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

import org.apache.tomcat.util.bcel.Const;

/**
 * This class represents the constant pool, i.e., a table of constants, of
 * a parsed classfile. It may contain null references, due to the JVM
 * specification that skips an entry after an 8-byte constant (double,
 * long) entry.  Those interested in generating constant pools
 * programatically should see <a href="../generic/ConstantPoolGen.html">
 * ConstantPoolGen</a>.

 * @see     Constant
 */
public class ConstantPool {

    private final Constant[] constant_pool;


    /**
     * Read constants from given input stream.
     *
     * @param input Input stream
     * @throws IOException
     * @throws ClassFormatException
     */
    ConstantPool(final DataInput input) throws IOException, ClassFormatException {
        final int constant_pool_count = input.readUnsignedShort();
        constant_pool = new Constant[constant_pool_count];
        /* constant_pool[0] is unused by the compiler and may be used freely
         * by the implementation.
         */
        for (int i = 1; i < constant_pool_count; i++) {
            constant_pool[i] = Constant.readConstant(input);
            /* Quote from the JVM specification:
             * "All eight byte constants take up two spots in the constant pool.
             * If this is the n'th byte in the constant pool, then the next item
             * will be numbered n+2"
             *
             * Thus we have to increment the index counter.
             */
            if (constant_pool[i] != null) {
                byte tag = constant_pool[i].getTag();
                if ((tag == Const.CONSTANT_Double) || (tag == Const.CONSTANT_Long)) {
                    i++;
                }
            }
        }
    }


    /**
     * Get constant from constant pool.
     *
     * @param  index Index in constant pool
     * @return Constant value
     * @see    Constant
     */
    public Constant getConstant( final int index ) {
        if (index >= constant_pool.length || index < 0) {
            throw new ClassFormatException("Invalid constant pool reference: " + index
                    + ". Constant pool size is: " + constant_pool.length);
        }
        return constant_pool[index];
    }


    /**
     * Get constant from constant pool and check whether it has the
     * expected type.
     *
     * @param  index Index in constant pool
     * @param  tag Tag of expected constant, i.e., its type
     * @return Constant value
     * @see    Constant
     * @throws  ClassFormatException If the constant is not of the expected type
     */
    public Constant getConstant( final int index, final byte tag ) throws ClassFormatException {
        Constant c;
        c = getConstant(index);
        if (c == null) {
            throw new ClassFormatException("Constant pool at index " + index + " is null.");
        }
        if (c.getTag() != tag) {
            throw new ClassFormatException("Expected class `" + Const.getConstantName(tag)
                    + "' at index " + index + " and got " + c);
        }
        return c;
    }
}
