package com.baeldung.copyfiles;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

public class FileCopier {
	public static File copyWithIO(File original, File copied) throws IOException {
		try (InputStream in = new BufferedInputStream(new FileInputStream(original));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(copied))) {
			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		}
		return copied;
	}

	public static Path copyWithNio(Path original, Path copied) throws IOException {
		Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
		return copied;
	}

	public static File copyWithCommonsIO(File original, File copied) throws IOException {
		FileUtils.copyFile(original, copied);
		return copied;
	}
}
