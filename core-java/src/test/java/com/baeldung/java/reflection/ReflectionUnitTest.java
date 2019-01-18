package com.baeldung.java.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 测试：反射
 */
public class ReflectionUnitTest {

    @Test
    public void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
        final Object person = new Person();
        final Field[] fields = person.getClass().getDeclaredFields();

        final List<String> actualFieldNames = getFieldNames(fields);
        System.out.println("actualFieldNames:{}" + actualFieldNames);

        assertTrue(Arrays.asList("name", "age").containsAll(actualFieldNames));
    }

    @Test
    public void givenObject_whenGetsClassName_thenCorrect() {
        final Object goat = new Goat("goat");
        final Class<?> clazz = goat.getClass();
        System.out.println("clazz:{}" + clazz);

        assertEquals("Goat", clazz.getSimpleName());
        assertEquals("com.baeldung.java.reflection.Goat", clazz.getName());
        assertEquals("com.baeldung.java.reflection.Goat", clazz.getCanonicalName());
    }

    /**
     * @see java.lang.Class#forName(String className)
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClassName_whenCreatesObject_thenCorrect() throws ClassNotFoundException {
        final Class<?> clazz = Class.forName("com.baeldung.java.reflection.Goat");
        System.out.println("clazz:{}" + clazz);

        assertEquals("Goat", clazz.getSimpleName());
        assertEquals("com.baeldung.java.reflection.Goat", clazz.getName());
        assertEquals("com.baeldung.java.reflection.Goat", clazz.getCanonicalName());
    }

    /**
     * 判断类的修饰符
     * @see java.lang.reflect.Modifier#isPublic(int)
     * @see java.lang.reflect.Modifier#isAbstract(int)
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenRecognisesModifiers_thenCorrect() throws ClassNotFoundException {
        final Class<?> goatClass = Class.forName("com.baeldung.java.reflection.Goat");
        final Class<?> animalClass = Class.forName("com.baeldung.java.reflection.Animal");
        final int goatMods = goatClass.getModifiers();
        final int animalMods = animalClass.getModifiers();
        System.out.println("goatMods:{}" + goatMods);
        System.out.println("animalMods:{}" + animalMods);

        assertTrue(Modifier.isPublic(goatMods));
        assertTrue(Modifier.isAbstract(animalMods));
        assertTrue(Modifier.isPublic(animalMods));
    }

    /**
     * 获取类所在的包的名字
     */
    @Test
    public void givenClass_whenGetsPackageInfo_thenCorrect() {
        final Goat goat = new Goat("goat");
        final Class<?> goatClass = goat.getClass();
        final Package pkg = goatClass.getPackage();
        System.out.println("Package name:{}" + pkg.getName());
        assertEquals("com.baeldung.java.reflection", pkg.getName());
    }

    /**
     * 获取父类名称
     */
    @Test
    public void givenClass_whenGetsSuperClass_thenCorrect() {
        final Goat goat = new Goat("goat");
        final String str = "any string";

        final Class<?> goatClass = goat.getClass();
        final Class<?> goatSuperClass = goatClass.getSuperclass();
        System.out.println("goatClass:{}" + goatClass);
        System.out.println("goatSuperClass:{}" + goatSuperClass);

        assertEquals("Animal", goatSuperClass.getSimpleName());
        assertEquals("Object", str.getClass().getSuperclass().getSimpleName());

    }

    /**
     * 获取类的接口
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsImplementedInterfaces_thenCorrect() throws ClassNotFoundException {
        final Class<?> goatClass = Class.forName("com.baeldung.java.reflection.Goat");
        final Class<?> animalClass = Class.forName("com.baeldung.java.reflection.Animal");
        final Class<?>[] goatInterfaces = goatClass.getInterfaces();
        final Class<?>[] animalInterfaces = animalClass.getInterfaces();
        System.out.println("goatInterfaces:{}" + Arrays.toString(goatInterfaces));
        System.out.println("animalInterfaces:{}" + Arrays.toString(animalInterfaces));

        assertEquals(1, goatInterfaces.length);
        assertEquals(1, animalInterfaces.length);
        assertEquals("Locomotion", goatInterfaces[0].getSimpleName());
        assertEquals("Eating", animalInterfaces[0].getSimpleName());
    }

    /**
     * 获取构造方法
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsConstructor_thenCorrect() throws ClassNotFoundException {
        final Class<?> goatClass = Class.forName("com.baeldung.java.reflection.Goat");
        final Constructor<?>[] constructors = goatClass.getConstructors();
        System.out.println("constructors:{}" + Arrays.toString(constructors));

        assertEquals(1, constructors.length);
        assertEquals("com.baeldung.java.reflection.Goat", constructors[0].getName());
    }

    /**
     * 获取类属性
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsFields_thenCorrect() throws ClassNotFoundException {
        final Class<?> animalClass = Class.forName("com.baeldung.java.reflection.Animal");
        final Field[] fields = animalClass.getDeclaredFields();
        System.out.println("fields:{}" + Arrays.toString(fields));

        final List<String> actualFields = getFieldNames(fields);
        System.out.println("actualFields:{}" + actualFields);

        assertEquals(2, actualFields.size());
        assertTrue(actualFields.containsAll(Arrays.asList("name", "CATEGORY")));
    }

    /**
     * 获取类的方法(除了构造方法外)
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsMethods_thenCorrect() throws ClassNotFoundException {
        final Class<?> animalClass = Class.forName("com.baeldung.java.reflection.Animal");
        final Method[] methods = animalClass.getDeclaredMethods();
        final List<String> actualMethods = getMethodNames(methods);
        System.out.println("actualMethods:{}" + actualMethods);

        assertEquals(3, actualMethods.size());
        assertTrue(actualMethods.containsAll(Arrays.asList("getName", "setName", "getSound")));
    }


    /**
     * 获取所有构造方法
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsAllConstructors_thenCorrect() throws ClassNotFoundException {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Constructor<?>[] constructors = birdClass.getConstructors();
        System.out.println("constructors:{}" + Arrays.toString(constructors));

        assertEquals(3, constructors.length);
    }

    /**
     * 根据入参数获取不同类型的构造函数
     * @throws Exception
     */
    @Test
    public void givenClass_whenGetsEachConstructorByParamTypes_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        System.out.println(birdClass.getConstructor());
        System.out.println(birdClass.getConstructor(String.class));
        System.out.println(birdClass.getConstructor(String.class, boolean.class));
    }

    /**
     * 根据反射获取的不同构造函数，创建对象实例
     * @throws Exception
     */
    @Test
    public void givenClass_whenInstantiatesObjectsAtRuntime_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");

        final Constructor<?> cons1 = birdClass.getConstructor();
        final Constructor<?> cons2 = birdClass.getConstructor(String.class);
        final Constructor<?> cons3 = birdClass.getConstructor(String.class, boolean.class);

        final Bird bird1 = (Bird) cons1.newInstance();
        final Bird bird2 = (Bird) cons2.newInstance("Weaver bird");
        final Bird bird3 = (Bird) cons3.newInstance("dove", true);

        System.out.println("Bird_1:{}" + bird1.getName());
        System.out.println("Bird_2:{}" + bird2.getName());
        System.out.println("Bird_3:{}" + bird3.getName());

        System.out.println("Bird_1_walks:{}" + bird1.walks());
        System.out.println("Bird_2_walks:{}" + bird2.walks());
        System.out.println("Bird_3_walks:{}" + bird3.walks());

        assertEquals("bird", bird1.getName());
        assertEquals("Weaver bird", bird2.getName());
        assertEquals("dove", bird3.getName());
        assertFalse(bird1.walks());
        assertTrue(bird3.walks());
    }


    /**
     * 获取类的public属性字段
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsPublicFields_thenCorrect() throws ClassNotFoundException {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Field[] fields = birdClass.getFields();
        System.out.println("fields:{}" + Arrays.toString(fields));
        assertEquals(1, fields.length);
        assertEquals("CATEGORY", fields[0].getName());
    }

    /**
     * 根据类的属性字段名称，获取public属性字段
     * @throws Exception
     */
    @Test
    public void givenClass_whenGetsPublicFieldByName_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Field field = birdClass.getField("CATEGORY");
        System.out.println("field:{}" + field.getName());
        assertEquals("CATEGORY", field.getName());
    }


    /**
     * 获取声明的所有类属性字段
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsDeclaredFields_thenCorrect() throws ClassNotFoundException {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Field[] fields = birdClass.getDeclaredFields();
        System.out.println("fields:{}" + Arrays.toString(fields));

        assertEquals(1, fields.length);
        assertEquals("walks", fields[0].getName());
    }

    /**
     * 根据属性属性名称，获取声明的类属性字段
     * @throws Exception
     */
    @Test
    public void givenClass_whenGetsFieldsByName_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Field field = birdClass.getDeclaredField("walks");
        System.out.println("field:{}" + field.getName());
        assertEquals("walks", field.getName());
    }


    /**
     * 获取类属性字段的修饰符类型
     * @throws Exception
     */
    @Test
    public void givenClassField_whenGetsType_thenCorrect() throws Exception {
        final Field field = Class.forName("com.baeldung.java.reflection.Bird")
                .getDeclaredField("walks");
        final Class<?> fieldClass = field.getType();
        System.out.println("fieldClass:{}" + fieldClass.getCanonicalName());

        assertEquals("boolean", fieldClass.getSimpleName());
    }


    /**
     * 反射中的setAccessible的使用
     * @throws Exception
     */
    @Test
    public void givenClassField_whenSetsAndGetsValue_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Bird bird = (Bird) birdClass.newInstance();
        final Field field = birdClass.getDeclaredField("walks");
        field.setAccessible(true);

        System.out.println(field.getBoolean(bird));
        System.out.println(bird.walks());
        assertFalse(field.getBoolean(bird));
        assertFalse(bird.walks());

        field.set(bird, true);

        System.out.println(field.getBoolean(bird));
        System.out.println(bird.walks());
        assertTrue(field.getBoolean(bird));
        assertTrue(bird.walks());
    }

    /**
     * 通过反射获取字段的属性值（特别注意：field.get(null)的使用）
     * @throws Exception
     */
    @Test
    public void givenClassField_whenGetsAndSetsWithNull_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Field field = birdClass.getField("CATEGORY");
        field.setAccessible(true);

        System.out.println(field.get(null));
        assertEquals("domestic", field.get(null));
    }


    /**
     * 获取所有的公共方法
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsAllPublicMethods_thenCorrect() throws ClassNotFoundException {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Method[] methods = birdClass.getMethods();
        final List<String> methodNames = getMethodNames(methods);

        System.out.println("methodNames:{}" + methodNames);
        assertTrue(methodNames.containsAll(Arrays.asList("equals", "notifyAll", "hashCode", "walks", "eats", "toString")));
    }

    /**
     * 获取内中声明的方法（除了构造方法）
     * @throws ClassNotFoundException
     */
    @Test
    public void givenClass_whenGetsOnlyDeclaredMethods_thenCorrect() throws ClassNotFoundException {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final List<String> actualMethodNames = getMethodNames(birdClass.getDeclaredMethods());
        System.out.println("actualMethodNames:{}" + actualMethodNames);

        final List<String> expectedMethodNames = Arrays.asList("setWalks", "walks", "getSound", "eats");

        assertEquals(expectedMethodNames.size(), actualMethodNames.size());
        assertTrue(expectedMethodNames.containsAll(actualMethodNames));
        assertTrue(actualMethodNames.containsAll(expectedMethodNames));

    }

    /**
     * 反射setAccessible的使用。
     * （1）在类的外面获取此类的私有成员变量的value时，需要注意。
     * @throws Exception
     */
    @Test
    public void givenMethodName_whenGetsMethod_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Method walksMethod = birdClass.getDeclaredMethod("walks");
        final Method setWalksMethod = birdClass.getDeclaredMethod("setWalks", boolean.class);
        System.out.println("walksMethod:{}" + walksMethod + " ; " + walksMethod.getName());
        System.out.println("setWalksMethod:{}" + setWalksMethod +" ; " + setWalksMethod.getName());

        assertFalse(walksMethod.isAccessible());
        assertFalse(setWalksMethod.isAccessible());

        walksMethod.setAccessible(true);
        setWalksMethod.setAccessible(true);

        assertTrue(walksMethod.isAccessible());
        assertTrue(setWalksMethod.isAccessible());

        System.out.println("\n======================\n");

        Object instanceBird = birdClass.newInstance();
        Object setWalksMethodReturn = setWalksMethod.invoke(instanceBird, true);
        System.out.println("setWalksMethodReturn:{}" + setWalksMethodReturn);

        Object walksMethodReturn = walksMethod.invoke(instanceBird , null);
        System.out.println("walksMethodReturn:{}" + walksMethodReturn);


        System.out.println("\n======================\n");
        Field[] fields = birdClass.getDeclaredFields();
        System.out.println("fields:{}" + Arrays.toString(fields));
        for(Field field : fields){
            field.setAccessible(true);
            System.out.println(field.get(instanceBird));
        }
    }

    /**
     * 反射中invoke的使用
     * @throws Exception
     */
    @Test
    public void givenMethod_whenInvokes_thenCorrect() throws Exception {
        final Class<?> birdClass = Class.forName("com.baeldung.java.reflection.Bird");
        final Bird bird = (Bird) birdClass.newInstance();
        final Method setWalksMethod = birdClass.getDeclaredMethod("setWalks", boolean.class);
        final Method walksMethod = birdClass.getDeclaredMethod("walks");
        final boolean walks = (boolean) walksMethod.invoke(bird);

        assertFalse(walks);
        assertFalse(bird.walks());

        setWalksMethod.invoke(bird, true);
        final boolean walks2 = (boolean) walksMethod.invoke(bird);
        System.out.println("walk2:{}" + walks2);
        System.out.println("bird.walks():{}" + bird.walks());

        assertTrue(walks2);
        assertTrue(bird.walks());

    }

    /**
     * 获取Field的名称
     * @param fields
     * @return
     */
    private static List<String> getFieldNames(Field[] fields) {
        final List<String> fieldNames = new ArrayList<>();
        for (final Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;

    }

    /**
     * 获取Method的名称
     * @param methods
     * @return
     */
    private static List<String> getMethodNames(Method[] methods) {
        final List<String> methodNames = new ArrayList<>();
        for (final Method method : methods) {
            methodNames.add(method.getName());
        }
        return methodNames;
    }

}
