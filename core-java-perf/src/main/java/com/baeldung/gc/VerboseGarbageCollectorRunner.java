package com.baeldung.gc;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple Java program to demonstrate how to enable verbose Garbage Collection (GC) logging.
 * <p>
 * This simple program loads 3 million {@link java.lang.String} instances into a {@link java.util.HashMap} 
 * object before making an explicit call to the garbage collector using <code>System.gc()</code>.
 * <p>
 * Finally, it removes 2 million of the {@link java.lang.String} instances from the {@link java.util.HashMap}. 
 * We also explicitly use <code>System.out.println</code> to make interpreting the output easier.
 * <p>
 * Run this program with the following arguments to see verbose GC logging in its complete form:
 * <pre>
 * -XX:+UseSerialGC -Xms1024m -Xmx1024m -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:/path/to/file/gc.log
 * </pre>
 * Where:
 * <ul>
 *   <li><code>-XX:+UseSerialGC</code> - specify the serial garbage collector.</li>
 *   <li><code>-Xms1024m</code> - specify the minimal heap size.</li>
 *   <li><code>-Xmx1024m</code> - specify the maximal heap size.</li>
 *   <li><code>-verbose:gc</code> - activate the logging of garbage collection information in its simplest form.</li>
 *   <li><code>-XX:+PrintGCDetails</code> - activate detailed information about garbage collection.</li>
 *   <li><code>-XX:+PrintGCTimeStamps</code> - include a timestamp in the output reflecting the real-time passed in seconds since the JVM started.</li>
 *   <li><code>-XX:+PrintGCDateStamps</code> - include the absolute date and time at the start of each line.</li>
 *   <li><code>-Xloggc</code> - by default the GC log is written to stdout. Specify an output file via this argument.</li>
 * </ul>
 * <p>
 * It should be noted that the first three arguments are not strictly necessary but for the purposes or the example
 * help really simplify things.
 *
 */
public class VerboseGarbageCollectorRunner {

    private static Map<String, String> stringContainer = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Start of program!");
        String stringWithPrefix = "stringWithPrefix";

        // Load Java Heap with 3 M java.lang.String instances
        for (int i = 0; i < 3000000; i++) {
            String newString = stringWithPrefix + i;
            stringContainer.put(newString, newString);
        }
        System.out.println("MAP size: " + stringContainer.size());

        // Explicit GC!
        System.gc();

        // Remove 2 M out of 3 M
        for (int i = 0; i < 2000000; i++) {
            String newString = stringWithPrefix + i;
            stringContainer.remove(newString);
        }

        System.out.println("MAP size: " + stringContainer.size());
        System.out.println("End of program!");
    }

}
