package com.AAccentureToken.Token.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class TokenExceptionHandler {

	@ExceptionHandler(value = {TokenFormatException.class})
	public ResponseEntity<Object> handlerTokenFormatException(TokenFormatException e) {
		TokenException tokenException = new TokenException();
		tokenException.setMessage(e.getMessage());
		tokenException.setHttpStatus(HttpStatus.BAD_REQUEST);
		tokenException.setTimestamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(tokenException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = {TokenExpireInvalidException.class})
	public ResponseEntity<Object> handlerTokenExpireException(TokenExpireInvalidException e) {
		TokenException tokenException = new TokenException();
		tokenException.setMessage(e.getMessage());
		tokenException.setHttpStatus(HttpStatus.MULTIPLE_CHOICES);
		tokenException.setTimestamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(tokenException, HttpStatus.MULTIPLE_CHOICES);
	}

	@ExceptionHandler(value = {TokenAppException.class})
	public ResponseEntity<Object> handlerTokenAppException(TokenAppException e) {
		TokenException tokenException = new TokenException();
		tokenException.setMessage(e.getMessage());
		tokenException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		tokenException.setTimestamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(tokenException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = {TokenNotFoundException.class})
	public ResponseEntity<Object> handlerTokenNotFoundException(TokenNotFoundException e) {
		TokenException tokenException = new TokenException();
		tokenException.setMessage(e.getMessage());
		tokenException.setHttpStatus(HttpStatus.NOT_FOUND);
		tokenException.setTimestamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(tokenException, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = {TokenExpireException.class})
	public ResponseEntity<Object> handlerTokenExpireException(TokenExpireException e) {
		TokenException tokenException = new TokenException();
		tokenException.setMessage(e.getMessage());
		tokenException.setHttpStatus(HttpStatus.MULTIPLE_CHOICES);
		tokenException.setTimestamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(tokenException, HttpStatus.MULTIPLE_CHOICES);
	}

	@ExceptionHandler(value = {TokenDeleteActiveException.class})
	public ResponseEntity<Object> handlerTokenExpireException(TokenDeleteActiveException e) {
		TokenException tokenException = new TokenException();
		tokenException.setMessage(e.getMessage());
		tokenException.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
		tokenException.setTimestamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(tokenException, HttpStatus.NOT_ACCEPTABLE);
	}
}
