package com.lihongjie.collection.tutorial.tutorial3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Map 增加，删除，修改，遍历
 * @author lihongjie
 *
 */
public class HashMapTest {

	public static void main(String[] args) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("one", 1);
		map.put("two", 2);
		map.put("three", 3);
		map.put("one", 4); // 修改map中的值

		System.out.println(map.get("one"));
		System.out.println(map.toString());
		
		map.remove(4); // 删除 value不成功
		System.out.println(map);
		map.remove("one");
		System.out.println(map); // 根据key删除
		
		//通过返回Map 的所有key组成的Set集合
		//从而遍历Map的每个key-value对
		for (String str : map.keySet()) {
			System.out.println("keys : " + str);
		}
	}

}
