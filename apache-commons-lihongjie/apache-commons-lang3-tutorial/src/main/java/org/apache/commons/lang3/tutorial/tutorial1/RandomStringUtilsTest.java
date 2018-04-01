package org.apache.commons.lang3.tutorial.tutorial1;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringUtilsTest {

	public static void main(String[] args) {
		
		String str1 = RandomStringUtils.random(5);
		System.out.println(str1);
		String str2 = RandomStringUtils.randomNumeric(5);
		System.out.println(str2);
		String str3 = RandomStringUtils.randomAlphabetic(5);
		System.out.println(str3);
		String str4 = RandomStringUtils.randomAlphanumeric(5);
		System.out.println(str4);
		String str5 = RandomStringUtils.randomAscii(5);
		System.out.println(str5);
		String str6 = RandomStringUtils.random(25, "abcdefdsfsdfsd");
		System.out.println(str6);
	}
}
