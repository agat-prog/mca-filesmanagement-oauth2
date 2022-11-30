package mca.filesmanagement.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Configurador de seguridad del servicio OAuth2 que va a ser levantado.
 * 
 * @author agat
 */
@Configuration
public class AuthorizacionServerConfiguration
		extends
			AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${mca.filesmanagement.oauth2.oauthServerPassword:???}")
	private String oauthServerPassword;

	public AuthorizacionServerConfiguration() {
		super();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.userDetailsService(this.userDetailsService)
				.authenticationManager(this.authenticationManager)
				.tokenStore(tokenStore);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
		clients.inMemory().withClient("cliente")
				.authorizedGrantTypes("password", "authorization_code",
						"refresh_token", "implicit")
				.scopes("read", "write").autoApprove(true)
				.secret(passwordEncoder.encode(oauthServerPassword));;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}
}
