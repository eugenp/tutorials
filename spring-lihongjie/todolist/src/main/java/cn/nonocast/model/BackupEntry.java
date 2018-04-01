package cn.nonocast.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BackupEntry implements Comparable<BackupEntry> {
	private Path path;
	private LocalDateTime createdAt;
	private long fileSize;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Path getPath() {
		return path;
	}

	public long getFileSize() {
		return fileSize;
	}

	public BackupEntry(Path path) {
		this.path = path;

		try {
			this.fileSize = Files.size(this.path);

			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			this.createdAt = LocalDateTime.ofInstant(attr.creationTime().toInstant(), ZoneId.systemDefault());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return this.path.getFileName().toString();
	}

	@Override
	public int compareTo(BackupEntry p) {
		return this.createdAt.compareTo(p.createdAt);
	}
}
