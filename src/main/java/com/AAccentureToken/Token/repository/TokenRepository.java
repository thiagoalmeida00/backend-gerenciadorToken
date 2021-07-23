package com.AAccentureToken.Token.repository;

import com.AAccentureToken.Token.emuns.Situacao;
import com.AAccentureToken.Token.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Token findByTokenAcesso(String token);

	Token findByTokenAcessoAndSituacao(String token, Situacao situacao);

	Token findByTokenAcessoAndReativarToken(String tokenAcesso, String reativarToken);

	Token findByTokenAcessoAndTipo(String token, String tipo);
}
