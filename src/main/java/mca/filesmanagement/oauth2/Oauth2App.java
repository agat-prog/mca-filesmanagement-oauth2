package mca.filesmanagement.oauth2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Iniciador/lanzador principal de la aplicación.
 * 
 * @author agat
 */
@SpringBootApplication
@EnableAuthorizationServer
public class Oauth2App {

	/**
	 * Método principal del lanzador de la aplicación.
	 *
	 * @param args
	 *            command line args.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Oauth2App.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
