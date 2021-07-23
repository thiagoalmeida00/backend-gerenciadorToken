package com.AAccentureToken.Token.controllers;

import com.AAccentureToken.Token.exception.*;
import com.AAccentureToken.Token.models.Token;
import com.AAccentureToken.Token.models.TokenReativarResponse;
import com.AAccentureToken.Token.models.TokenResponse;
import com.AAccentureToken.Token.service.TokenServiceImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens/")
public class TokenController {

	@Autowired
	private TokenServiceImplements tokenService;

	/**
	 * está método utiliza o token e coloca sua situação como utilizado
	 * assim não poderá utiliza-lo novamente
	 * @param token: String
	 */
	@GetMapping("/usar-token/{token_access}")
	public void usarToken(@PathVariable("token_access") String token) {
		try {
			this.tokenService.userToken(token);
		} catch (TokenExpireException e) {
			throw new TokenExpireException("Token expirado");
		} catch (TokenNotFoundException e) {
			throw new TokenNotFoundException("Token não encontrado");
		} catch (TokenFormatException e) {
			throw new TokenFormatException("Token formato invalido!");
		} catch (Exception e) {
			throw new TokenAppException();
		}
	}

	/**
	 * está método vai receber um token com as credenciais e o expira (opcional)
	 * @param token: Token
	 * @return TokenResponse
	 */
	@PostMapping
	public TokenResponse gerarToken(@RequestBody(required = false) Token token) {
		try {
			this.validaExpira(token);
			this.formatToken(token);
			return this.tokenService.save(token);
		} catch (TokenFormatException e) {
			throw new TokenFormatException("Token formato invalido!");
		} catch (TokenExpireInvalidException e) {
			throw new TokenExpireInvalidException("Expire invalid!");
		} catch (Exception e) {
			throw new TokenAppException();
		}
	}

	private void validaExpira(Token token) throws TokenExpireInvalidException {
		if (token.getExpira() == null) {
			token.setExpira(3600000L);
			return;
		}
		if (token.getExpira() > 3600000L || token.getExpira() < 0L)
			throw new TokenExpireInvalidException("Expire invalid!");
	}

	private void formatToken(Token token) throws TokenFormatException {
		String[] credenciais = token.getCredencial().trim().split(" ");
		if (!credenciais[0].equals("Basico"))
			throw new TokenFormatException("Token formato invalido!");
	}

	/**
	 * Método para validar se o token ainda pode ser usado
	 * @param token: String
	 * @return TokenResponse
	 */
	@GetMapping("/validar-token/{token_access}")
	public TokenResponse validarToken(@PathVariable("token_access") String token) {
		try {
			return this.tokenService.validarToken(token);
		} catch (TokenExpireException e) {
			throw new TokenExpireException("Token expirado");
		} catch (TokenNotFoundException e) {
			throw new TokenNotFoundException("Token não encontrado");
		} catch (TokenFormatException e) {
			throw new TokenFormatException("Token formato invalido!");
		} catch (Exception e) {
			throw new TokenAppException();
		}
	}

	/**
	 * método usado para reativar o token gerando um novo token
	 * @param token: String
	 * @return TokenReativarResponse
	 */
	@PutMapping
	public TokenReativarResponse reativarTokenExpirado(@RequestBody Token token) {
		try {
			return this.tokenService.update(token);
		} catch (TokenExpireException e) {
			throw new TokenExpireException("Token já utilizado");
		} catch (TokenNotFoundException e) {
			throw new TokenNotFoundException("Token não encontrado");
		} catch (TokenFormatException e) {
			throw new TokenFormatException("Token formato invalido!");
		} catch (Exception e) {
			throw new TokenAppException();
		}
	}

	/**
	 * método para deletar token, se o token estiver ativo vai gerar TokenDeleteActiveException
	 * @param token: String
	 */
	@DeleteMapping("{token_access}")
	public void delete(@PathVariable("token_access") String token) {
		try {
			this.tokenService.delete(token);
		} catch (TokenFormatException e) {
			throw new TokenFormatException("Token formato invalido!");
		} catch (TokenDeleteActiveException e) {
			throw new TokenDeleteActiveException("Token ativo nao pode ser deletado");
		} catch (TokenNotFoundException e) {
			throw new TokenNotFoundException("Token não encontrado");
		} catch (Exception e) {
			throw new TokenAppException();
		}
	}
}
