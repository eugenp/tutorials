package com.insightsource.concurrent.collect;

import java.util.WeakHashMap;

public class WeakMapTest {

    public static void main(String[] args) {
        WeakHashMap<Data, String> map = new WeakHashMap<Data, String>();
        Data data = new Data("foo");
        map.put(data, data.getValue());
        System.out.println("map contains data ? " + map.containsKey(data));

        data = null;    //weak reference, GC will collect it automatically.
        for (int i = 0; i < 100000; i++) {
            if (map.size() != 0)
                System.out.println("At iteration " + i + " the map still holds the reference on data");
            else {
                System.out.println("data has finally been garbage collected at iteration " + i + ", hence the map is now empty");
                break;
            }
        }
    }

    static class Data {
        private String value;

        public Data(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
