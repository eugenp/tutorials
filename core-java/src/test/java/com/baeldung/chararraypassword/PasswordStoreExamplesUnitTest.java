package com.baeldung.chararraypassword;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 密码存储测试
 */
public class PasswordStoreExamplesUnitTest {

	String stringPassword = "password";
	char[] charPassword = new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};

	@Test
	public void givenStringHashCode_WhenStringValueChanged_ThenHashCodesNotEqualAndValesNotEqual() {
		String originalHashCode = Integer.toHexString(stringPassword.hashCode());

		stringPassword = "********";
		String changedHashCode = Integer.toHexString(stringPassword.hashCode());

		System.out.println("originalHashCode:{}" + originalHashCode);
		System.out.println("changedHashCode:{}" + changedHashCode);

		assertThat(originalHashCode).isNotEqualTo(changedHashCode);
		assertThat(stringPassword).isNotEqualTo("password");
	}

	@Test
	public void givenStringHashCode_WhenStringValueChangedAndStringValueReassigned_ThenHashCodesEqualAndValesEqual() {
		String originalHashCode = Integer.toHexString(stringPassword.hashCode());

		stringPassword = "********";
		stringPassword = "password";
		//重新分配散列码
		String reassignedHashCode = Integer.toHexString(stringPassword.hashCode());

		System.out.println("originalHashCode:{}" + originalHashCode);
		System.out.println("reassignedHashCode:{}" + reassignedHashCode);

		assertThat(originalHashCode).isEqualTo(reassignedHashCode);
		assertThat(stringPassword).isEqualTo("password");
	}

	@Test
	public void givenStringHashCode_WhenStringValueReplaced_ThenHashCodesEqualAndValesEqual() {
		String originalHashCode = Integer.toHexString(stringPassword.hashCode());

		String newString = "********";
		stringPassword.replace(stringPassword, newString);

		System.out.println("stringPassword:{}" + stringPassword);

		String hashCodeAfterReplace = Integer.toHexString(stringPassword.hashCode());

		System.out.println("originalHashCode:{}" + originalHashCode);
		System.out.println("hashCodeAfterReplace:{}" + hashCodeAfterReplace);

		assertThat(originalHashCode).isEqualTo(hashCodeAfterReplace);
		assertThat(stringPassword).isEqualTo("password");
	}

	/**
	 * @see java.util.Arrays#fill(char[] a, char val)
	 */
	@Test
	public void givenCharArrayHashCode_WhenArrayElementsValueChanged_ThenHashCodesEqualAndValesNotEqual() {
		String originalHashCode = Integer.toHexString(charPassword.hashCode());

		Arrays.fill(charPassword, '*');

		System.out.println("charPassword:{}" + Arrays.toString(charPassword));

		String changedHashCode = Integer.toHexString(charPassword.hashCode());

		System.out.println("originalHashCode:{}" + originalHashCode);
		System.out.println("changedHashCode:{}" + changedHashCode);

		assertThat(originalHashCode).isEqualTo(changedHashCode);
		assertThat(charPassword).isNotEqualTo(new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'});
	}

	@Test
	public void whenCallingToStringOfString_ThenValuesEqual() {
		assertThat(stringPassword.toString()).isEqualTo("password");
	}

	@Test
	public void whenCallingToStringOfCharArray_ThenValuesNotEqual() {
		assertThat(charPassword.toString()).isNotEqualTo("password");
	}
}
