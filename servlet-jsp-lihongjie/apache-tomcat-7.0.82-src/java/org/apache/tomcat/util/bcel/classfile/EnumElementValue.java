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

public class EnumElementValue extends ElementValue
{
    private final int valueIdx;

    EnumElementValue(final int type, final int valueIdx, final ConstantPool cpool) {
        super(type, cpool);
        if (type != ENUM_CONSTANT)
            throw new RuntimeException(
                    "Only element values of type enum can be built with this ctor - type specified: " + type);
        this.valueIdx = valueIdx;
    }

    @Override
    public String stringifyValue()
    {
        final ConstantUtf8 cu8 = (ConstantUtf8) super.getConstantPool().getConstant(valueIdx,
                Const.CONSTANT_Utf8);
        return cu8.getBytes();
    }
}
