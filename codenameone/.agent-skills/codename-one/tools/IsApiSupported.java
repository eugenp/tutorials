import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/// Checks whether a fully-qualified class name (or class#method) is part of the
/// Codename One supported Java API subset.
///
/// Usage:
///
///     java tools/IsApiSupported.java java.util.HashMap
///     java tools/IsApiSupported.java java.nio.file.Files
///     java tools/IsApiSupported.java java.util.HashMap#put
///
/// The tool resolves the highest-versioned `java-runtime` jar under
/// ~/.m2/repository/com/codenameone/java-runtime/ and looks the class up there.
///
/// Exit codes:
///
///   0 — supported (printed as `YES`)
///   1 — not supported / not found (printed as `NO`)
///   2 — usage / discovery error
///
/// Limitations: method-level lookups currently confirm the class is present and
/// then leave you to verify the exact signature in the jar (use `javap -p` on the
/// printed path) — proper bytecode inspection without external dependencies is
/// beyond the scope of this stub.
public class IsApiSupported {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java IsApiSupported.java <fully.qualified.ClassName>[#methodName]");
            System.exit(2);
        }
        String target = args[0];
        String className = target;
        String method = null;
        int hash = target.indexOf('#');
        if (hash > 0) {
            className = target.substring(0, hash);
            method = target.substring(hash + 1);
        }

        Path jar = findLatestJavaRuntimeJar();
        if (jar == null) {
            System.err.println("Could not locate java-runtime jar under "
                    + System.getProperty("user.home") + "/.m2/repository/com/codenameone/java-runtime/. "
                    + "Run a Codename One build (or `mvn -pl common compile`) to populate the local cache.");
            System.exit(2);
        }
        System.err.println("[IsApiSupported] using " + jar);

        String entryName = className.replace('.', '/') + ".class";
        try (JarFile jf = new JarFile(jar.toFile())) {
            JarEntry e = jf.getJarEntry(entryName);
            if (e == null) {
                System.out.println("NO");
                System.exit(1);
            }
            if (method != null) {
                System.out.println("YES  (class present at " + jar.getFileName() + "!" + entryName
                        + " — for method-level confirmation run `javap -p -classpath " + jar + " "
                        + className + "` and grep for `" + method + "`)");
            } else {
                System.out.println("YES");
            }
        }
    }

    private static Path findLatestJavaRuntimeJar() throws IOException {
        Path baseDir = Paths.get(System.getProperty("user.home"),
                ".m2", "repository", "com", "codenameone", "java-runtime");
        if (!Files.isDirectory(baseDir)) return null;
        List<Path> candidates = new ArrayList<>();
        try (DirectoryStream<Path> versions = Files.newDirectoryStream(baseDir)) {
            for (Path versionDir : versions) {
                if (!Files.isDirectory(versionDir)) continue;
                try (DirectoryStream<Path> jars = Files.newDirectoryStream(versionDir, "java-runtime-*.jar")) {
                    for (Path jar : jars) {
                        if (jar.getFileName().toString().endsWith("-sources.jar")) continue;
                        if (jar.getFileName().toString().endsWith("-javadoc.jar")) continue;
                        candidates.add(jar);
                    }
                }
            }
        }
        if (candidates.isEmpty()) return null;
        candidates.sort((a, b) -> compareVersions(versionOf(b), versionOf(a)));
        return candidates.get(0);
    }

    private static String versionOf(Path jar) {
        Path versionDir = jar.getParent();
        return versionDir == null ? "" : versionDir.getFileName().toString();
    }

    private static int compareVersions(String left, String right) {
        String[] leftParts = left.split("-", 2);
        String[] rightParts = right.split("-", 2);
        int numeric = compareNumericVersions(leftParts[0], rightParts[0]);
        if (numeric != 0) return numeric;
        String leftSuffix = leftParts.length > 1 ? leftParts[1] : "";
        String rightSuffix = rightParts.length > 1 ? rightParts[1] : "";
        return leftSuffix.compareTo(rightSuffix);
    }

    private static int compareNumericVersions(String left, String right) {
        String[] leftParts = left.split("\\.");
        String[] rightParts = right.split("\\.");
        int max = Math.max(leftParts.length, rightParts.length);
        for (int i = 0; i < max; i++) {
            int comparison = Integer.compare(versionPart(leftParts, i), versionPart(rightParts, i));
            if (comparison != 0) return comparison;
        }
        return 0;
    }

    private static int versionPart(String[] parts, int index) {
        if (index >= parts.length) return 0;
        try {
            return Integer.parseInt(parts[index]);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
