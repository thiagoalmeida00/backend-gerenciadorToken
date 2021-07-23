package com.AAccentureToken.Token.service;

import com.AAccentureToken.Token.emuns.Situacao;
import com.AAccentureToken.Token.exception.TokenDeleteActiveException;
import com.AAccentureToken.Token.exception.TokenExpireException;
import com.AAccentureToken.Token.exception.TokenFormatException;
import com.AAccentureToken.Token.exception.TokenNotFoundException;
import com.AAccentureToken.Token.models.Token;
import com.AAccentureToken.Token.models.TokenReativarResponse;
import com.AAccentureToken.Token.models.TokenResponse;
import com.AAccentureToken.Token.repository.TokenRepository;
import com.AAccentureToken.Token.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TokenServiceImplements implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public TokenResponse save(Token token) {
		token.setTokenAcesso(this.hash());
		token.setReativarToken(this.hash());
		token.setTipo("Rebatedor");
		token.setSituacao(Situacao.ATIVO);
		this.tokenRepository.save(token);
		return this.buildTokenResponse(token);
	}

	private String hash() {
		String uuid = HashUtils.getHashBase64(HashUtils.generateUUID());
		return HashUtils.getHashBase64(uuid);
	}

	@Override
	public void userToken(String token) throws TokenFormatException, TokenNotFoundException, TokenExpireException {
		String[] credenciais = this.formatToken(token);
		Token t = this.getToken(credenciais[1]);
		this.tokenExpirado(t);
		this.updateSituacao(t, Situacao.UTILIZADO);
	}

	@Override
	public TokenResponse validarToken(String token) throws TokenFormatException, TokenNotFoundException, TokenExpireException {
		String[] credenciais = this.formatToken(token);
		Token t = this.getToken(credenciais[1]);
//		if (t.getExpira() > 0)
//			throw new TokenExpireException("Token valido, não pode reativar");
		this.tokenExpirado(t);
		return this.buildTokenResponse(t);
	}

	private String[] formatToken(String token) throws TokenFormatException {
		String[] credenciais = token.trim().split(" ");
		if (!credenciais[0].equals("Rebatedor"))
			throw new TokenFormatException("Token formato invalido!");
		return credenciais;
	}

	private Token getToken(String credencial) throws TokenNotFoundException {
		Token token = this.tokenRepository.findByTokenAcesso(credencial);
		if (token == null)
			throw new TokenNotFoundException("Token não encontrado");
		return token;
	}

	private void tokenExpirado(Token token) throws TokenExpireException {
		long expira = this.convertMillisecondForMinutes(token);
		long time = this.diffLocalDateTime(token, expira);
		token.setExpira(this.convertMinutesForMillisecond(time));

		if (time <= 0) {
			this.updateSituacao(token, Situacao.EXPIRADO);
			throw new TokenExpireException("Token expirado");
		}

		if (token.getSituacao() == Situacao.EXPIRADO || token.getSituacao() == Situacao.UTILIZADO)
			throw new TokenExpireException("Token já utilizado ou expirado");
	}

	private long convertMillisecondForMinutes(Token token) {
		return token.getExpira() / 1000 / 60;
	}

	private long convertMinutesForMillisecond(long time) {
		return time * 1000 * 600;
	}

	private long diffLocalDateTime(Token token, long expira) {
		LocalDateTime now = LocalDateTime.now();
		return ChronoUnit.MILLIS.between(now, token.getDataHora().plusMinutes(expira));
	}

	@Override
	public TokenReativarResponse update(Token token) throws TokenFormatException, TokenNotFoundException, TokenExpireException {
		String[] credenciais = this.formatToken(token.getTokenAcesso());

		Token t = this.getTokenReativar(credenciais[1], token.getReativarToken());

		if (t.getSituacao() == Situacao.UTILIZADO)
			throw new TokenExpireException("Token já utilizado");

		token.setCredencial(t.getCredencial());
		token.setTokenAcesso(this.hash());
		token.setReativarToken(this.hash());
		token.setTipo("Rebatedor");
		token.setSituacao(Situacao.ATIVO);

		this.updateSituacao(t, Situacao.UTILIZADO);

		return this.buildTokenReativarResponse(this.tokenRepository.save(token));
	}

	private Token getTokenReativar(String token, String reativarToken) {
		Token t = this.tokenRepository.findByTokenAcessoAndReativarToken(token, reativarToken);
		if (t == null)
			throw new TokenNotFoundException("Token não encontrado");
		return t;
	}

	private void updateSituacao(Token token, Situacao situacao) {
		this.tokenRepository.findById(token.getId()).map(o -> {
			o.setSituacao(situacao);
			return this.tokenRepository.save(o);
		});
	}

	private TokenResponse buildTokenResponse(Token token) {
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setExpira(token.getExpira());
		tokenResponse.setReativarToken(token.getReativarToken());
		tokenResponse.setTipo(token.getTipo());
		tokenResponse.setTokenAcesso(token.getTokenAcesso());
		return tokenResponse;
	}

	private TokenReativarResponse buildTokenReativarResponse(Token token) {
		TokenReativarResponse tokenReativarResponse = new TokenReativarResponse();
		tokenReativarResponse.setExpira(token.getExpira());
		tokenReativarResponse.setTipo(token.getTipo());
		tokenReativarResponse.setTokenAcesso(token.getTokenAcesso());
		return tokenReativarResponse;
	}

	@Override
	public void delete(String token) throws TokenNotFoundException, TokenDeleteActiveException, TokenFormatException {
		String[] credenciais = this.formatToken(token);
		Token token1 = this.tokenRepository.findByTokenAcessoAndTipo(credenciais[1], credenciais[0]);

		if (token1 == null)
			throw new TokenNotFoundException("Token não encontrado");

		if (token1.getSituacao() == Situacao.ATIVO)
			throw new TokenDeleteActiveException("Token ativo nao pode ser deletado");

		this.tokenRepository.delete(token1);
	}
}
