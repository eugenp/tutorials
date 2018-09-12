package com.baeldung.rxjava.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ReactiveFileReader {

    private File file;
    private ByteBuffer buffer;

    public File getFile() {
        return file;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public ReactiveFileReader(String fileName) {
        super();
        this.file = new File(ReactiveFileReader.class.getClassLoader()
            .getResource(fileName)
            .getFile());
        this.buffer = ByteBuffer.allocate((int) file.length());
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
            List<String> lines = new ArrayList<>();
            try {
                reader = new BufferedReader(new FileReader(file));
                lines = reader.lines()
                    .map(line -> line)
                    .collect(Collectors.toList());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return lines.iterator();
        });
    }

    public Flowable<String> readFileConvertAsyncToObservablesByCreate() {
        return Single.<String> create(emitter -> {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(file.toPath());
            
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
                    byte[] data = new byte[attachment.limit()];
                    attachment.get(data);
                    emitter.onSuccess(new String(data));
                    attachment.clear();
                }

                @Override
                public void failed(Throwable error, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    emitter.onError(error);
                }
            });
        })
            .toFlowable();
    }

    public Flowable<Integer> readFileConvertAsyncToObservablesFromFuture() throws IOException {

        AsynchronousFileChannel channel = AsynchronousFileChannel.open(file.toPath());
        Future<Integer> operation = channel.read(buffer, 0);
        return Flowable.fromFuture(operation);

    }

    public void cleanBuffer() {
        buffer.clear();
    }

}
