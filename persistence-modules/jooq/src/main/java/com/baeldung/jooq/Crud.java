package com.baeldung.jooq;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.UpdatableRecord;

import java.util.Map;

public class Crud {

    public static <R extends UpdatableRecord<R>> void save(UpdatableRecord<R> record) {
        record.store();
    }

    public static Result<Record> getAll(DSLContext context, Table<? extends Record> table) {
        return context.select()
          .from(table)
          .fetch();
    }

    public static Result<Record> getFields(DSLContext context, Table<? extends Record> table, SelectFieldOrAsterisk... fields) {
        return context.select(fields)
          .from(table)
          .fetch();
    }

    public static <R extends Record> R getOne(DSLContext context, Table<R> table, Condition condition) {
        return context.fetchOne(table, condition);
    }

    public static <T> void update(DSLContext context, Table<? extends Record> table, Map<Field<T>, T> values, Condition condition) {
        context.update(table)
          .set(values)
          .where(condition)
          .execute();
    }

    public static <R extends UpdatableRecord<R>> void update(UpdatableRecord<R> record) {
        record.update();
    }

    public static void delete(DSLContext context, Table<? extends Record> table, Condition condition) {
        context.delete(table)
          .where(condition)
          .execute();
    }

    public static <R extends UpdatableRecord<R>> void delete(UpdatableRecord<R> record) {
        record.delete();
    }

}
