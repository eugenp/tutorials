package cn.workcenter.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class StringUtil {
	
	public static void main(String[] args) {
		System.out.println(getRandom(6));
	}
	
	public static boolean isEmpty(String str){
		
		return ("".equals(str)||str == null) ;
	}
	
	public static String getRandom(int num) {
		String random = "";
		if(num<=0) 
			return random;
		for(int i=0;i<num;i++) {
			random += getRandom();
		}
		return random;
	}
	
	public static String getRandom(){
		return (int)(Math.random()*10)+"";
	}

	public static String getString(Map<String, Object> map, String key) {
		return getString(map, key, "");
	}
	
	public static String getString(Map<String, Object> map, String key, String defaultValue) {
		String value = (map.get(key)==null?"":map.get(key)).toString();
		value = StringUtils.isEmpty(value) ? "" : value;
		return value;
	}
	
	public static Date addMillis(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(calendar.getTimeInMillis() + millis);
		return calendar.getTime();

	}

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// drop "-" 
		return s.replaceAll("-", "");
	}

	public static String getParameterByDefault(HttpServletRequest request, String key, String defaultValue) {
		String value = request.getParameter(key);
		if(isEmpty(value)) {
			return defaultValue;
		}
		return value;
	}
	
}
