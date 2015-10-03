package java.org.baeldung.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Response {
	
	private String content;
	private Date date = new Date();
	
    public Response(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    
    public String getDate(){
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	return dateFormat.format(date);
    }
}
