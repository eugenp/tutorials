package cn.nonocast.admin.controller;

import cn.nonocast.model.BackupEntry;
import cn.nonocast.service.BackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Controller("adminDatabaseController")
@RequestMapping("/admin/backup")
public class BackupController {
	private static final Logger logger = LoggerFactory.getLogger(BackupController.class);

	@Autowired
	private BackupService backupService;

	@RequestMapping("")
	public String index(Model model, Pageable pageable) {
		model.addAttribute("page", backupService.findAll(pageable));
		return "admin/backup/index";
	}

	@RequestMapping("file")
	@ResponseBody
	public ResponseEntity<InputStreamResource> file(@RequestParam("q") String q, @RequestParam(value="op", required=false) String op) throws IOException {
		Optional<BackupEntry> entry = backupService.findByName(q);
		if(entry.isPresent()) {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(entry.get().getFileSize());
			if("download".equals(op)) {
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=" + entry.get().getFileName());
			} else {
				headers.setContentType(MediaType.TEXT_PLAIN);
			}

			return ResponseEntity.ok()
				.headers(headers)
				.body(new InputStreamResource(Files.newInputStream(entry.get().getPath())));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@RequestMapping(value="sync", method=RequestMethod.POST)
	public String sync() throws IOException {
		backupService.sync();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value="export", method=RequestMethod.POST)
	public String exportEntry() throws IOException, InterruptedException {
		backupService.exportEntry();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value="import", method=RequestMethod.GET)
	public String importEntry(@RequestParam("q") String q) throws IOException {
		Optional<BackupEntry> entry = backupService.findByName(q);
		if(entry.isPresent()) {
			backupService.importEntry(entry.get());
		}
		return "redirect:/admin/backup";
	}
}
