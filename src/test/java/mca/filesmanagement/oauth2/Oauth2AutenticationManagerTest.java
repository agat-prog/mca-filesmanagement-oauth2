package mca.filesmanagement.oauth2;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mca.filesmanagement.oauth2.service.UserService;

@ExtendWith(SpringExtension.class)
@Tag("UnitTest")
@DisplayName("Authentication manager test")
public class Oauth2AutenticationManagerTest {

	@InjectMocks
	private Oauth2AutenticationManager oauth2AutenticationManager;

	@Mock
	private UserService userService;

	@Mock
	private PasswordEncoder passwordEncoder;

	/** Se testea el manager de autenticación. */
	@Test
	@DisplayName("Test find a existing user")
	public void givenAAuthenticationWhenSetThenReturnAuthenticacion() {
		long id = 1;
		when(this.userService.findByUserName(any())).thenReturn(Optional.ofNullable(UserFactory.createUser(id)));
		when(this.passwordEncoder.matches(any(), any())).thenReturn(true);

		Authentication authentication = new Authentication(){
			private static final long serialVersionUID = 8412819671799450613L;

			@Override
			public String getName() {
				return String.format(UserFactory.USER_USER_NAME_FORMAT, id);
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return null;
			}

			@Override
			public Object getCredentials() {
				return null;
			}

			@Override
			public Object getDetails() {
				return null;
			}

			@Override
			public Object getPrincipal() {
				return String.format(UserFactory.USER_USER_NAME_FORMAT, id);
			}

			@Override
			public boolean isAuthenticated() {
				return false;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			}};

		authentication = this.oauth2AutenticationManager.authenticate(authentication);

		assertNotNull(authentication);
		assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
		assertTrue(authentication.getName().equals(String.format(UserFactory.USER_USER_NAME_FORMAT, id)));
	}

	/** Se testea el manager de autenticación. */
	@Test
	@DisplayName("Test find a existing user")
	public void givenAAuthenticationFailedWhenSetThenThrowException() {
		long id = 1;
		when(this.userService.findByUserName(any())).thenReturn(Optional.ofNullable(UserFactory.createUser(id)));
		when(this.passwordEncoder.matches(any(), any())).thenReturn(false);

		final Authentication authentication = new Authentication(){
			private static final long serialVersionUID = 8412819671799450613L;

			@Override
			public String getName() {
				return String.format(UserFactory.USER_USER_NAME_FORMAT, id);
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return null;
			}

			@Override
			public Object getCredentials() {
				return null;
			}

			@Override
			public Object getDetails() {
				return null;
			}

			@Override
			public Object getPrincipal() {
				return String.format(UserFactory.USER_USER_NAME_FORMAT, id);
			}

			@Override
			public boolean isAuthenticated() {
				return false;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			}};


		assertThrows(BadCredentialsException.class,
				() -> this.oauth2AutenticationManager.authenticate(authentication));
		verify(this.userService, times(1)).findByUserName(any());
	}
}
