package com.baeldung.commons.untar.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;

import com.baeldung.commons.untar.TarExtractor;

public class TarExtractorVfs extends TarExtractor {

    public TarExtractorVfs(InputStream in, boolean gzip, Path destination) throws IOException {
        super(in, gzip, destination);
    }

    @Override
    public void untar() throws IOException {
        Path tmpTar = Files.createTempFile("temp", isGzip() ? ".tar.gz" : ".tar");
        Files.copy(getTarStream(), tmpTar, StandardCopyOption.REPLACE_EXISTING);

        FileSystemManager fsManager = VFS.getManager();
        String uri = String.format("%s:file://%s", isGzip() ? "tgz" : "tar", tmpTar);
        FileObject tar = fsManager.resolveFile(uri);

        for (FileObject entry : tar) {
            Path extractTo = Paths.get(getDestination().toString(), entry.getName()
                .getPath());

            if (entry.isReadable() && entry.getType() == FileType.FILE) {
                Files.createDirectories(extractTo.getParent());

                try (FileContent content = entry.getContent(); InputStream stream = content.getInputStream()) {
                    Files.copy(stream, extractTo, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }

        Files.delete(tmpTar);
    }
}