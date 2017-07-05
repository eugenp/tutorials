package com.baeldung;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitor {

	public static void main(String[] args) throws Exception {
		File folder = FileUtils.getTempDirectory();
		startFileMonitor(folder);
	}

	/**
	 * @param folder
	 * @throws Exception
	 */
	public static void startFileMonitor(File folder) throws Exception {
		FileAlterationObserver observer = new FileAlterationObserver(folder);
		FileAlterationMonitor monitor = new FileAlterationMonitor(5000);

		FileAlterationListener fileAlterationListener = new FileAlterationListenerAdaptor() {
			// created files
			@Override
			public void onFileCreate(File file) {
				try {
					System.out.println("File created: " + file.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// deleted files
			@Override
			public void onFileDelete(File file) {
				try {
					System.out.println("File deleted: " + file.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		observer.addListener(fileAlterationListener);
		monitor.addObserver(observer);
		monitor.start();
	}
}