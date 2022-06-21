package com.baeldung;

import com.baeldung.JarTool;
import java.io.IOException;
import java.util.jar.*;

public class Driver {

	public static void main(String[] args) throws IOException {
		JarTool tool = new JarTool();
		tool.startManifest();
		tool.addToManifest("Main-Class","com.baeldung.HelloWorld");
		JarOutputStream target = tool.openJar("C:\\Users\\Tim\\Documents\\tutorials\\java-jar\\timdevries\\HelloWorld.jar");
		tool.addFile(target,"C:\\Users\\Tim\\Documents\\tutorials\\java-jar\\timdevries\\"
                                   ,"C:\\Users\\Tim\\Documents\\tutorials\\java-jar\\timdevries\\com\\baeldung\\HelloWorld.class");
		target.close();
 	}
}