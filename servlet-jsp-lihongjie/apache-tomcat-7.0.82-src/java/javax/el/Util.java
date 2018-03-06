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
package javax.el;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Util {

    /**
     * Checks whether the supplied Throwable is one that needs to be
     * rethrown and swallows all others.
     * @param t the Throwable to check
     */
    static void handleThrowable(Throwable t) {
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath) t;
        }
        if (t instanceof VirtualMachineError) {
            throw (VirtualMachineError) t;
        }
        // All other instances of Throwable will be silently swallowed
    }


    static String message(ELContext context, String name, Object... props) {
        Locale locale = null;
        if (context != null) {
            locale = context.getLocale();
        }
        if (locale == null) {
            locale = Locale.getDefault();
            if (locale == null) {
                return "";
            }
        }
        ResourceBundle bundle = ResourceBundle.getBundle(
                "javax.el.LocalStrings", locale);
        try {
            String template = bundle.getString(name);
            if (props != null) {
                template = MessageFormat.format(template, props);
            }
            return template;
        } catch (MissingResourceException e) {
            return "Missing Resource: '" + name + "' for Locale "
                    + locale.getDisplayName();
        }
    }


    private static final CacheValue nullTcclFactory = new CacheValue();
    private static final ConcurrentMap<CacheKey, CacheValue> factoryCache =
            new ConcurrentHashMap<CacheKey, CacheValue>();

    /**
     * Provides a per class loader cache of ExpressionFactory instances without
     * pinning any in memory as that could trigger a memory leak.
     */
    static ExpressionFactory getExpressionFactory() {

        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        CacheValue cacheValue = null;
        ExpressionFactory factory = null;

        if (tccl == null) {
            cacheValue = nullTcclFactory;
        } else {
            CacheKey key = new CacheKey(tccl);
            cacheValue = factoryCache.get(key);
            if (cacheValue == null) {
                CacheValue newCacheValue = new CacheValue();
                cacheValue = factoryCache.putIfAbsent(key, newCacheValue);
                if (cacheValue == null) {
                    cacheValue = newCacheValue;
                }
            }
        }

        final Lock readLock = cacheValue.getLock().readLock();
        readLock.lock();
        try {
            factory = cacheValue.getExpressionFactory();
        } finally {
            readLock.unlock();
        }

        if (factory == null) {
            final Lock writeLock = cacheValue.getLock().writeLock();
            try {
                writeLock.lock();
                factory = cacheValue.getExpressionFactory();
                if (factory == null) {
                    factory = ExpressionFactory.newInstance();
                    cacheValue.setExpressionFactory(factory);
                }
            } finally {
                writeLock.unlock();
            }
        }

        return factory;
    }


    /**
     * Key used to cache default ExpressionFactory information per class
     * loader. The class loader reference is never {@code null}, because
     * {@code null} tccl is handled separately.
     */
    private static class CacheKey {
        private final int hash;
        private final WeakReference<ClassLoader> ref;

        public CacheKey(ClassLoader key) {
            hash = key.hashCode();
            ref = new WeakReference<ClassLoader>(key);
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            ClassLoader thisKey = ref.get();
            if (thisKey == null) {
                return false;
            }
            return thisKey == ((CacheKey) obj).ref.get();
        }
    }

    private static class CacheValue {
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private WeakReference<ExpressionFactory> ref;

        public CacheValue() {
        }

        public ReadWriteLock getLock() {
            return lock;
        }

        public ExpressionFactory getExpressionFactory() {
            return ref != null ? ref.get() : null;
        }

        public void setExpressionFactory(ExpressionFactory factory) {
            ref = new WeakReference<ExpressionFactory>(factory);
        }
    }


    /*
     * This method duplicates code in org.apache.el.util.ReflectionUtil. When
     * making changes keep the code in sync.
     */
    static Method findMethod(Class<?> clazz, String methodName,
            Class<?>[] paramTypes, Object[] paramValues) {

        if (clazz == null || methodName == null) {
            throw new MethodNotFoundException(
                    message(null, "util.method.notfound", clazz, methodName,
                    paramString(paramTypes)));
        }

        if (paramTypes == null) {
            paramTypes = getTypesFromValues(paramValues);
        }

        Method[] methods = clazz.getMethods();

        List<Wrapper> wrappers = Wrapper.wrap(methods, methodName);

        Wrapper result = findWrapper(
                clazz, wrappers, methodName, paramTypes, paramValues);

        if (result == null) {
            return null;
        }
        return getMethod(clazz, (Method) result.unWrap());
    }

    /*
     * This method duplicates code in org.apache.el.util.ReflectionUtil. When
     * making changes keep the code in sync.
     */
    @SuppressWarnings("null")
    private static Wrapper findWrapper(Class<?> clazz, List<Wrapper> wrappers,
            String name, Class<?>[] paramTypes, Object[] paramValues) {

        Map<Wrapper,MatchResult> candidates = new HashMap<Wrapper,MatchResult>();

        int paramCount;
        if (paramTypes == null) {
            paramCount = 0;
        } else {
            paramCount = paramTypes.length;
        }

        for (Wrapper w : wrappers) {
            Class<?>[] mParamTypes = w.getParameterTypes();
            int mParamCount;
            if (mParamTypes == null) {
                mParamCount = 0;
            } else {
                mParamCount = mParamTypes.length;
            }

            // Check the number of parameters
            if (!(paramCount == mParamCount ||
                    (w.isVarArgs() && paramCount >= mParamCount))) {
                // Method has wrong number of parameters
                continue;
            }

            // Check the parameters match
            int exactMatch = 0;
            int assignableMatch = 0;
            int coercibleMatch = 0;
            boolean noMatch = false;
            for (int i = 0; i < mParamCount; i++) {
                // Can't be null
                if (mParamTypes[i].equals(paramTypes[i])) {
                    exactMatch++;
                } else if (i == (mParamCount - 1) && w.isVarArgs()) {
                    Class<?> varType = mParamTypes[i].getComponentType();
                    for (int j = i; j < paramCount; j++) {
                        if (isAssignableFrom(paramTypes[j], varType)) {
                            assignableMatch++;
                        } else {
                            if (paramValues == null) {
                                noMatch = true;
                                break;
                            } else {
                                if (isCoercibleFrom(paramValues[j], varType)) {
                                    coercibleMatch++;
                                } else {
                                    noMatch = true;
                                    break;
                                }
                            }
                        }
                        // Don't treat a varArgs match as an exact match, it can
                        // lead to a varArgs method matching when the result
                        // should be ambiguous
                    }
                } else if (isAssignableFrom(paramTypes[i], mParamTypes[i])) {
                    assignableMatch++;
                } else {
                    if (paramValues == null) {
                        noMatch = true;
                        break;
                    } else {
                        if (isCoercibleFrom(paramValues[i], mParamTypes[i])) {
                            coercibleMatch++;
                        } else {
                            noMatch = true;
                            break;
                        }
                    }
                }
            }
            if (noMatch) {
                continue;
            }

            // If a method is found where every parameter matches exactly,
            // return it
            if (exactMatch == paramCount) {
                return w;
            }

            candidates.put(w, new MatchResult(
                    exactMatch, assignableMatch, coercibleMatch, w.isBridge()));
        }

        // Look for the method that has the highest number of parameters where
        // the type matches exactly
        MatchResult bestMatch = new MatchResult(0, 0, 0, false);
        Wrapper match = null;
        boolean multiple = false;
        for (Map.Entry<Wrapper, MatchResult> entry : candidates.entrySet()) {
            int cmp = entry.getValue().compareTo(bestMatch);
            if (cmp > 0 || match == null) {
                bestMatch = entry.getValue();
                match = entry.getKey();
                multiple = false;
            } else if (cmp == 0) {
                multiple = true;
            }
        }
        if (multiple) {
            if (bestMatch.getExact() == paramCount - 1) {
                // Only one parameter is not an exact match - try using the
                // super class
                match = resolveAmbiguousWrapper(candidates.keySet(), paramTypes);
            } else {
                match = null;
            }

            if (match == null) {
                // If multiple methods have the same matching number of parameters
                // the match is ambiguous so throw an exception
                throw new MethodNotFoundException(message(
                        null, "util.method.ambiguous", clazz, name,
                        paramString(paramTypes)));
                }
        }

        // Handle case where no match at all was found
        if (match == null) {
            throw new MethodNotFoundException(message(
                        null, "util.method.notfound", clazz, name,
                        paramString(paramTypes)));
        }

        return match;
    }


    private static final String paramString(Class<?>[] types) {
        if (types != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < types.length; i++) {
                if (types[i] == null) {
                    sb.append("null, ");
                } else {
                    sb.append(types[i].getName()).append(", ");
                }
            }
            if (sb.length() > 2) {
                sb.setLength(sb.length() - 2);
            }
            return sb.toString();
        }
        return null;
    }


    /*
     * This method duplicates code in org.apache.el.util.ReflectionUtil. When
     * making changes keep the code in sync.
     */
    private static Wrapper resolveAmbiguousWrapper(Set<Wrapper> candidates,
            Class<?>[] paramTypes) {
        // Identify which parameter isn't an exact match
        Wrapper w = candidates.iterator().next();

        int nonMatchIndex = 0;
        Class<?> nonMatchClass = null;

        for (int i = 0; i < paramTypes.length; i++) {
            if (w.getParameterTypes()[i] != paramTypes[i]) {
                nonMatchIndex = i;
                nonMatchClass = paramTypes[i];
                break;
            }
        }

        if (nonMatchClass == null) {
            // Null will always be ambiguous
            return null;
        }

        for (Wrapper c : candidates) {
           if (c.getParameterTypes()[nonMatchIndex] ==
                   paramTypes[nonMatchIndex]) {
               // Methods have different non-matching parameters
               // Result is ambiguous
               return null;
           }
        }

        // Can't be null
        Class<?> superClass = nonMatchClass.getSuperclass();
        while (superClass != null) {
            for (Wrapper c : candidates) {
                if (c.getParameterTypes()[nonMatchIndex].equals(superClass)) {
                    // Found a match
                    return c;
                }
            }
            superClass = superClass.getSuperclass();
        }

        // Treat instances of Number as a special case
        Wrapper match = null;
        if (Number.class.isAssignableFrom(nonMatchClass)) {
            for (Wrapper c : candidates) {
                Class<?> candidateType = c.getParameterTypes()[nonMatchIndex];
                if (Number.class.isAssignableFrom(candidateType) ||
                        candidateType.isPrimitive()) {
                    if (match == null) {
                        match = c;
                    } else {
                        // Match still ambiguous
                        match = null;
                        break;
                    }
                }
            }
        }

        return match;
    }


    /*
     * This method duplicates code in org.apache.el.util.ReflectionUtil. When
     * making changes keep the code in sync.
     */
    static boolean isAssignableFrom(Class<?> src, Class<?> target) {
        // src will always be an object
        // Short-cut. null is always assignable to an object and in EL null
        // can always be coerced to a valid value for a primitive
        if (src == null) {
            return true;
        }

        Class<?> targetClass;
        if (target.isPrimitive()) {
            if (target == Boolean.TYPE) {
                targetClass = Boolean.class;
            } else if (target == Character.TYPE) {
                targetClass = Character.class;
            } else if (target == Byte.TYPE) {
                targetClass = Byte.class;
            } else if (target == Short.TYPE) {
                targetClass = Short.class;
            } else if (target == Integer.TYPE) {
                targetClass = Integer.class;
            } else if (target == Long.TYPE) {
                targetClass = Long.class;
            } else if (target == Float.TYPE) {
                targetClass = Float.class;
            } else {
                targetClass = Double.class;
            }
        } else {
            targetClass = target;
        }
        return targetClass.isAssignableFrom(src);
    }


    /*
     * This method duplicates code in org.apache.el.util.ReflectionUtil. When
     * making changes keep the code in sync.
     */
    private static boolean isCoercibleFrom(Object src, Class<?> target) {
        // TODO: This isn't pretty but it works. Significant refactoring would
        //       be required to avoid the exception.
        try {
            getExpressionFactory().coerceToType(src, target);
        } catch (ELException e) {
            return false;
        }
        return true;
    }


    private static Class<?>[] getTypesFromValues(Object[] values) {
        if (values == null) {
            return null;
        }

        Class<?> result[] = new Class<?>[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                result[i] = null;
            } else {
                result[i] = values[i].getClass();
            }
        }
        return result;
    }


    /*
     * This method duplicates code in org.apache.el.util.ReflectionUtil. When
     * making changes keep the code in sync.
     */
    static Method getMethod(Class<?> type, Method m) {
        if (m == null || Modifier.isPublic(type.getModifiers())) {
            return m;
        }
        Class<?>[] inf = type.getInterfaces();
        Method mp = null;
        for (int i = 0; i < inf.length; i++) {
            try {
                mp = inf[i].getMethod(m.getName(), m.getParameterTypes());
                mp = getMethod(mp.getDeclaringClass(), mp);
                if (mp != null) {
                    return mp;
                }
            } catch (NoSuchMethodException e) {
                // Ignore
            }
        }
        Class<?> sup = type.getSuperclass();
        if (sup != null) {
            try {
                mp = sup.getMethod(m.getName(), m.getParameterTypes());
                mp = getMethod(mp.getDeclaringClass(), mp);
                if (mp != null) {
                    return mp;
                }
            } catch (NoSuchMethodException e) {
                // Ignore
            }
        }
        return null;
    }
    
    
    static Constructor<?> findConstructor(Class<?> clazz, Class<?>[] paramTypes,
            Object[] paramValues) {

        String methodName = "<init>";

        if (clazz == null) {
            throw new MethodNotFoundException(
                    message(null, "util.method.notfound", clazz, methodName,
                    paramString(paramTypes)));
        }

        if (paramTypes == null) {
            paramTypes = getTypesFromValues(paramValues);
        }

        Constructor<?>[] constructors = clazz.getConstructors();

        List<Wrapper> wrappers = Wrapper.wrap(constructors);

        Wrapper result = findWrapper(
                clazz, wrappers, methodName, paramTypes, paramValues);

        if (result == null) {
            return null;
        }
        return getConstructor(clazz, (Constructor<?>) result.unWrap());
    }


    static Constructor<?> getConstructor(Class<?> type, Constructor<?> c) {
        if (c == null || Modifier.isPublic(type.getModifiers())) {
            return c;
        }
        Constructor<?> cp = null;
        Class<?> sup = type.getSuperclass();
        if (sup != null) {
            try {
                cp = sup.getConstructor(c.getParameterTypes());
                cp = getConstructor(cp.getDeclaringClass(), cp);
                if (cp != null) {
                    return cp;
                }
            } catch (NoSuchMethodException e) {
                // Ignore
            }
        }
        return null;
    }


    static Object[] buildParameters(Class<?>[] parameterTypes,
            boolean isVarArgs,Object[] params) {
        ExpressionFactory factory = getExpressionFactory();
        Object[] parameters = null;
        if (parameterTypes.length > 0) {
            parameters = new Object[parameterTypes.length];
            int paramCount = params.length;
            if (isVarArgs) {
                int varArgIndex = parameterTypes.length - 1;
                // First argCount-1 parameters are standard
                for (int i = 0; (i < varArgIndex); i++) {
                    parameters[i] = factory.coerceToType(params[i],
                            parameterTypes[i]);
                }
                // Last parameter is the varargs
                Class<?> varArgClass =
                    parameterTypes[varArgIndex].getComponentType();
                final Object varargs = Array.newInstance(
                    varArgClass,
                    (paramCount - varArgIndex));
                for (int i = (varArgIndex); i < paramCount; i++) {
                    Array.set(varargs, i - varArgIndex,
                            factory.coerceToType(params[i], varArgClass));
                }
                parameters[varArgIndex] = varargs;
            } else {
                parameters = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameters[i] = factory.coerceToType(params[i],
                            parameterTypes[i]);
                }
            }
        }
        return parameters;
    }


    private abstract static class Wrapper {

        public static List<Wrapper> wrap(Method[] methods, String name) {
            List<Wrapper> result = new ArrayList<Wrapper>();
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    result.add(new MethodWrapper(method));
                }
            }
            return result;
        }

        public static List<Wrapper> wrap(Constructor<?>[] constructors) {
            List<Wrapper> result = new ArrayList<Wrapper>();
            for (Constructor<?> constructor : constructors) {
                result.add(new ConstructorWrapper(constructor));
            }
            return result;
        }

        public abstract Object unWrap();
        public abstract Class<?>[] getParameterTypes();
        public abstract boolean isVarArgs();
        public abstract boolean isBridge();
    }


    private static class MethodWrapper extends Wrapper {
        private final Method m;

        public MethodWrapper(Method m) {
            this.m = m;
        }

        @Override
        public Object unWrap() {
            return m;
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return m.getParameterTypes();
        }

        @Override
        public boolean isVarArgs() {
            return m.isVarArgs();
        }

        @Override
        public boolean isBridge() {
            return m.isBridge();
        }
    }

    private static class ConstructorWrapper extends Wrapper {
        private final Constructor<?> c;

        public ConstructorWrapper(Constructor<?> c) {
            this.c = c;
        }

        @Override
        public Object unWrap() {
            return c;
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return c.getParameterTypes();
        }

        @Override
        public boolean isVarArgs() {
            return c.isVarArgs();
        }

        @Override
        public boolean isBridge() {
            return false;
        }
    }

    /*
     * This class duplicates code in org.apache.el.util.ReflectionUtil. When
     * making changes keep the code in sync.
     */
    private static class MatchResult implements Comparable<MatchResult> {

        private final int exact;
        private final int assignable;
        private final int coercible;
        private final boolean bridge;

        public MatchResult(int exact, int assignable, int coercible, boolean bridge) {
            this.exact = exact;
            this.assignable = assignable;
            this.coercible = coercible;
            this.bridge = bridge;
        }

        public int getExact() {
            return exact;
        }

        public int getAssignable() {
            return assignable;
        }

        public int getCoercible() {
            return coercible;
        }

        public boolean isBridge() {
            return bridge;
        }

        @Override
        public int compareTo(MatchResult o) {
            if (this.getExact() < o.getExact()) {
                return -1;
            } else if (this.getExact() > o.getExact()) {
                return 1;
            } else {
                if (this.getAssignable() < o.getAssignable()) {
                    return -1;
                } else if (this.getAssignable() > o.getAssignable()) {
                    return 1;
                } else {
                    if (this.getCoercible() < o.getCoercible()) {
                        return -1;
                    } else if (this.getCoercible() > o.getCoercible()) {
                        return 1;
                    } else {
                        // The nature of bridge methods is such that it actually
                        // doesn't matter which one we pick as long as we pick
                        // one. That said, pick the 'right' one (the non-bridge
                        // one) anyway.
                        if (o.isBridge() && !this.isBridge()) {
                            return 1;
                        } else if (!o.isBridge() && this.isBridge()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        }
    }
}
