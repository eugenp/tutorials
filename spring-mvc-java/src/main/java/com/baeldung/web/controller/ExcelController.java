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
import jxl.read.biff.BiffException;
import java.util.Map;
import java.util.ArrayList;
import javax.annotation.Resource;
import jxl.write.WriteException;

@Controller
public class ExcelController {

    private String fileLocation;

    @Resource(name = "jExcelHelper")
    private JExcelHelper jExcelHelper;

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
        System.out.println(fileLocation);
        model.addAttribute("message", "File: " + file.getOriginalFilename() + " has been uploaded successfully!");
        return "excel";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/readJExcel")
    public String readJExcel(Model model) throws IOException, BiffException {

        if (fileLocation != null) {
            if (fileLocation.endsWith(".xls")) {
                Map<Integer, ArrayList<String>> data = jExcelHelper.readJExcel(fileLocation);
                model.addAttribute("data", data);
            } else {
                model.addAttribute("message", "Not a valid .xls file!");
            }
        } else {
            model.addAttribute("message", "File missing! Please upload an excel file.");
        }
        return "excel";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/readPOI")
    public String readPOI(Model model) throws IOException {

        if (fileLocation != null) {
            if (fileLocation.endsWith(".xlsx")) {
                Map<Integer, ArrayList<String>> data = excelPOIHelper.readExcel(fileLocation);
                model.addAttribute("data", data);
            } else {
                model.addAttribute("message", "Not a valid .xlsx file!");
            }
        } else {
            model.addAttribute("message", "File missing! Please upload an excel file.");
        }
        return "excel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/writeJExcel")
    public String writeJExcel(Model model) throws IOException, BiffException, WriteException {

        jExcelHelper.writeJExcel();

        model.addAttribute("message", "Write successful!");

        return "excel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/writePOI")
    public String writePOI(Model model) throws IOException {

        excelPOIHelper.writeExcel();

        model.addAttribute("message", "Write successful!");

        return "excel";
    }

}