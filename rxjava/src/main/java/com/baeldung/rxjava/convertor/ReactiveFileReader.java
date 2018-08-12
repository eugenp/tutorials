package com.baeldung.rxjava.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ReactiveFileReader {

    private String filePath;

    public ReactiveFileReader(String fileName) {
        super();
        this.filePath = ReactiveFileReader.class.getClassLoader()
            .getResource(fileName)
            .getFile();
        ;
    }

    public Flowable<String> readFileInSync() {

        return Flowable.generate(() -> new BufferedReader(new FileReader(filePath)), (reader, emitter) -> {
            final String line = reader.readLine();
            if (line != null) {
                emitter.onNext(line);
            } else {
                emitter.onComplete();
            }
        }, reader -> reader.close());
    }

    public Flowable<String> readFileInAsync() {
        File file = new File(filePath);
        return Single.<String> create(emitter -> {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(file.toPath());
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {

                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        emitter.onError(e);
                        return;
                    }
                    attachment.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    emitter.onSuccess(new String(data));
                }

                @Override
                public void failed(Throwable error, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        // e.printStackTrace();
                    }
                    emitter.onError(error);
                }
            });
        })
            .toFlowable();
    }

}
