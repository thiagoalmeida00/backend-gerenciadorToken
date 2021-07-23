package com.AAccentureToken.Token;

import com.AAccentureToken.Token.controllers.TokenController;
import com.AAccentureToken.Token.emuns.Situacao;
import com.AAccentureToken.Token.exception.TokenNotFoundException;
import com.AAccentureToken.Token.models.Token;
import com.AAccentureToken.Token.models.TokenReativarResponse;
import com.AAccentureToken.Token.models.TokenResponse;
import com.AAccentureToken.Token.repository.TokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

public class TokenTest {

	private static final Logger LOG = LoggerFactory.getLogger(TokenTest.class);

	private Token token;

	private Token add() {
		Token token = new Token();
		token.setExpira(3600000L);
		token.setSituacao(Situacao.ATIVO);
		token.setReativarToken("123");
		token.setTokenAcesso("321");
		token.setTipo("Rebatedor");
		token.setId(1L);
		token.setCredencial("Basico ZXU6YWx0ZXJhw6fDo29fbGVpdHVyYV9leGNsdXNhbw");
		return token;
	}

	private void loadToken() {
		this.token = this.add();
	}

	@MockBean
	private TokenRepository tokenRepository;

	@Test
	void main() {
		LOG.info("------token-------");
	}

	@Test
	void validaTokenRepositoryTest() {
		LOG.info("------ init validaTokenRepositoryTest -------");
		this.loadToken();

		TokenRepository mockToken = mock(TokenRepository.class);

		when(mockToken.findByTokenAcessoAndTipo("321", "Rebatedor"))
				.thenReturn(this.token);

		Token tokenGET = mockToken.findByTokenAcessoAndTipo("321", "Rebatedor");

		Assertions.assertSame(tokenGET.getTokenAcesso(), this.token.getTokenAcesso());
		Assertions.assertSame(tokenGET.getCredencial(), this.token.getCredencial());
		Assertions.assertSame(tokenGET.getSituacao(), this.token.getSituacao());
		Assertions.assertSame(tokenGET.getReativarToken(), this.token.getReativarToken());
		Assertions.assertSame(tokenGET.getDataHora(), this.token.getDataHora());

		LOG.info("------ end validaTokenRepositoryTest -------");
	}
	// end tests repository

	//	init tests controllers
	@Test
	void addTokenTest() {
		LOG.info("------ init addTokenTest -------");
		this.loadToken();
		TokenResponse token = new TokenResponse();
		token.setExpira(this.token.getExpira());
		token.setTokenAcesso(this.token.getTokenAcesso());
		token.setReativarToken(this.token.getReativarToken());
		token.setTipo(this.token.getTipo());

		TokenController tokenControllerMock = mock(TokenController.class);

		when(tokenControllerMock.gerarToken(this.token)).
				thenReturn(token);

		TokenResponse tokenResponsePOST = tokenControllerMock.gerarToken(this.token);

		Assertions.assertEquals(tokenResponsePOST.getTokenAcesso(), token.getTokenAcesso());
		Assertions.assertEquals(tokenResponsePOST.getReativarToken(), token.getReativarToken());
		Assertions.assertEquals(tokenResponsePOST.getTipo(), token.getTipo());
		Assertions.assertEquals(tokenResponsePOST.getExpira(), token.getExpira());

		LOG.info("------ end addTokenTest -------");
	}

	@Test
	void usarTokenTest() {
		LOG.info("------ init usarTokenTest -------");
		this.loadToken();
		TokenResponse token = new TokenResponse();
		token.setExpira(this.token.getExpira());
		token.setTokenAcesso(this.token.getTokenAcesso());
		token.setReativarToken(this.token.getReativarToken());
		token.setTipo(this.token.getTipo());

		TokenController tokenControllerMock = mock(TokenController.class);

		doThrow(new TokenNotFoundException("format")).when(tokenControllerMock).usarToken(this.token.getTokenAcesso());

		LOG.info("------ end usarTokenTest -------");
	}

	@Test
	void validaExpiraTest() {
		LOG.info("------ init validaExpiraTest -------");
		this.loadToken();
		TokenResponse token = new TokenResponse();
		token.setExpira(this.token.getExpira());
		token.setTokenAcesso(this.token.getTokenAcesso());
		token.setReativarToken(this.token.getReativarToken());
		token.setTipo(this.token.getTipo());

		TokenController tokenControllerMock = mock(TokenController.class);

		when(tokenControllerMock.validarToken(this.token.getTokenAcesso())).
				thenReturn(token);

		TokenResponse tokenResponsePOST = tokenControllerMock.validarToken(this.token.getTokenAcesso());

		Assertions.assertEquals(tokenResponsePOST.getTokenAcesso(), token.getTokenAcesso());
		Assertions.assertEquals(tokenResponsePOST.getReativarToken(), token.getReativarToken());
		Assertions.assertEquals(tokenResponsePOST.getTipo(), token.getTipo());
		Assertions.assertEquals(tokenResponsePOST.getExpira(), token.getExpira());

		LOG.info("------ end validaExpiraTest -------");
	}

	@Test
	void reativarTokenExpiradoTest() {
		LOG.info("------ init validaExpiraTest -------");
		this.loadToken();
		TokenReativarResponse token = new TokenReativarResponse();
		token.setExpira(this.token.getExpira());
		token.setTokenAcesso(this.token.getTokenAcesso());
		token.setTipo(this.token.getTipo());

		TokenController tokenControllerMock = mock(TokenController.class);

		when(tokenControllerMock.reativarTokenExpirado(this.token)).
				thenReturn(token);

		TokenReativarResponse tokenResponsePOST = tokenControllerMock.reativarTokenExpirado(this.token);

		Assertions.assertEquals(tokenResponsePOST.getTokenAcesso(), token.getTokenAcesso());
		Assertions.assertEquals(tokenResponsePOST.getTipo(), token.getTipo());
		Assertions.assertEquals(tokenResponsePOST.getExpira(), token.getExpira());

		LOG.info("------ end validaExpiraTest -------");
	}

	@Test
	void deleteTest() {
		LOG.info("------ init deleteTest -------");
		this.loadToken();
//		TokenReativarResponse token = new TokenReativarResponse();
//		token.setExpira(this.token.getExpira());
//		token.setTokenAcesso(this.token.getTokenAcesso());
//		token.setTipo(this.token.getTipo());

		TokenController tokenControllerMock = mock(TokenController.class);

		doThrow(new TokenNotFoundException("format")).when(tokenControllerMock).delete(this.token.getTokenAcesso());

		LOG.info("------ end deleteTest -------");
	}
}
