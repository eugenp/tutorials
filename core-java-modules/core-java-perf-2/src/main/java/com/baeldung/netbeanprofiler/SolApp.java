package com.baeldung.netbeanprofiler;

import com.baeldung.netbeanprofiler.galaxy.SolarSystem;
import org.netbeans.lib.profiler.heap.GCRoot;
import org.netbeans.lib.profiler.heap.Instance;
import org.netbeans.lib.profiler.heap.JavaClass;
import org.netbeans.lib.profiler.heap.Field;
import org.netbeans.lib.profiler.heap.Heap;
import org.netbeans.lib.profiler.heap.HeapFactory;
import org.netbeans.lib.profiler.heap.HeapSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.netbeans.lib.profiler.heap.GCRoot.JAVA_FRAME;
import static org.netbeans.lib.profiler.heap.GCRoot.JNI_GLOBAL;
import static org.netbeans.lib.profiler.heap.GCRoot.JNI_LOCAL;
import static org.netbeans.lib.profiler.heap.GCRoot.THREAD_OBJECT;

public class SolApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolApp.class);
    private static Heap heap;

    public SolApp(String heapDumpPath) throws Exception {
        heap = HeapFactory.createHeap(new File(heapDumpPath));
    }

    public static void solarSystem() throws IOException {
        List<String> planet = new ArrayList<>();
        planet.add("Mercury");
        planet.add("Mars");
        planet.add("Earth");
        planet.add("Venus");
        SolarSystem solarSystem = new SolarSystem(1, "Sol System", planet);
        solarSystem.logSolarSystem();

        File file = new File("solarSystem.hprof");
        if (file.exists()) {
            file.delete();
        }
        HeapDump.dumpHeap("solarSystem.hprof", true);
    }

    public static void heapDumpSummary() {
        HeapSummary summary = heap.getSummary();
        LOGGER.info("Total instances: " + summary.getTotalLiveInstances());
        LOGGER.info("Total bytes: " + summary.getTotalLiveBytes());
        LOGGER.info("Time: " + summary.getTime());
        LOGGER.info("GC Roots: " + heap.getGCRoots().size());
        LOGGER.info("Allocated bytes: " + summary.getTotalAllocatedBytes());
        LOGGER.info("Total classes: " + heap.getAllClasses().size());
    }

    public static void solarSystemSummary() {
        JavaClass solarSystemClass = heap.getJavaClassByName("com.baeldung.netbeanprofiler.galaxy.SolarSystem");
        if (solarSystemClass == null) {
            LOGGER.error("Class not found");
            return;
        }
        List<Instance> instances = solarSystemClass.getInstances();
        long totalSize = 0;
        long instancesNumber = solarSystemClass.getInstancesCount();
        for (Instance instance : instances) {
            totalSize += instance.getSize();
        }
        LOGGER.info("Total SolarSystem instances: " + instancesNumber);
        LOGGER.info("Total memory used by SolarSystem instances: " + totalSize);
    }

    public static void logFieldDetails() {
        JavaClass solarSystemClass = heap.getJavaClassByName("com.baeldung.netbeanprofiler.galaxy.SolarSystem");
        if (solarSystemClass == null) {
            LOGGER.error("Class not found");
            return;
        }

        List<Field> fields = solarSystemClass.getFields();
        for (Field field : fields) {
            LOGGER.info("Field: " + field.getName());
            LOGGER.info("Type: " + field.getType().getName());
        }
    }

    public static void analyzeGCRoots() {
        LOGGER.info("Analyzing GC Roots:");
        Collection<GCRoot> gcRoots = heap.getGCRoots();
        if (gcRoots == null) {
            LOGGER.error("No GC Roots found");
            return;
        }

        LOGGER.info("Total GC Roots: " + gcRoots.size());

        int threadObj = 0, jniGlobal = 0, jniLocal = 0, javaFrame = 0, other = 0;

        for (GCRoot gcRoot : gcRoots) {
            Instance instance = gcRoot.getInstance();
            String kind = gcRoot.getKind();

            switch (kind) {
                case THREAD_OBJECT:
                    threadObj++;
                    break;
                case JNI_GLOBAL:
                    jniGlobal++;
                    break;
                case JNI_LOCAL:
                    jniLocal++;
                    break;
                case JAVA_FRAME:
                    javaFrame++;
                    break;
                default:
                    other++;
            }

            if (threadObj + jniGlobal + jniLocal + javaFrame + other <= 10) {
                LOGGER.info("  GC Root: " + instance.getJavaClass().getName());
                LOGGER.info("    Kind: " + kind);
                LOGGER.info("    Size: " + instance.getSize() + " bytes");
            }
        }

        LOGGER.info("\nGC Root Summary:");
        LOGGER.info("  Thread Objects: " + threadObj);
        LOGGER.info("  JNI Global References: " + jniGlobal);
        LOGGER.info("  JNI Local References: " + jniLocal);
        LOGGER.info("  Java Frames: " + javaFrame);
        LOGGER.info("  Other: " + other);
    }

    public static void analyzeClassHistogram() {
        LOGGER.info("\nClass Histogram (Top 10 by instance count):");
        List<JavaClass> unmodifiableClasses = heap.getAllClasses();
        if (unmodifiableClasses == null) {
            LOGGER.error("No classes found");
            return;
        }
        List<JavaClass> classes = new ArrayList<>(unmodifiableClasses);
        classes.sort((c1, c2) -> Long.compare(c2.getInstancesCount(), c1.getInstancesCount()));

        for (int i = 0; i < Math.min(10, classes.size()); i++) {
            JavaClass javaClass = classes.get(i);
            LOGGER.info("  " + javaClass.getName());
            LOGGER.info("    Instances: " + javaClass.getInstancesCount());
            LOGGER.info("    Total size: " + javaClass.getAllInstancesSize() + " bytes");
        }
    }

}
