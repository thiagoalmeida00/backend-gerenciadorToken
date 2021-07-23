package com.AAccentureToken.Token.models;

public class TokenReativarResponse {
	private String tokenAcesso;
	private String tipo;
	private Long expira;

	public TokenReativarResponse() {
	}

	public String getTokenAcesso() {
		return tokenAcesso;
	}

	public void setTokenAcesso(String tokenAcesso) {
		this.tokenAcesso = tokenAcesso;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getExpira() {
		return expira;
	}

	public void setExpira(Long expira) {
		this.expira = expira;
	}
}
