package com.baeldung.rxjava2.converter;

import com.baeldung.rxjava2.Member;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

public class AsyncToRxApi {

    public Flowable<Member> getMemberListAsyncAsFlowable(){
        File file = new File("src/main/resources/members.json");

        return Single.<ByteBuffer> create(emitter -> {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(file.toPath());
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

            channel.read(buffer, 0, null, new CompletionHandler<Integer, Void>() {

                @Override
                public void completed(Integer result, Void attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        emitter.onError(e);
                        return;
                    }
                    emitter.onSuccess(buffer);
                }

                @Override
                public void failed(Throwable error, Void attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        // ignore
                    }
                    emitter.onError(error);
                }
            });
        })
          .map(buffer -> new String(buffer.array(), StandardCharsets.UTF_8))
          .map(JsonArray::new)
          .flatMapPublisher(Flowable::fromIterable)
          .cast(JsonObject.class)
          .map(json -> json.mapTo(Member.class));
    }

}
