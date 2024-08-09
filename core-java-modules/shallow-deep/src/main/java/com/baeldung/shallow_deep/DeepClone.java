package com.baeldung.shallow_deep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.baeldung.shallow_deep.models.Document;
import com.baeldung.shallow_deep.models.DocDescription;

@SpringBootApplication
public class DeepClone implements Cloneable{

	public Object clone() throws CloneNotSupportedException {  
		return super.clone();  
	}

	public static void main(String[] args) {
		try { 
			Document originalDocument = new Document(123, new DocDescription(56, "XLS"));
			Document deepCopyDocument = (Document)d1.clone(); 
			
			System.out.println("Original: " + originalDocument); // {123, (56, "XLS")} 
			System.out.println("Deep Copy Document: " + deepCopyDocument); // {123, (56, "PDF")} 
		} 
			
			catch(CloneNotSupportedException e) {
			LOGGER.error("Clone error : ", e);
		}
	}
}