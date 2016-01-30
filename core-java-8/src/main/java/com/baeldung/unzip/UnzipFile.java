package com.baeldung.unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
	public static void main(final String[] args) throws FileNotFoundException,
	IOException {
		final String commonPath = System.getenv("TEST_PATH");
		final String fileZip = commonPath + "/zipped/cities.zip";
		final String outputFolder = commonPath + "/unzipped/";
		final byte[] buffer = new byte[1024];
		try {
			final ZipInputStream zis = new ZipInputStream(new FileInputStream(
					fileZip));
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				final String fileName = zipEntry.getName();
				final File newFile = new File(outputFolder + fileName);
				final FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}
}