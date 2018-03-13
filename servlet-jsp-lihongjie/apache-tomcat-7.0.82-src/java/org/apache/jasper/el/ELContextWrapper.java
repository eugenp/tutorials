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
package org.apache.jasper.el;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

/**
 * Simple ELContextWrapper for runtime evaluation of EL w/ dynamic FunctionMappers
 * 
 * @author jhook
 */
public final class ELContextWrapper extends ELContext {

    private final ELContext target;
    private final FunctionMapper fnMapper;
    
    public ELContextWrapper(ELContext target, FunctionMapper fnMapper) {
        this.target = target;
        this.fnMapper = fnMapper;
    }

    @Override
    public ELResolver getELResolver() {
        return this.target.getELResolver();
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        if (this.fnMapper != null) return this.fnMapper;
        return this.target.getFunctionMapper();
    }

    @Override
    public VariableMapper getVariableMapper() {
        return this.target.getVariableMapper();
    }

    @Override
    @SuppressWarnings("rawtypes") // Can't use Class<?> because API needs to match super-class specification
    public Object getContext(Class key) {
        return this.target.getContext(key);
    }

    @Override
    public Locale getLocale() {
        return this.target.getLocale();
    }

    @Override
    public boolean isPropertyResolved() {
        return this.target.isPropertyResolved();
    }

    @Override
    @SuppressWarnings("rawtypes") // Can't use Class<?> because API needs to match super-class specification
    public void putContext(Class key, Object contextObject) throws NullPointerException {
        this.target.putContext(key, contextObject);
    }

    @Override
    public void setLocale(Locale locale) {
        this.target.setLocale(locale);
    }

    @Override
    public void setPropertyResolved(boolean resolved) {
        this.target.setPropertyResolved(resolved);
    }

}
