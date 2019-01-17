package com.baeldung.java.set;

import org.junit.Test;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 测试：集合Set
 * 注意：使用Log4j时，去找log4j.properties文件；使用Logback时，去找logback.xml文件。
 */
public class SetUnitTest {

    /**
     * @see {https://blog.csdn.net/qq_26115733/article/details/70560934}
     * @see {https://www.jianshu.com/p/c02d3a7f8caa}
     * @see {https://www.cnblogs.com/jingmoxukong/p/5910309.html}
     *
     * 使用Log4j打印日志代码
     */
    private static final Logger LOG = LogManager.getLogger(SetUnitTest.class);

    /**
     * 使用logback打印日志代码
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SetUnitTest.class);


    /**
     * TreeSet默认按照升序进行排序
     */
    @Test
    public void givenTreeSet_whenRetrievesObjects_thenNaturalOrder() {
        Set<String> set = new TreeSet<>();
        set.add("Baeldung");
        set.add("is");
        set.add("Awesome");
        System.out.println("set:{}" + set);
        assertEquals(3, set.size());
        assertTrue(set.iterator()
            .next()
            .equals("Awesome"));

        System.out.println("\n===============\n");

        Set<Integer> setNum = new TreeSet<>();
        setNum.add(12);
        setNum.add(1);
        setNum.add(15);
        System.out.println("set:{}" + setNum);
        assertEquals(3, set.size());
        assertTrue(setNum.iterator()
                .next()
                .equals(1));
    }


    /**
     * TreeSet不能放null值
     */
    @Test(expected = NullPointerException.class)
    public void givenTreeSet_whenAddNullObject_thenNullPointer() {
        try{
            Set<String> set = new TreeSet<>();
            set.add("Baeldung");
            set.add("is");
            set.add(null);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }


    /**
     * HashSet允许放null值
     */
    @Test
    public void givenHashSet_whenAddNullObject_thenOK() {
        Set<String> set = new HashSet<>();
        set.add("Baeldung");
        set.add("is");
        set.add(null);
        assertEquals(3, set.size());
    }

    /**
     * 测试：HashSet和TreeSet的插入时间
     */
    @Test
    public void givenHashSetAndTreeSet_whenAddObjects_thenHashSetIsFaster() {

        long hashSetInsertionTime = measureExecution(new Runnable() {
            @Override
            public void run() {
                Set<String> set = new HashSet<>();
                set.add("Baeldung");
                set.add("is");
                set.add("Awesome");
            }
        });

        long treeSetInsertionTime = measureExecution(new Runnable() {
            @Override
            public void run() {
                Set<String> set = new TreeSet<>();
                set.add("Baeldung");
                set.add("is");
                set.add("Awesome");
            }
        });

        LOGGER.debug("HashSet insertion time: {}", hashSetInsertionTime);
        LOGGER.debug("TreeSet insertion time: {}", treeSetInsertionTime);
        System.out.println("LOGGER.isDebugEnabled()" + LOGGER.isDebugEnabled());
    }

    /**
     * HashSet和TreeSet的add都是去重复
     */
    @Test
    public void givenHashSetAndTreeSet_whenAddDuplicates_thenOnlyUnique() {
        Set<String> set = new HashSet<>();
        set.add("Baeldung");
        set.add("Baeldung");
        assertTrue(set.size() == 1);

        Set<String> set2 = new TreeSet<>();
        set2.add("Baeldung");
        set2.add("Baeldung");
        assertTrue(set2.size() == 1);
    }

    /**
     * HashSet的Iterator支持FailFast操作（遍历过程中，不允许修改）
     */
    @Test(expected = ConcurrentModificationException.class)
    public void givenHashSet_whenModifyWhenIterator_thenFailFast() {
        Set<String> set = new HashSet<>();
        set.add("Baeldung");
        Iterator<String> it = set.iterator();

        while (it.hasNext()) {
            set.add("Awesome");
            it.next();
        }
    }

    private static long measureExecution(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        LOGGER.debug("print in measureExecution:{}" , String.valueOf(executionTime));
        return executionTime;
    }
}
