package com.baeldung.rxjava2.converter;

import com.baeldung.rxjava2.Member;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.file.FileSystem;

public class RxToRxApi {

    Single<String> getMembersAsString(String filepath) {
        Vertx vertx = Vertx.vertx();
        FileSystem fileSystem = vertx.fileSystem();
        return fileSystem
          .rxReadFile(filepath)
          .map(buffer -> buffer.toString());
    }

    Single<JsonArray> getMembersAsArray(String filepath) {
        Vertx vertx = Vertx.vertx();
        FileSystem fileSystem = vertx.fileSystem();
        return fileSystem
          .rxReadFile(filepath)
          .map(buffer -> buffer.toString())
          .map(content -> new JsonArray(content));
    }

    Flowable<Member> getMemberRxArrayAsFlowable(String filepath) {
        return getMembersAsArray(filepath)
          .flatMapPublisher(array -> Flowable.fromIterable(array))
          .cast(JsonObject.class)
          .map(json -> json.mapTo(Member.class));
    }

    Flowable<Member> getMembersListAsFlowable(String filepath) {
        Vertx vertx = Vertx.vertx();
        FileSystem fileSystem = vertx.fileSystem();
        return fileSystem
          .rxReadFile(filepath)
          .map(buffer -> buffer.toString())
          .map(content -> new JsonArray(content))
          .flatMapPublisher(array -> Flowable.fromIterable(array))
          .cast(JsonObject.class)
          .map(json -> json.mapTo(Member.class));
    }
}
