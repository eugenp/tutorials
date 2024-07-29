package com.baeldung.shallow_deep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.baeldung.shallow_deep.models.Document;
import com.baeldung.shallow_deep.models.DocDescription;

@SpringBootApplication
public class ShallowDeepApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShallowDeepApplication.class, args);
	}

	public void shallowExample() {

		Document originalDocument = new Document(123, new DocDescription(56, "XLS")); 
		Document shallowCopyDocument = new Document(originalDocument); 
		shallowCopyDocument.getDocDescription().setType("PDF"); 
		
		System.out.println("Original: " + originalDocument); // {123, (56, "PDF")} 
		System.out.println("Shallow Copy Document: " + shallowCopyDocument); // {123, (56, "PDF")}
	}

	public void deepExample() {
		Document originalDocument = new Document(123, new DocDescription(56, "XLS")); 
		Document deepCopyDocument = DeepCopyUtil.deepCopy(originalDocument, Document.class); 
		deepCopyDocument.getDocDescription().setType("PDF"); 
		
		System.out.println("Original: " + originalDocument); // {123, (56, "XLS")} 
		System.out.println("Deep Copy Document: " + deepCopyDocument); // {123, (56, "PDF")}
	}

}
