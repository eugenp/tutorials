package com.baeldung.createjar;

import java.io.*;
import java.util.jar.*;

public class JarTool {
    private Manifest manifest = new Manifest();

    public void addToManifest(String key, String value) {
        manifest.getMainAttributes()
            .put(new Attributes.Name(key), value);
    }

    public void addDirectoryEntry(JarOutputStream target, String parentPath, File dir) throws IOException {
        String remaining = "";
        if (parentPath.endsWith(File.separator))
            remaining = dir.getAbsolutePath()
                .substring(parentPath.length());
        else
            remaining = dir.getAbsolutePath()
                .substring(parentPath.length() + 1);
        String name = remaining.replace("\\", "/");
        if (!name.endsWith("/"))
            name += "/";
        JarEntry entry = new JarEntry(name);
        entry.setTime(dir.lastModified());
        target.putNextEntry(entry);
        target.closeEntry();
    }

    public void addFile(JarOutputStream target, String rootPath, String source) throws IOException {
        BufferedInputStream in = null;
        String remaining = "";
        if (rootPath.endsWith(File.separator))
            remaining = source.substring(rootPath.length());
        else
            remaining = source.substring(rootPath.length() + 1);
        String name = remaining.replace("\\", "/");
        JarEntry entry = new JarEntry(name);
        entry.setTime(new File(source).lastModified());
        target.putNextEntry(entry);
        in = new BufferedInputStream(new FileInputStream(source));
        byte[] buffer = new byte[1024];
        while (true) {
            int count = in.read(buffer);
            if (count == -1)
                break;
            target.write(buffer, 0, count);
        }
        target.closeEntry();
        in.close();
    }

    public JarOutputStream openJar(String jarFile) throws IOException {
        JarOutputStream target = new JarOutputStream(new FileOutputStream(jarFile), manifest);
        return target;
    }

    public void setMainClass(String mainFQCN) {
        if (mainFQCN != null && !mainFQCN.equals(""))
            manifest.getMainAttributes()
                .put(Attributes.Name.MAIN_CLASS, mainFQCN);
    }

    public void startManifest() {
        manifest.getMainAttributes()
            .put(Attributes.Name.MANIFEST_VERSION, "1.0");
    }
}