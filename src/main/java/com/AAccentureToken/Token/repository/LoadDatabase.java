package com.AAccentureToken.Token.repository;

import com.AAccentureToken.Token.emuns.Situacao;
import com.AAccentureToken.Token.models.Token;
import com.AAccentureToken.Token.models.User;
import com.AAccentureToken.Token.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	private User u1 = new User("gabriel", "gabriel@email.com", "123");
	private User u2 = new User("thiago", "thiago@email.com", "123");
	private User u3 = new User("andre", "andre@email.com", "123");

	private static final String SECURITY_KEY = Base64.getEncoder().encodeToString("LIACFWSDSHBO".getBytes(StandardCharsets.UTF_8));
	private String credencial1 = "Basico " + Base64.getEncoder().encodeToString((u1 + SECURITY_KEY).getBytes(StandardCharsets.UTF_8));
	private String credencial2 = "Basico " + Base64.getEncoder().encodeToString((u2 + SECURITY_KEY).getBytes(StandardCharsets.UTF_8));
	private String credencial3 = "Basico " + Base64.getEncoder().encodeToString((u3 + SECURITY_KEY).getBytes(StandardCharsets.UTF_8));

	private Timestamp agora = new Timestamp(System.currentTimeMillis());

	@Bean
	CommandLineRunner iniDatabase(TokenRepository tokenRepository) {
		LocalDateTime limite = this.agora.toLocalDateTime();
		LocalDateTime limite1 = this.agora.toLocalDateTime();
		limite.toString();
		LocalDateTime newTime = limite1.plusMinutes(10);


		Token t1 = new Token(credencial1, 3600000L);
		t1.setSituacao(Situacao.ATIVO);

		String uuid = HashUtils.getHashBase64(HashUtils.generateUUID());
		String base64 = HashUtils.getHashBase64(uuid);
		t1.setTokenAcesso(base64);
		uuid = HashUtils.getHashBase64(HashUtils.generateUUID());
		base64 = HashUtils.getHashBase64(uuid);
		t1.setReativarToken(base64);
		t1.setTipo("Rebatedor");

		Token t2 = new Token(credencial2, 3600000L);
		t2.setSituacao(Situacao.ATIVO);

		uuid = HashUtils.getHashBase64(HashUtils.generateUUID());
		base64 = HashUtils.getHashBase64(uuid);
		t2.setTokenAcesso(base64);
		uuid = HashUtils.getHashBase64(HashUtils.generateUUID());
		base64 = HashUtils.getHashBase64(uuid);
		t2.setReativarToken(base64);
		t2.setTipo("Rebatedor");

		Token t3 = new Token(credencial3, 3600000L);
		t3.setSituacao(Situacao.ATIVO);

		uuid = HashUtils.getHashBase64(HashUtils.generateUUID());
		base64 = HashUtils.getHashBase64(uuid);
		t3.setTokenAcesso(base64);
		uuid = HashUtils.getHashBase64(HashUtils.generateUUID());
		base64 = HashUtils.getHashBase64(uuid);
		t3.setReativarToken(base64);
		t3.setTipo("Rebatedor");

		return args -> {
//			log.info("loading: " + tokenRepository.save(t1));
//			log.info("loading: " + tokenRepository.save(t2));
//			log.info("loading: " + tokenRepository.save(t3));
			log.info("date: " + limite);
			log.info("date: " + newTime);
		};
	}
}
