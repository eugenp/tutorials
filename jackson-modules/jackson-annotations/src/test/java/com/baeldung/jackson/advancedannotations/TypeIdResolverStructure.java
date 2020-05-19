package com.baeldung.jackson.advancedannotations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

public class TypeIdResolverStructure {
    public static class BeanContainer {
        private List<AbstractBean> beans;

        public List<AbstractBean> getBeans() {
            return beans;
        }

        public void setBeans(List<AbstractBean> beans) {
            this.beans = beans;
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
    @JsonTypeIdResolver(BeanIdResolver.class)
    public static class AbstractBean {
        private int id;

        protected AbstractBean() {
        }

        protected AbstractBean(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class FirstBean extends AbstractBean {
        String firstName;

        public FirstBean() {
        }

        public FirstBean(int id, String name) {
            super(id);
            setFirstName(name);
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String name) {
            firstName = name;
        }
    }

    public static class LastBean extends AbstractBean {
        String lastName;

        public LastBean() {
        }

        public LastBean(int id, String name) {
            super(id);
            setLastName(name);
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String name) {
            lastName = name;
        }
    }

    public static class BeanIdResolver extends TypeIdResolverBase {
        private JavaType superType;

        @Override
        public void init(JavaType baseType) {
            superType = baseType;
        }

        @Override
        public Id getMechanism() {
            return Id.NAME;
        }

        @Override
        public String idFromValue(Object obj) {
            return idFromValueAndType(obj, obj.getClass());
        }

        @Override
        public String idFromValueAndType(Object obj, Class<?> subType) {
            String typeId = null;
            switch (subType.getSimpleName()) {
            case "FirstBean":
                typeId = "bean1";
                break;
            case "LastBean":
                typeId = "bean2";
            }
            return typeId;
        }

        @Override
        public JavaType typeFromId(DatabindContext context, String id) {
            Class<?> subType = null;
            switch (id) {
            case "bean1":
                subType = FirstBean.class;
                break;
            case "bean2":
                subType = LastBean.class;
            }
            return context.constructSpecializedType(superType, subType);
        }
    }
}