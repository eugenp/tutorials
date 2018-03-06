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

package org.apache.el;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.el.ELContext;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;

import org.apache.el.lang.ELSupport;
import org.apache.el.util.MessageFactory;
import org.apache.el.util.ReflectionUtil;


public final class ValueExpressionLiteral extends ValueExpression implements
        Externalizable {

    private static final long serialVersionUID = 1L;

    private Object value;

    private Class<?> expectedType;

    public ValueExpressionLiteral() {
        super();
    }

    public ValueExpressionLiteral(Object value, Class<?> expectedType) {
        this.value = value;
        this.expectedType = expectedType;
    }

    @Override
    public Object getValue(ELContext context) {
        if (this.expectedType != null) {
            return ELSupport.coerceToType(this.value, this.expectedType);
        }
        return this.value;
    }

    @Override
    public void setValue(ELContext context, Object value) {
        throw new PropertyNotWritableException(MessageFactory.get(
                "error.value.literal.write", this.value));
    }

    @Override
    public boolean isReadOnly(ELContext context) {
        return true;
    }

    @Override
    public Class<?> getType(ELContext context) {
        return (this.value != null) ? this.value.getClass() : null;
    }

    @Override
    public Class<?> getExpectedType() {
        return this.expectedType;
    }

    @Override
    public String getExpressionString() {
        return (this.value != null) ? this.value.toString() : null;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ValueExpressionLiteral && this
                .equals((ValueExpressionLiteral) obj));
    }

    public boolean equals(ValueExpressionLiteral ve) {
        return (ve != null && (this.value != null && ve.value != null && (this.value == ve.value || this.value
                .equals(ve.value))));
    }

    @Override
    public int hashCode() {
        return (this.value != null) ? this.value.hashCode() : 0;
    }

    @Override
    public boolean isLiteralText() {
        return true;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.value);
        out.writeUTF((this.expectedType != null) ? this.expectedType.getName()
                : "");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        this.value = in.readObject();
        String type = in.readUTF();
        if (!"".equals(type)) {
            this.expectedType = ReflectionUtil.forName(type);
        }
    }
}
