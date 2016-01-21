package org.baeldung.web.controller.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileuploadController {
	private static Logger log = LoggerFactory.getLogger(FileuploadController.class);

	private final String BASE_DIR = System.getProperty("user.dir") + File.pathSeparator + "tmp";

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map<String, Long> upload(@RequestParam("file") final List<MultipartFile> files) {
		final Map<String, Long> uploadedFiles = new LinkedHashMap<>();

		files.forEach(f -> {
			final String name = f.getOriginalFilename();
			final long fileSize = f.getSize();

			if (fileSize != 0) {
				try {
					final File saveToFile = new File(BASE_DIR + File.separator + name);
					if (!saveToFile.exists())
						saveToFile.mkdirs();
					f.transferTo(saveToFile);

					uploadedFiles.put(name, fileSize);
				} catch (IllegalStateException | IOException e) {
					log.error("Error while saving file {}", name, e);
					uploadedFiles.put(name, -1l);
				}
			}
		});

		return uploadedFiles;
	}

}
