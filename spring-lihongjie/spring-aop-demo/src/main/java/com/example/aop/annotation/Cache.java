package com.example.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>缓存注解</p>
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    DataStructure dataStructure() default DataStructure.hash;

    String key();

    String fieldKey() default "";

    int expireTime() default 3600;

    boolean selfControl() default false; // true缓存的使用不受外部控制

    boolean isUpdate() default false; // true 更新缓存中的数据

}
