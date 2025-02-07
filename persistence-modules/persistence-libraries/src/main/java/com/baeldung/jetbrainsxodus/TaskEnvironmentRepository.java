package com.baeldung.jetbrainsxodus;

import jetbrains.exodus.ArrayByteIterable;
import jetbrains.exodus.ByteIterable;
import jetbrains.exodus.bindings.StringBinding;
import jetbrains.exodus.env.*;

import java.util.HashMap;
import java.util.Map;

public class TaskEnvironmentRepository {
    private static final String DB_PATH = "db\\.myAppData";
    private static final String TASK_STORE = "TaskStore";

    public void save(String taskId, String taskDescription) {
        try (Environment environment = openEnvironmentExclusively()) {

            Transaction writeTransaction = environment.beginExclusiveTransaction();

            try {
                Store taskStore = environment.openStore(TASK_STORE,
                  StoreConfig.WITHOUT_DUPLICATES, writeTransaction);

                ArrayByteIterable id = StringBinding.stringToEntry(taskId);
                ArrayByteIterable value = StringBinding.stringToEntry(taskDescription);

                taskStore.put(writeTransaction, id, value);
            } catch (Exception e) {
                writeTransaction.abort();
            } finally {
                if (!writeTransaction.isFinished()) {
                    writeTransaction.commit();
                }
            }
        }
    }

    private Environment openEnvironmentExclusively() {
        return Environments.newInstance(DB_PATH);
    }

    public String findOne(String taskId) {
        try (Environment environment = openEnvironmentExclusively()) {

            Transaction readonlyTransaction = environment.beginReadonlyTransaction();

            try {
                Store taskStore = environment.openStore(TASK_STORE,
                  StoreConfig.WITHOUT_DUPLICATES, readonlyTransaction);

                ArrayByteIterable id = StringBinding.stringToEntry(taskId);

                ByteIterable result = taskStore.get(readonlyTransaction, id);

                return result == null ? null : StringBinding.entryToString(result);

            } finally {
                readonlyTransaction.abort();
            }
        }
    }

    public Map<String, String> findAll() {
        try (Environment environment = openEnvironmentExclusively()) {

            Transaction readonlyTransaction = environment.beginReadonlyTransaction();

            try {
                Store taskStore = environment.openStore(TASK_STORE,
                  StoreConfig.WITHOUT_DUPLICATES, readonlyTransaction);

                Map<String, String> result = new HashMap<>();
                try (Cursor cursor = taskStore.openCursor(readonlyTransaction)) {

                    while (cursor.getNext()) {
                        result.put(StringBinding.entryToString(cursor.getKey()),
                          StringBinding.entryToString(cursor.getValue()));
                    }
                }

                return result;

            } finally {
                readonlyTransaction.abort();
            }
        }
    }

    public void deleteAll() {
        try (Environment environment = openEnvironmentExclusively()) {

            Transaction exclusiveTransaction = environment.beginExclusiveTransaction();

            try {
                Store taskStore = environment.openStore(TASK_STORE,
                  StoreConfig.WITHOUT_DUPLICATES, exclusiveTransaction);

                try (Cursor cursor = taskStore.openCursor(exclusiveTransaction)) {

                    while (cursor.getNext()) {
                        taskStore.delete(exclusiveTransaction, cursor.getKey());
                    }
                }

            } finally {
                exclusiveTransaction.commit();
            }
        }
    }
}
