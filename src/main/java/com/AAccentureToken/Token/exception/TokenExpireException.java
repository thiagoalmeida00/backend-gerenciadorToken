package com.AAccentureToken.Token.exception;

public class TokenExpireException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenExpireException(String message) {
		super(message);
	}

	public TokenExpireException(String message, Throwable cause) {
		super(message, cause);
	}
}
