package com.baeldung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.baeldung.excel.*;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

@Controller
public class ExcelController {

    private String fileLocation;

    @Resource(name = "excelPOIHelper")
    private ExcelPOIHelper excelPOIHelper;

    @RequestMapping(method = RequestMethod.GET, value = "/excelProcessing")
    public String getExcelProcessingPage() {
        return "excel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadExcelFile")
    public String uploadFile(Model model, MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
        FileOutputStream f = new FileOutputStream(fileLocation);
        int ch = 0;
        while ((ch = in.read()) != -1) {
            f.write(ch);
        }
        f.flush();
        f.close();
        model.addAttribute("message", "File: " + file.getOriginalFilename() + " has been uploaded successfully!");
        return "excel";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/readPOI")
    public String readPOI(Model model) throws IOException {

        if (fileLocation != null) {
            if (fileLocation.endsWith(".xlsx") || fileLocation.endsWith(".xls")) {
                Map<Integer, List<MyCell>> data = excelPOIHelper.readExcel(fileLocation);
                model.addAttribute("data", data);
            } else {
                model.addAttribute("message", "Not a valid excel file!");
            }
        } else {
            model.addAttribute("message", "File missing! Please upload an excel file.");
        }
        return "excel";
    }

}