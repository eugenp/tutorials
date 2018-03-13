package test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *通过传递的class
	构造出一个对象
	调用该类的方法
	找到该类的属性
	找到该类的注解
 */
public class ReflectTest {
	
	public void test(Class clazz) throws Exception {
		Object obj = clazz.newInstance();//有一个无参构造函数
		
		Method[] method = clazz.getDeclaredMethods();//找到该类的所有方法，公有的，私有的都包括，但是超类的不显示。
		method = clazz.getMethods();
//		method[0].invoke(obj, args);
		//找到属性
		Field[] fields = clazz.getFields();
		//找到注解
		Annotation[] anno = clazz.getAnnotations();
	}
}
