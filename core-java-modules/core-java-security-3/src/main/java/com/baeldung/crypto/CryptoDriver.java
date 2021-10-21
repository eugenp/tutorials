package com.baeldung.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;

import com.baeldung.crypto.utils.CryptoUtils;

public class CryptoDriver {
	
	private byte[] ecbEncrypt(SecretKey key, byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	private byte[] ecbDecrypt(SecretKey key, byte[] cipherText) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(cipherText);
	}

	public void encryptAesEcb() throws GeneralSecurityException {
		SecretKey key = CryptoUtils.generateKey();
		byte[] plaintext = ("Encrypt this").getBytes();
		byte[] ciphertext = ecbEncrypt(key, plaintext);
		byte[] decryptedtext = ecbDecrypt(key, ciphertext);
	}

	private byte[] cbcEncrypt(SecretKey key, IvParameterSpec iv, byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		return cipher.doFinal(data);

	}

	private byte[] cbcDecrypt(SecretKey key, IvParameterSpec iv, byte[] cipherText) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		return cipher.doFinal(cipherText);
	}

	public void aesCbc() throws GeneralSecurityException {
		SecretKey key = CryptoUtils.generateKey();
		IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES");
		byte[] plaintext = ("Encrypt this block for testing").getBytes();

		byte[] ciphertext = cbcEncrypt(key, iv, plaintext);
		byte[] decryptedtext = cbcDecrypt(key, iv, ciphertext);
	}

	private byte[] cfbEncrypt(SecretKey key, IvParameterSpec iv, byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		return cipher.doFinal(data);
	}

	private byte[] cfbDecrypt(SecretKey key, IvParameterSpec iv, byte[] cipherText) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		return cipher.doFinal(cipherText);
	}

	public void aesCfb() throws GeneralSecurityException {
		SecretKey key = CryptoUtils.generateKey();
		IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES/CFB/NoPadding");
		byte[] plaintext = ("Encrypt this for testing").getBytes();

		byte[] ciphertext = cfbEncrypt(key, iv, plaintext);
		byte[] decryptedtext = cfbDecrypt(key, iv, ciphertext);
	}

	private byte[] ofbEncrypt(SecretKey key, IvParameterSpec iv, byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/OFB32/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		return cipher.doFinal(data);
	}

	private byte[] ofbDecrypt(SecretKey key, IvParameterSpec iv, byte[] cipherText) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/OFB32/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		return cipher.doFinal(cipherText);
	}

	public void aesOfb() throws GeneralSecurityException {
		SecretKey key = CryptoUtils.generateKey();
		IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES/OFB32/PKCS5Padding");
		byte[] plaintext = ("Encrypt this for testing").getBytes();

		byte[] ciphertext = ofbEncrypt(key, iv, plaintext);
		byte[] decryptedtext = ofbDecrypt(key, iv, ciphertext);
	}

	public static byte[][] ctrEncrypt(SecretKey key, IvParameterSpec iv, byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		return new byte[][] { cipher.getIV(), cipher.doFinal(data) };
	}

	public static byte[] ctrDecrypt(SecretKey key, byte[] iv, byte[] cipherText) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		return cipher.doFinal(cipherText);
	}
	
	public void aesCtr() throws GeneralSecurityException {
		SecretKey key = CryptoUtils.generateKey();
		IvParameterSpec iv = CryptoUtils.getIVSecureRandom("AES/CTR/NoPadding");
		byte[] plaintext = ("Encrypt this for testing").getBytes();

		byte[][] ciphertext = ctrEncrypt(key, iv, plaintext);
		byte[] decryptedtext = ctrDecrypt(key, ciphertext[0], ciphertext[1]);
	}
	
	public static byte[][] gcmEncrypt(SecretKey key, byte[] iv,  byte[] data) throws GeneralSecurityException {
	       Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	       cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
	        byte[] ciphertext = cipher.doFinal(data);
	        return new byte[][] {
	        	iv, ciphertext
	        };	
	}

	public static byte[] gcmDecrypt(SecretKey key, byte[] iv, byte[] ciphertext) throws GeneralSecurityException {
	
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] plaintext = cipher.doFinal(ciphertext);
        return plaintext;	
	}
	
	public void aesGcm() throws GeneralSecurityException {
		SecretKey key = CryptoUtils.generateKey();
		byte[] iv = CryptoUtils.getRandomIVWithSize(12);
		byte[] plaintext = ("Encrypt this for testing").getBytes();

		byte[][] ciphertext = gcmEncrypt(key, iv, plaintext);
		byte[] decryptedtext = gcmDecrypt(key, ciphertext[0], ciphertext[1]);
	}
}
