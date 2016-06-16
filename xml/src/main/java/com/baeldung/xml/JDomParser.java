package com.baeldung.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class JDomParser {

	private File file;

	public JDomParser(File file) {
		this.file = file;
	}

	public List<Element> getAllTitles() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(this.getFile());
			Element tutorials = doc.getRootElement();
			List<Element> titles = tutorials.getChildren("Tutorial");
			return titles;
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
