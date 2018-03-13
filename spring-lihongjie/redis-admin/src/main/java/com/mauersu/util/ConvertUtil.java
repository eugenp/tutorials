package com.mauersu.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class ConvertUtil {

	public static void convertByteToString(RedisConnection connection, Set<byte[]> keysSet, List<RKey> tempList) {
		StringRedisSerializer stringSerializer = new StringRedisSerializer(Charset.forName("UTF8"));
		for(byte[] byteArray: keysSet) {
			String converted = stringSerializer.deserialize(byteArray);
			DataType dateType = null;
			switch(RedisApplication.showType) {
			case show:
				dateType = connection.type(byteArray);
				break;
			case hide:
				dateType = DataType.NONE;
				break;
			default:
				dateType = connection.type(byteArray);
				break;
			}
			
			RKey rkey = new RKey(converted, dateType); 
			tempList.add(rkey);
		}
	}
	
	public static double[] convert2Double(String[] strings) {
		if(strings==null) return null;
		double[] doubleList = new double[strings.length];
		for(int i=0;i<strings.length;i++) {
			double d = Double.parseDouble(strings[i]);
			doubleList[i] = d;
		}
		return doubleList;
	}
}
