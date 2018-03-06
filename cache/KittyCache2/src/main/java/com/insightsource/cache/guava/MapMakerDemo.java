package com.insightsource.cache.guava;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.MapMaker;

public class MapMakerDemo {
    public static void main(String args[]) {
        ConcurrentMap<String, String> testMap = new MapMaker().concurrencyLevel(32).weakValues().makeMap();

        String a = "testaaa";
        testMap.put("a", a);
        testMap.put("b", "testb");

        System.out.println(testMap.get("a"));
        System.out.println(testMap.get("b"));
        System.out.println(testMap.get("c"));

        a = null;
        System.gc();

        /**
         * ����sleep4���ӹ���
         * ���涼ʧЧ����get�ͻ���ݰ󶨵�functionȥ���value����map���ˡ�
         */
        try {
            TimeUnit.SECONDS.sleep(2L);        //no work????
            //Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * �����������������ǲ��Ǿ����µ�ֵ�ˣ�~
         */

        System.out.println(testMap.get("a"));
        System.out.println(testMap.get("b"));
        System.out.println(testMap.get("c"));
    }
}
