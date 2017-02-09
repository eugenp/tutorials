package com.baeldung.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PdfBoxEncryptionExample {

	public static void main(String[] args) throws IOException {
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		AccessPermission accessPermission = new AccessPermission();
		accessPermission.setCanPrint(false);
		accessPermission.setCanModify(false);

		StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy("ownerpass", "userpass", accessPermission);
		document.protect(standardProtectionPolicy);
		document.save("pdfBoxEncryption.pdf");
		document.close();
	}
}
