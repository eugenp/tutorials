package org.baeldung.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.baeldung.resources.AutoClosableResources_First;
import org.baeldung.resources.AutoClosableResources_Second;

public class JavaTryWithResources {

    private void useTryWithResources() throws FileNotFoundException {
		try (PrintWriter pw = new PrintWriter(new File("d://test.txt"))) {
			pw.println("Hello World");
		}
		
	}
    
    private void replaceTryCatchFinally_with_tryWithResources() {
    	
    	try (Scanner sc = new Scanner(new File("d://test.txt"))) {
    		while (sc.hasNext())
    		System.out.println(sc.nextLine());
    	} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
    	
    }
    private void useTryWithResourcesWithMultipleResources() throws FileNotFoundException {
    	
    	try (Scanner sc = new Scanner(new File("d://test.txt"));
    			PrintWriter pw = new PrintWriter(new File("d://testwrite.txt"))) {
    		while (sc.hasNext()) {
    			pw.print(sc.nextLine());
    		}
    	}
    }
    
    private void orderOfClosingResources() throws Exception {
    	try (AutoClosableResources_First af = new AutoClosableResources_First();
    			AutoClosableResources_Second as = new AutoClosableResources_Second()) {
    		af.doSomething();
    		as.doSomething();
    	}
    }
    
    private void orderOfExceptionThrown() throws Exception {
    	
    	AutoClosableResources_First af = new AutoClosableResources_First();
    	try {
    		af.doSomething();
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		af.close();
    	}
    }

}
