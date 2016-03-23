package org.baeldung.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/addFile1", method = RequestMethod.POST)
	public String submit(@RequestParam("file") final MultipartFile file, final ModelMap modelMap) {

		modelMap.addAttribute("fileName", file.getOriginalFilename());
		modelMap.addAttribute("fileType", file.getContentType());

		return "fileUploadView";
	}

	@RequestMapping(value = "/addFile2", method = RequestMethod.POST)
	public String submit(final HttpServletRequest request, final HttpServletResponse response,
			final ModelMap modelMap) {

		final String TEMP_PATH = "C:\\Users\\ivan\\Desktop\\tmp\\";

		try {

			final DiskFileItemFactory factory = new DiskFileItemFactory();

			// Configure a repository (to ensure a secure temp location is used)
			final File repository = new File(TEMP_PATH);
			factory.setRepository(repository);

			// Create a new file upload handler
			final ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			final List<FileItem> items = upload.parseRequest(request);

			final Iterator<FileItem> iter = items.iterator();

			while (iter.hasNext()) {

				final FileItem item = iter.next();

				if (!item.isFormField()) {

					final File targetFile = new File(TEMP_PATH + item.getName());
					FileUtils.copyInputStreamToFile(item.getInputStream(), targetFile);

					modelMap.addAttribute("fileName", item.getName());
					modelMap.addAttribute("fileType", item.getContentType());
				}
			}

		} catch (final FileUploadException e) {
			e.printStackTrace();

		} catch (final IOException e) {
			e.printStackTrace();
		}

		return "fileUploadView";
	}
}
