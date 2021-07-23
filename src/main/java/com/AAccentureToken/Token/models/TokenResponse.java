package com.AAccentureToken.Token.models;


public class TokenResponse {
	private String tokenAcesso;
	private String reativarToken;
	private Long expira;
	private String tipo;

	public TokenResponse() {
	}

	public TokenResponse(String tokenAcesso, String reativarToken, Long expira, String tipo) {
		this.tokenAcesso = tokenAcesso;
		this.reativarToken = reativarToken;
		this.expira = expira;
		this.tipo = tipo;
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
}
