package com.baeldung.web.reports;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.Exporter;

/**
 * = JasperReportsExporter
 * 
 * This interface defines the operations for a JasperReport exporter.
 * 
 * JasperReports library already provides an Exporter interface called
 * {@link Exporter}. However, it doesn't provides an operation that writes the
 * exported JasperReport into the {@link HttpServletResponse}.
 */
public interface JasperReportsExporter {

	/**
	 * This operation must be implemented by every JasperReport exporter to be
	 * able to write a generated report into a the {@link HttpServletResponse}}.
	 * 
	 * @param jp
	 *            The generated JasperReport.
	 * @param fileName
	 *            The fileName of the exported JasperReport
	 * @param response
	 *            The HttpServletResponse where generated report has been
	 *            written
	 * @throws JRException
	 *             during JasperReport export.
	 * @throws IOException
	 *             when writes the ByteArrayOutputStream into the
	 *             HttpServletResponse
	 */
	public void export(JasperPrint jp, String fileName, HttpServletResponse response) throws JRException, IOException;

}