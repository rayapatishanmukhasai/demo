package com.sesimagotag.training.demo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 */
public class CipherUtil {
	private static final String UTF_8 = "UTF-8";
	private static final String ENCRYPT_B64 = "encryptB64:";
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	// must be 16 chars
	// DO NEVER CHANGE THIS VALUE !
	public final static String INIT_VECTOR = "0123456789012345";
	// must be 16 chars
	// DO NEVER CHANGE THIS VALUE !
	private static final String SALT = "0123456789012345";
	private IvParameterSpec initVector;

	public CipherUtil(final String initVector) {
		try {
			this.initVector = new IvParameterSpec(initVector.getBytes("UTF-8"));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		CipherUtil c = new CipherUtil(INIT_VECTOR);
		String b64 = c.encryptAsB64("98765432109876543210");
		System.out.println("98765432109876543210");
		System.out.println(b64);
		System.out.println(c.decryptAsB64(b64));
	}

	public byte[] encrypt(byte[] message) {
		try {
			final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			final SecretKey secretKey = getKeyFromPassword(EnumEncryptionType.AES_128, new String(INIT_VECTOR), SALT);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, initVector);
			final byte[] cipherText = cipher.doFinal(message);
			return cipherText;
		} catch (final Exception e) {
			throw new RuntimeException("Error while encrypting", e);
		}
	}

	public byte[] decrypt(byte[] encryptedMessage) {
		try {
			final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			final SecretKey secretKey = getKeyFromPassword(EnumEncryptionType.AES_128, new String(INIT_VECTOR), SALT);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, initVector);
			final byte[] cipherText = cipher.doFinal(encryptedMessage);
			return cipherText;
		} catch (final Exception e) {
			throw new RuntimeException("Error while encrypting", e);
		}
	}

	public String encryptAsB64(final String message) throws InvalidKeyException, NoSuchPaddingException,
			NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException,
			InvalidKeySpecException, InvalidAlgorithmParameterException {
		return message;
	}

	public String decryptAsB64(final String message) throws InvalidKeyException, NoSuchPaddingException,
			NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		return message;
	}

	private static SecretKey getKeyFromPassword(final EnumEncryptionType encryptionType, final String password,
			final String salt) {
		try {
			final SecretKeyFactory factory = SecretKeyFactory.getInstance(encryptionType.algorithmToGenerateSecret);
			final KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, encryptionType.size);
			final SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
					.getEncoded(), ALGORITHM);
			return secret;
		} catch (final Exception e) {
			throw new RuntimeException("Error while generating the secret key", e);
		}
	}

	public static enum EnumEncryptionType {
		AES_128(128), AES_192(192), AES_256(256);

		private final int size;
		private final String algorithmToGenerateSecret = "PBKDF2WithHmacSHA256";

		private EnumEncryptionType(final int size) {
			this.size = size;
		}

	}

}
