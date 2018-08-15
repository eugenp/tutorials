package com.baeldung.rxjava.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ReactiveFileReader {

    private File file;

    public File getFile() {
        return file;
    }

    public ReactiveFileReader(String fileName) {
        super();
        this.file = new File(ReactiveFileReader.class.getClassLoader()
            .getResource(fileName)
            .getFile());
        ;
    }

    public Flowable<String> readFileConvertSyncToObservablesByGenerate() {

        return Flowable.generate(() -> new BufferedReader(new FileReader(file)), (reader, emitter) -> {
            final String line = reader.readLine();
            if (line != null) {
                emitter.onNext(line);
            } else {
                emitter.onComplete();
            }
        }, reader -> reader.close());
    }

    public Flowable<String> readFileConvertSyncToObservablesFromIterable() {

        return Flowable.fromIterable(() -> {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return reader.lines()
                .iterator();
        });
    }

    public Flowable<String> readFileConvertAsyncToObservablesByCreate() {
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

    public Flowable<String> readFileConvertAsyncToObservablesFromCallable() {
        return Flowable.fromCallable(() -> {

            AsynchronousFileChannel channel = AsynchronousFileChannel.open(file.toPath());
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            Future<Integer> operation = channel.read(buffer, 0);

            operation.get();

            String content = new String(buffer.array()).trim();
            buffer.clear();
            return content;

        });
    }

}
