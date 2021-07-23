package com.AAccentureToken.Token.exception;

public class TokenFormatException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	public TokenFormatException(String message) {
		super(message);
	}

	public TokenFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
