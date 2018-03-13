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

import org.apache.tomcat.util.bcel.Const;

public class SimpleElementValue extends ElementValue
{
    private final int index;

    SimpleElementValue(final int type, final int index, final ConstantPool cpool) {
        super(type, cpool);
        this.index = index;
    }

    /**
     * @return Value entry index in the cpool
     */
    public int getIndex()
    {
        return index;
    }


    // Whatever kind of value it is, return it as a string
    @Override
    public String stringifyValue()
    {
        final ConstantPool cpool = super.getConstantPool();
        final int _type = super.getType();
        switch (_type)
        {
        case PRIMITIVE_INT:
            final ConstantInteger c = (ConstantInteger) cpool.getConstant(getIndex(),
                    Const.CONSTANT_Integer);
            return Integer.toString(c.getBytes());
        case PRIMITIVE_LONG:
            final ConstantLong j = (ConstantLong) cpool.getConstant(getIndex(),
                    Const.CONSTANT_Long);
            return Long.toString(j.getBytes());
        case PRIMITIVE_DOUBLE:
            final ConstantDouble d = (ConstantDouble) cpool.getConstant(getIndex(),
                    Const.CONSTANT_Double);
            return Double.toString(d.getBytes());
        case PRIMITIVE_FLOAT:
            final ConstantFloat f = (ConstantFloat) cpool.getConstant(getIndex(),
                    Const.CONSTANT_Float);
            return Float.toString(f.getBytes());
        case PRIMITIVE_SHORT:
            final ConstantInteger s = (ConstantInteger) cpool.getConstant(getIndex(),
                    Const.CONSTANT_Integer);
            return Integer.toString(s.getBytes());
        case PRIMITIVE_BYTE:
            final ConstantInteger b = (ConstantInteger) cpool.getConstant(getIndex(),
                    Const.CONSTANT_Integer);
            return Integer.toString(b.getBytes());
        case PRIMITIVE_CHAR:
            final ConstantInteger ch = (ConstantInteger) cpool.getConstant(
                    getIndex(), Const.CONSTANT_Integer);
            return String.valueOf((char)ch.getBytes());
        case PRIMITIVE_BOOLEAN:
            final ConstantInteger bo = (ConstantInteger) cpool.getConstant(
                    getIndex(), Const.CONSTANT_Integer);
            if (bo.getBytes() == 0) {
                return "false";
            }
            return "true";
        case STRING:
            final ConstantUtf8 cu8 = (ConstantUtf8) cpool.getConstant(getIndex(),
                    Const.CONSTANT_Utf8);
            return cu8.getBytes();
        default:
            throw new RuntimeException("SimpleElementValue class does not know how to stringify type " + _type);
        }
    }
}
