package org.apache.commons.lang3.tutorial.tutorial1;

import org.apache.commons.lang3.StringUtils;

public class StringUtilsTest {

	public static void main(String[] args) {

		String str = "abcde";
		char[] chars = str.toCharArray();

		System.out.println(chars.length);

		String newString = StringUtils.join(chars, ',');
		System.out.println(newString);
	}
}
