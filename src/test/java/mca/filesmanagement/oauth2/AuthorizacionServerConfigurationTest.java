package mca.filesmanagement.oauth2;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mca.filesmanagement.oauth2.config.AuthorizacionServerConfiguration;

@ExtendWith(SpringExtension.class)
@Tag("UnitTest")
@DisplayName("AuthorizacionServert tests")
public class AuthorizacionServerConfigurationTest {

	@InjectMocks
	private AuthorizacionServerConfiguration authorizacionServerConfiguration;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private TokenStore tokenStore;

	/** Se testea el manager de autenticación. */
	@Test
	@DisplayName("Test AuthorizationServerEndpointsConfigurer")
	public void givenAuthorizationServerEndpointWhenSetThenChanged() throws Exception {
		AuthorizationServerEndpointsConfigurer configurer = new AuthorizationServerEndpointsConfigurer();
		this.authorizacionServerConfiguration.configure(configurer);

		assertNotNull(authenticationManager);
	}
}
