package org.commons.web.controller;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * https://poi.apache.org/spreadsheet/index.html
 * @author lihongjie
 *
 */
@Controller
public class FileUploadController {

	@RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
	public String singleUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
		String filename = multipartFile.getOriginalFilename();
		System.out.println("file name: " + multipartFile.getOriginalFilename());
		if (filename.endsWith(".txt")) {
			
		} else if (filename.endsWith(".md")) {
			
		}
		Workbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
		SXSSFWorkbook 
		return "success";
	}
}
