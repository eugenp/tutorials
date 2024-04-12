package com.baeldung.list.multipleobjecttypes;

import java.util.List;

@FunctionalInterface
public interface UserFunctionalInterface {

    List<Object> addToList(List<Object> list, Object data);

    default void printList(List<Object> dataList) {
        for (Object data : dataList) {
            if (data instanceof String stringData)
                System.out.println("String Data: " + stringData);
            if (data instanceof Integer intData)
                System.out.println("Integer Data: " + intData);
        }
    }
}
