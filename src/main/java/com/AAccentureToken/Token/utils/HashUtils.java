package com.AAccentureToken.Token.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class HashUtils {

	private static final String SECURITY_KEY = Base64.getEncoder().encodeToString("LIACFWSDSHBO".getBytes(StandardCharsets.UTF_8));

	public static String getHashBase64(String value) {
		return Base64.getEncoder().encodeToString((value + SECURITY_KEY).getBytes(StandardCharsets.UTF_8));
	}

	public static String generateUUID() {
		String hash = UUID.randomUUID().toString();
		if (hash.length() > 128)
			hash = hash.substring(0, 128);
		return hash;
	}

	public static String getHash(String value) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
		return hash.toString();
	}
}
