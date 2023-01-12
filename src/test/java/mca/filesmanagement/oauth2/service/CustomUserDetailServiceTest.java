package mca.filesmanagement.oauth2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mca.filesmanagement.oauth2.UserFactory;
import mca.filesmanagement.oauth2.port.in.IuserUseCase;

@ExtendWith(SpringExtension.class)
@Tag("UnitTest")
@DisplayName("Custom User Detail Services tests")
public class CustomUserDetailServiceTest {

	@InjectMocks
	private CustomUserDetailService customUserDetailService;

	@Mock
	private IuserUseCase userUseCase;

	@Mock
	private PasswordEncoder passwordEncoder;

	/** Configuración inicial. */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Se testea que dado un username válido, responde con el DTO correspondiente.
	 */
	@Test
	@DisplayName("Test find a existing user")
	public void givenAExistingUserWhenFindThenReturnUserDto() {
		long id = 1;
		when(this.userUseCase.findByUserName(any())).thenReturn(Optional.ofNullable(UserFactory.createUser(id)));
		when(this.passwordEncoder.encode(any())).thenReturn(String.format(UserFactory.USER_PASWORD_FORMAT, id));

		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(String.format(UserFactory.USER_NAME_FORMAT, id));

		verify(this.userUseCase, times(1)).findByUserName(any());

		assertNotNull(userDetails);
		assertEquals(String.format(UserFactory.USER_USER_NAME_FORMAT, id), userDetails.getUsername());

	}

	/**
	 * Testea que devuelve un Optional vacío ante un username inexistente.
	 */
	@Test
	@DisplayName("Test find a not existing user")
	public void givenAnNotExistingUserWhenFindThenReturnOptionalEmpty() {
		long id = 1;
		when(this.userUseCase.findByUserName(any()))
				.thenReturn(Optional.ofNullable(null));
		assertThrows(UsernameNotFoundException.class,
				() -> this.customUserDetailService.loadUserByUsername(String.format(UserFactory.USER_NAME_FORMAT, id)));
		verify(this.userUseCase, times(1)).findByUserName(any());
	}
}
