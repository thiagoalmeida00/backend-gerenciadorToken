package com.AAccentureToken.Token.models;

import com.AAccentureToken.Token.emuns.Situacao;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 128, unique = true)
	private String tokenAcesso;

	@Column(length = 128, unique = true)
	private String reativarToken;

	private String credencial;
	private Long expira;
	private String tipo;
	private LocalDateTime dataHora;
	private Situacao situacao;

	public Token() {
		this.dataHora = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
	}

	public Token(String credencial, long expira) {
		this.setCredencial(credencial);
		this.setExpira(expira);
		this.dataHora = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCredencial() {
		return credencial;
	}

	public void setCredencial(String credencial) {
		this.credencial = credencial;
	}

	public String getTokenAcesso() {
		return tokenAcesso;
	}

	public void setTokenAcesso(String tokenAcesso) {
		this.tokenAcesso = tokenAcesso;
	}

	public String getReativarToken() {
		return reativarToken;
	}

	public void setReativarToken(String reativarToken) {
		this.reativarToken = reativarToken;
	}

	public Long getExpira() {
		return expira;
	}

	public void setExpira(Long expira) {
		this.expira = expira;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Token)) return false;
		Token token = (Token) o;
		return Objects.equals(id, token.id) && Objects.equals(tokenAcesso, token.tokenAcesso) && Objects.equals(reativarToken, token.reativarToken) && Objects.equals(credencial, token.credencial) && Objects.equals(expira, token.expira) && Objects.equals(tipo, token.tipo) && Objects.equals(dataHora, token.dataHora) && situacao == token.situacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tokenAcesso, reativarToken, credencial, expira, tipo, dataHora, situacao);
	}

	@Override
	public String toString() {
		return "Token{" +
				"tokenAcesso='" + tokenAcesso + '\'' +
				", reativarToken='" + reativarToken + '\'' +
				", expira='" + expira + '\'' +
				", tipo='" + tipo + '\'' +
				'}';
	}
}