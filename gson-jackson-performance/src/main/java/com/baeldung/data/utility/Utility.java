package com.baeldung.data.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Utility {

	public static String generateRandomName() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz ".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static String generateRandomAddress() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz ".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 30; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static String generateRandomZip() {
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static String generateRandomPhone() {
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static String readFile(String fileName , Class clazz) throws IOException {
		String dir = clazz.getResource("/").getFile();
		dir = dir + "/" + fileName;

		BufferedReader br = new BufferedReader(new FileReader(dir));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static String bytesIntoMB(long bytes) {
		long kilobyte = 1024;
		long megabyte = kilobyte * 1024;

		if ((bytes >= 0) && (bytes < kilobyte)) {
			return bytes + " B";

		} else if ((bytes >= kilobyte) && (bytes < megabyte)) {
			return (bytes / kilobyte) + " KB";

		} else if ((bytes >= megabyte)) {
			return (bytes / megabyte) + " MB";
		}

		return null;
	}
}
