package com.baeldung.sizebenchmark;

import org.apache.commons.io.FileUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.NANOSECONDS)
public class FileSizeBenchmark {
    
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void getFileSizeUsingLengthMethod(Blackhole blackhole) throws Exception {
        File file = new File("src/test/resources/size/sample_file_1.in");
        blackhole.consume(file.length());
    }

    @Benchmark
    public void getFileSizeUsingFileInputStream(Blackhole blackhole) throws Exception {
        try (FileInputStream fis = new FileInputStream("src/test/resources/size/sample_file_1.in")) {
            long result = fis.getChannel().size();
            blackhole.consume(result);
        }

    }

    @Benchmark
    public void getFileSizeUsingInputStreamAndUrl(Blackhole blackhole) throws Exception {
        File me = new File("src/test/resources/size/sample_file_1.in");
        URL url = me.toURI().toURL();

        try (InputStream stream = url.openStream()) {
            blackhole.consume(stream.available());
        }
    }

    @Benchmark
    public void getFileSizeUsingApacheCommon(Blackhole blackhole) {
        File imageFile = new File("src/test/resources/size/sample_file_1.in");
        long size = FileUtils.sizeOf(imageFile);
        blackhole.consume(size);
    }

    @Benchmark
    public void getFileSizeUsingFileChannel(Blackhole blackhole) throws IOException {
        Path imageFilePath = Paths.get("src/test/resources/size/sample_file_1.in");
        try (FileChannel imageFileChannel = FileChannel.open(imageFilePath)) {
            long imageFileSize = imageFileChannel.size();
            blackhole.consume(imageFileSize);
        }
    }
}
