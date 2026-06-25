package com.baeldung.cn1tutorial.util;

import com.codename1.io.FileSystemStorage;
import com.codename1.ui.CN;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Small UTF-8 and file-system helpers used by repositories and diagnostics code.
 * <p>
 * CN1 does not expose the full Java NIO API, so tiny focused helpers keep the persistence code
 * readable without hiding the platform-specific file APIs.
 */
public final class IOUtil {
    /**
     * Utility class; do not instantiate.
     */
    private IOUtil() {
    }

    /**
     * Reads an input stream as UTF-8 text.
     *
     * @param inputStream source stream
     * @return full decoded content
     * @throws IOException if reading fails
     */
    public static String readUtf8(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[1024];
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        int read;
        while ((read = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, read);
        }
        return builder.toString();
    }

    /**
     * Reads a UTF-8 file through the CN1 storage API.
     *
     * @param path file path relative to the CN1 file system
     * @return file content
     * @throws IOException if reading fails
     */
    public static String readUtf8File(String path) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = CN.openFileInputStream(path);
            return readUtf8(inputStream);
        } finally {
            closeQuietly(inputStream);
        }
    }

    /**
     * Writes UTF-8 text to a file, creating the parent directory if required.
     *
     * @param path destination file path
     * @param content content to write
     * @throws IOException if writing fails
     */
    public static void writeUtf8File(String path, String content) throws IOException {
        ensureParentDirectory(path);
        OutputStream outputStream = null;
        Writer writer = null;
        try {
            outputStream = CN.openFileOutputStream(path);
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(content);
            writer.flush();
        } finally {
            closeQuietly(writer);
            closeQuietly(outputStream);
        }
    }

    /**
     * Ensures the parent directory for a file path exists.
     *
     * @param path destination file path
     */
    public static void ensureParentDirectory(String path) {
        int slash = path.lastIndexOf('/');
        if (slash > 0) {
            String dir = path.substring(0, slash + 1);
            if (!FileSystemStorage.getInstance().exists(dir)) {
                FileSystemStorage.getInstance().mkdir(dir);
            }
        }
    }

    /**
     * Closes the supported resource type, ignoring checked I/O failures.
     *
     * @param closeable supported stream/reader/writer instance, or {@code null}
     */
    public static void closeQuietly(Object closeable) {
        try {
            if (closeable instanceof InputStream) {
                ((InputStream) closeable).close();
            } else if (closeable instanceof OutputStream) {
                ((OutputStream) closeable).close();
            } else if (closeable instanceof Reader) {
                ((Reader) closeable).close();
            } else if (closeable instanceof Writer) {
                ((Writer) closeable).close();
            }
        } catch (IOException ignored) {
        }
    }
}
