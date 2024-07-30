package com.baeldung.shallow_deep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.baeldung.shallow_deep.models.Document;
import com.baeldung.shallow_deep.models.DocDescription;

@SpringBootApplication
public class ShallowDeepApplication {

	public static void main(String[] args) {
		shallowExample();
		deepExample();
		copyOnWriteExample();
	}

	public static void shallowExample() {

		Document originalDocument = new Document(123, new DocDescription(56, "XLS")); 
		Document shallowCopyDocument = new Document(originalDocument); 
		shallowCopyDocument.getDocDescription().setType("PDF"); 
		
		System.out.println("Original: " + originalDocument); // {123, (56, "PDF")} 
		System.out.println("Shallow Copy Document: " + shallowCopyDocument); // {123, (56, "PDF")}
	}

	public static void deepExample() {
		Document originalDocument = new Document(123, new DocDescription(56, "XLS")); 
		Document deepCopyDocument = DeepCopyUtil.deepCopy(originalDocument, Document.class); 
		deepCopyDocument.getDocDescription().setType("PDF"); 
		
		System.out.println("Original: " + originalDocument); // {123, (56, "XLS")} 
		System.out.println("Deep Copy Document: " + deepCopyDocument); // {123, (56, "PDF")}
	}

	public static void copyOnWriteExample() {
		Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
        CopyDocument copyDocument = new CopyDocument(originalDocument);
        
        // Get original document ID
        System.out.println("Original Document ID: " + copyDocument.getDocId());
        
        // Change document ID
        copyDocument.setDocId(456);
        
        // Get updated document ID
        System.out.println("Updated Document ID: " + copyDocument.getDocId());
        
        // Check original document ID
        System.out.println("Original Document ID After Update: " + originalDocument.getDocId());
	}

}
