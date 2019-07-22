package com.baeldung.application;

import com.baeldung.core.Crypto;
import com.baeldung.core.ICryptoService;

public class CryptoServiceAdapter implements ICryptoService{

	public String encryptString(String text) {
		Crypto engine = new Crypto("TestKey123456789");
		String encryptedString = engine.encrypt(text);
		return encryptedString;
	}

	public String decryptString(String encryptedText) {
		Crypto engine = new Crypto("TestKey123456789");
		String text = engine.decrypt(encryptedText);
		return text;
	}

}
