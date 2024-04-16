package com.baeldung.jooq.jointables;

import static org.jooq.impl.DSL.field;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;

import com.baeldung.jooq.jointables.public_.Tables;

public class JoinTables {

    public static Result<Record> usingJoinMethod(DSLContext context) {
        SelectJoinStep<Record> query = context.select()
            .from(Tables.BOOK)
            .join(Tables.BOOKAUTHOR)
            .on(field(Tables.BOOK.AUTHOR_ID).eq(field(Tables.BOOKAUTHOR.ID)));
        return query.fetch();
    }

    public static Result<Record> usingMultipleJoinMethod(DSLContext context) {
        SelectJoinStep<Record> query = context.select()
            .from(Tables.BOOK)
            .join(Tables.BOOKAUTHOR)
            .on(field(Tables.BOOK.AUTHOR_ID).eq(field(Tables.BOOKAUTHOR.ID)))
            .join(Tables.STORE)
            .on(field(Tables.BOOK.STORE_ID).eq(field(Tables.STORE.ID)));
        return query.fetch();
    }

    public static Result<Record> usingLeftOuterJoinMethod(DSLContext context) {
        SelectJoinStep<Record> query = context.select()
            .from(Tables.BOOK)
            .leftOuterJoin(Tables.BOOKAUTHOR)
            .on(field(Tables.BOOK.AUTHOR_ID).eq(field(Tables.BOOKAUTHOR.ID)));
        return query.fetch();
    }

    public static Result<Record> usingRightOuterJoinMethod(DSLContext context) {
        SelectJoinStep<Record> query = context.select()
            .from(Tables.BOOK)
            .rightOuterJoin(Tables.BOOKAUTHOR)
            .on(field(Tables.BOOK.AUTHOR_ID).eq(field(Tables.BOOKAUTHOR.ID)));
        return query.fetch();
    }

    public static Result<Record> usingFullOuterJoinMethod(DSLContext context) {
        SelectJoinStep<Record> query = context.select()
            .from(Tables.BOOK)
            .fullOuterJoin(Tables.BOOKAUTHOR)
            .on(field(Tables.BOOK.AUTHOR_ID).eq(field(Tables.BOOKAUTHOR.ID)));
        return query.fetch();
    }

    public static Result<Record> usingNaturalJoinMethod(DSLContext context) {
        SelectJoinStep<Record> query = context.select()
            .from(Tables.BOOK)
            .naturalJoin(Tables.BOOKAUTHOR);
        return query.fetch();
    }

    public static Result<Record> usingCrossJoinMethod(DSLContext context) {
        SelectJoinStep<Record> query = context.select()
            .from(Tables.STORE)
            .crossJoin(Tables.BOOK);
        return query.fetch();
    }

    public static void printResult(Result<Record> result) {
        for (Record record : result) {
            System.out.println(record);
        }
    }
}
