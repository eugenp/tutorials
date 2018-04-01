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
import java.io.IOException;

public abstract class ElementValue
{
    private final int type;

    private final ConstantPool cpool;


    ElementValue(final int type, final ConstantPool cpool) {
        this.type = type;
        this.cpool = cpool;
    }

    public abstract String stringifyValue();

    public static final byte STRING            = 's';
    public static final byte ENUM_CONSTANT     = 'e';
    public static final byte CLASS             = 'c';
    public static final byte ANNOTATION        = '@';
    public static final byte ARRAY             = '[';
    public static final byte PRIMITIVE_INT     = 'I';
    public static final byte PRIMITIVE_BYTE    = 'B';
    public static final byte PRIMITIVE_CHAR    = 'C';
    public static final byte PRIMITIVE_DOUBLE  = 'D';
    public static final byte PRIMITIVE_FLOAT   = 'F';
    public static final byte PRIMITIVE_LONG    = 'J';
    public static final byte PRIMITIVE_SHORT   = 'S';
    public static final byte PRIMITIVE_BOOLEAN = 'Z';

    public static ElementValue readElementValue(final DataInput input, final ConstantPool cpool) throws IOException
    {
        final byte type = input.readByte();
        switch (type)
        {
            case PRIMITIVE_BYTE:
            case PRIMITIVE_CHAR:
            case PRIMITIVE_DOUBLE:
            case PRIMITIVE_FLOAT:
            case PRIMITIVE_INT:
            case PRIMITIVE_LONG:
            case PRIMITIVE_SHORT:
            case PRIMITIVE_BOOLEAN:
            case STRING:
                return new SimpleElementValue(type, input.readUnsignedShort(), cpool);

            case ENUM_CONSTANT:
                input.readUnsignedShort();    // Unused type_index
                return new EnumElementValue(ENUM_CONSTANT, input.readUnsignedShort(), cpool);

            case CLASS:
                return new ClassElementValue(CLASS, input.readUnsignedShort(), cpool);

            case ANNOTATION:
                // TODO isRuntimeVisible
                return new AnnotationElementValue(ANNOTATION, new AnnotationEntry(input, cpool), cpool);

            case ARRAY:
                final int numArrayVals = input.readUnsignedShort();
                final ElementValue[] evalues = new ElementValue[numArrayVals];
                for (int j = 0; j < numArrayVals; j++)
                {
                    evalues[j] = ElementValue.readElementValue(input, cpool);
                }
                return new ArrayElementValue(ARRAY, evalues, cpool);

            default:
                throw new ClassFormatException(
                        "Unexpected element value kind in annotation: " + type);
        }
    }

    final ConstantPool getConstantPool() {
        return cpool;
    }

    final int getType() {
        return type;
    }
}
