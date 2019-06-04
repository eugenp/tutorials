package datastore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import domain.ObtainFacts;

public class FileAdapter implements ObtainFacts{
	
	private String filePath;
	
	public FileAdapter(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public Map<Integer,String> getFacts(){
		
		HashMap<Integer,String> result = new HashMap<Integer,String>();
		String line = "";
		
		BufferedReader br = null;
		int i=0;
		try {
			br=new BufferedReader(new FileReader(filePath));
			while((line = br.readLine()) != null) {
				result.put(i,line);
				i++;
			}
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
