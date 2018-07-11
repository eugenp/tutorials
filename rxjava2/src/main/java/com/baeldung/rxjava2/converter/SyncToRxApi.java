package com.baeldung.rxjava2.converter;

import com.baeldung.rxjava2.Member;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class SyncToRxApi {

    Flowable<Member> getMemberListAsFlowable(String filename) {
        return Single.<String>create(emitter -> {
            try {
                ClassLoader cl = getClass().getClassLoader();
                File file = new File(cl
                  .getResource(filename)
                  .getFile());
                String members = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                emitter.onSuccess(members);
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
          .map(list -> new JsonArray(list))
          .flatMapPublisher(array -> Flowable.fromIterable(array))
          .cast(JsonObject.class)
          .map(json -> json.mapTo(Member.class));
    }


    Flowable<Member> getMemberListAntiPattern(String filename) {
        String members = "";
        try {
            ClassLoader cl = getClass().getClassLoader();
            File file = new File(cl
              .getResource(filename)
              .getFile());
            members = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error on read: \n" + e.getMessage());
        }
        return Single.<String>just(members)
          .map(list -> new JsonArray(list))
          .flatMapPublisher(array -> Flowable.fromIterable(array))
          .cast(JsonObject.class)
          .map(json -> json.mapTo(Member.class));
    }
}
