package com.baeldung.compressbytes;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressByteArrayUtil {

    public static byte[] compress(byte[] input) {
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int compressedSize = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedSize);
        }

        return outputStream.toByteArray();
    }

    public static byte[] decompress(byte[] input) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int decompressedSize = inflater.inflate(buffer);
            outputStream.write(buffer, 0, decompressedSize);
        }

        return outputStream.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        String inputString = "Baeldung helps developers explore the Java ecosystem and simply be better engineers. " +
                "We publish to-the-point guides and courses, with a strong focus on building web applications, Spring, " +
                "Spring Security, and RESTful APIs";
        byte[] input = inputString.getBytes();

        // Compression
        byte[] compressedData = compress(input);

        // Decompression
        byte[] decompressedData = decompress(compressedData);

        System.out.println("Original: " + input.length + " bytes");
        System.out.println("Compressed: " + compressedData.length + " bytes");
        System.out.println("Decompressed: " + decompressedData.length + " bytes");
    }

}
