package com.java.test;
import java.sql.Timestamp;
import java.util.*;
import java.io.File;
import java.io.IOException;


public class Test { 
	public static void main(String ar[]){
		Object[] obj1 = {"abc" , "pqr" , "xyz"};
		Object[] obj2 = {"12" , "23" , "34"};
		Object[] obj4 = {null , null , null};
		List list1 = new ArrayList();
		list1.add(obj4);
	//	list1.add(null);
		
		Iterator itr = list1.iterator();
		while(itr.hasNext()){
			Object[] obj3 =(Object[])itr.next();
			System.out.println("obj3[0] , obj3[1] "+obj3[0]+" "+obj3[1]);
		}
		
		String fullPath = "D:/check/testing.pdf";
		File f = new File(fullPath);
		try{
			System.out.println("f.getAbsolutePath() "+f.getAbsolutePath());
			System.out.println("f.getCanonicalPath() "+f.getCanonicalPath());
			System.out.println("f.getParent() "+f.getParent());
			Date date = new Date();
			long timeStamp = date.getTime();
			String newFile = f.getParent()+"\\"+timeStamp;
			System.out.println("newFile "+newFile);
			f.renameTo(new File(newFile));
			System.out.println("f.getAbsolutePath() "+f.getAbsolutePath());
			System.out.println("f.getName() "+f.getName());
			System.out.println("f.getCanonicalPath() "+f.getCanonicalPath());
			System.out.println("f.getParent() "+f.getParent());
		}catch(IOException io){
			io.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
