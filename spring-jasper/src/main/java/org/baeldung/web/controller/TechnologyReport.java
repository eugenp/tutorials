package org.baeldung.web.controller;

import java.io.File;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.baeldung.persistence.model.Technology;
import org.baeldung.web.form.TechnologyForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TechnologyReport {
    @ModelAttribute("technologies")
    public ArrayList<String> getTechnologies() { 
        ArrayList<String> technologies = new ArrayList<String>();
        technologies.add("Java");
        technologies.add("C");
        technologies.add("C++");
        technologies.add("C#");
        technologies.add("VB");
        technologies.add("ASP");
		
        return technologies;
    }	
	
    @RequestMapping(value = "/loadTechnology", method = RequestMethod.GET)
    public String loadHelloWorldPage(@ModelAttribute("technologyForm") TechnologyForm technologyForm, Model model) {
        model.addAttribute("technologyForm", technologyForm);
        return "showTechnology";

    }

    @RequestMapping(value = "/showTechnologyReport", method = RequestMethod.POST)
    public String generateReport(@Valid @ModelAttribute("technologyForm") TechnologyForm technologyForm, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {

        if (result.hasErrors()) {
            System.out.println("validation error occured in jasper input form");
            return "loadTechnology";

        }

        try {
            String reportFileName = "list_technologies";

            String tempUser = technologyForm.getInputName();
            HashMap<String, Object> hmParams = new HashMap<String, Object>();

            hmParams.put("tempUser", tempUser);
            ClassLoader classLoader = getClass().getClassLoader(); 
              
            if (classLoader.getResource(reportFileName + ".jasper") == null) {
                JasperCompileManager.compileReportToFile(classLoader.getResource(reportFileName + ".jrxml").getFile());
            }
            File reportFile = new File(classLoader.getResource(reportFileName + ".jasper").getFile());
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());

            ArrayList<Technology> technologies = new ArrayList<Technology>();
            Technology technology = new Technology();
            for (String selectedTechnology : technologyForm.getTechnologies()) {
            	technology = new Technology();
            	technology.setTechonologyName(selectedTechnology);
            	technologies.add(technology);
            }
            JRBeanCollectionDataSource technologyDataSource = new JRBeanCollectionDataSource(technologies);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hmParams, technologyDataSource);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=list_technologies_report.pdf");

            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

        } catch (Exception exp) {

            System.out.println("Exception::" + exp.toString());

        }

        return null;
    }

}
