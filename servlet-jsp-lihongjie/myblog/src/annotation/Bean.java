package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识JavaBean与数据库表名的映射关系
 * 数据表的名字
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)	
public @interface Bean {
	String value();
}
