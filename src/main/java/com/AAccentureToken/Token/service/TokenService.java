package com.AAccentureToken.Token.service;

import com.AAccentureToken.Token.models.Token;
import com.AAccentureToken.Token.models.TokenReativarResponse;
import com.AAccentureToken.Token.models.TokenResponse;

public interface TokenService {

	TokenResponse save(Token token);

	void userToken(String token);

	TokenResponse validarToken(String token);

	TokenReativarResponse update(Token token);

	void delete(String token);

}
