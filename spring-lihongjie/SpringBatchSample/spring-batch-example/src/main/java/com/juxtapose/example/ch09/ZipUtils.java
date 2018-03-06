/**
 * 
 */
package com.juxtapose.example.ch09;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-7上午11:09:14
 */
public class ZipUtils {
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isZipFile(String fileName) {
		if (fileName != null && !"".equals(fileName.trim())) {
			if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip")) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 
	 * @param filePath
	 * @param outFileDir
	 */
	public static void decompressZip(String filePath, String outFileDir) {
		if(!isZipFile(filePath)){
			return;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}
		File outFileDirFile = new File(outFileDir);
		if(!outFileDirFile.exists()){
			outFileDirFile.mkdirs();
		}

		InputStream is = null;
		ZipArchiveInputStream zais = null;
		try {
			is = new FileInputStream(file);
			zais = new ZipArchiveInputStream(is);
			ArchiveEntry archiveEntry = null;
			while ((archiveEntry = zais.getNextEntry()) != null) {
				String entryFileName = archiveEntry.getName();
				String entryFilePath = outFileDir + File.separator + entryFileName;
				byte[] content = new byte[(int) archiveEntry.getSize()];
				zais.read(content);
				OutputStream os = null;
				try {
					File entryFile = new File(entryFilePath);
					if(!entryFile.exists()){
						entryFile.createNewFile();
					}
					os = new FileOutputStream(entryFile);
					os.write(content);
				} catch (IOException e) {
					throw new IOException(e);
				} finally {
					if (os != null) {
						os.flush();
						os.close();
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (zais != null) {
					zais.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
