package com.baeldung.guava.tutorial;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

import java.util.HashMap;

public class ClassToInstanceMapExample {
    public static void main(String[] args) {
        ClassToInstanceMap<Action> map = MutableClassToInstanceMap.create(new HashMap());

        map.put(Save.class, new Save());
        Open newOpen = (Open) map.put(Open.class, new Open());
        Delete newDelete = map.putInstance(Delete.class, new Delete());


        ImmutableClassToInstanceMap.<Action>builder()
                .put(Save.class, new Save())
                .put(Open.class, new Open())
                .put(Delete.class, new Delete())
                .build();

        Action saveAction = map.get(Save.class);
        Open openAction = (Open) map.get(Open.class);
        Delete deleteAction = map.getInstance(Delete.class);
    }
}

abstract class Action {
}

class Save extends Action {
}

class Open extends Action {
}

class Delete extends Action {
}