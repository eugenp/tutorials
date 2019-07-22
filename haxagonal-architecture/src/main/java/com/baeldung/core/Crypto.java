package com.baeldung.core;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	private Key aesKey;

	public Crypto(String key) {
		this.aesKey = new SecretKeySpec(key.getBytes(), "AES");
	}

	public String encrypt(String text) {

		Cipher cipher;
		String encryptedString = null;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			encryptedString = Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;

	}

	public String decrypt(String text) {

		Cipher cipher;
		String decryptedString = null;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] decodedBytes = Base64.getDecoder().decode(text);
			decryptedString = new String(cipher.doFinal(decodedBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return decryptedString;
	}

}
