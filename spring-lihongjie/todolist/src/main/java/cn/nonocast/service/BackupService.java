package cn.nonocast.service;

import cn.nonocast.model.BackupEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BackupService {
	private static final Logger logger = LoggerFactory.getLogger(BackupService.class);

	@Value("${data.backup.path}")
	private String backupPath;


	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	private Path path;

	private List<BackupEntry> backupEntries;

	public int getBackupEntryCount() {
		return this.backupEntries.size();
	}

	public void sync() throws IOException {
		this.path = Paths.get(backupPath.replaceFirst("~", System.getProperty("user.home")));
		if(Files.exists(path)) {
			backupEntries = Files.list(path).filter(p -> p.toString().endsWith(".sql")).map(p -> new BackupEntry(p)).collect(Collectors.toList());
			Collections.sort(backupEntries, Collections.reverseOrder());
		} else {
			logger.warn("Path didn't exists: " + this.path);
		}
	}

	public Page<BackupEntry> findAll(Pageable pageable) {
		List<BackupEntry> entries = this.backupEntries.stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).collect(Collectors.toList());

		if(backupEntries.size() > 0) {
			return new PageImpl<>(
				entries,
				pageable,
				this.backupEntries.size()
			);
		}

		return new PageImpl<BackupEntry>(new ArrayList<>());
	}

	public Optional<BackupEntry> findByName(String q) {
		return backupEntries.stream().filter(p -> p.getFileName().toString().equals(q)).findFirst();
	}

	public void exportEntry() throws IOException, InterruptedException {
		Path q = Paths.get(
			backupPath.replaceFirst("~", System.getProperty("user.home")),
			String.format("data-%s.sql", LocalDateTime.now().toString().replace(":", "_"))
		);

		ProcessBuilder p = new ProcessBuilder("mysqldump", "todolist", "-u"+username, "-p"+password);
		p.redirectOutput(new File(q.toString()));
		p.start().waitFor();

		this.sync();
	}

	public void importEntry(BackupEntry entry) throws IOException {
		Path q = Paths.get(
			backupPath.replaceFirst("~", System.getProperty("user.home")),
			entry.getFileName()
		);

		ProcessBuilder p = new ProcessBuilder("mysql", "todolist", "-u"+username, "-p"+password);
		p.redirectInput(new File(entry.getPath().toString()));
		p.start();
	}
}
