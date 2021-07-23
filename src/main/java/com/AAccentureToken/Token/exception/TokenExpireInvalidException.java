package com.AAccentureToken.Token.exception;

public class TokenExpireInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenExpireInvalidException(String message) {
		super(message);
	}

	public TokenExpireInvalidException(String message, Throwable cause) {
		super(message, cause);
	}
}
