package com.baeldung.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

/**
 * @author zn.wang
 */
public class ArrayInverter {

    /**
     * 数组反转
     * @param array
     */
    public void invertUsingFor(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    /**
     * 使用{@link java.util.Collections#reverse(List)}
     * @param array
     */
    public void invertUsingCollectionsReverse(Object[] array) {
        List<Object> list = Arrays.asList(array);
        Collections.reverse(list);
    }

    /**
     * 借助一个List，然后倒序进行赋值操作。
     * @param array
     * @return
     */
    public Object[] invertUsingStreams(final Object[] array) {
        List<Object> list = new ArrayList<>();
        int bound = array.length;
        for (int i = 1; i <= bound; i++) {
            Object o = array[array.length - i];
            list.add(o);
        }
        return list.toArray();
    }


    /**
     * @see org.apache.commons.lang3.ArrayUtils#reverse(Object[] array)
     * @param array
     */
    public void invertUsingCommonsLang(Object[] array) {
        ArrayUtils.reverse(array);
    }

    /**
     * 借助于gauva的{@link com.google.common.collect.Lists#reverse(List list)}
     * @param array
     * @return
     */
    public Object[] invertUsingGuava(Object[] array) {
        List<Object> list = Arrays.asList(array);
        List<Object> reverted = Lists.reverse(list);
        return reverted.toArray();
    }

}
