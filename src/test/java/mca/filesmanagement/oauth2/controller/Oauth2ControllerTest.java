package mca.filesmanagement.oauth2.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mca.filesmanagement.oauth2.UserFactory;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.service.UserService;

@ExtendWith(SpringExtension.class)
@Tag("UnitTest")
@DisplayName("Controller tests")
public class Oauth2ControllerTest {

	@InjectMocks
	private Oauth2Controller oauth2Controller;

	@Mock
	private UserService userService;

	/**
	 * Configuración inicial.
	 */
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
		when(this.userService.findByUserName(any())).thenReturn(Optional.ofNullable(UserFactory.createUser(id)));

		ResponseEntity<UserDto> response = this.oauth2Controller.findByUserName(UserFactory.USER_NAME_FORMAT);

		verify(this.userService, times(1)).findByUserName(any());

		assertNotNull(response);
		assertTrue(HttpStatus.OK.equals(response.getStatusCode()));

		UserDto user = response.getBody();
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(String.format(UserFactory.USER_FIRST_NAME_FORMAT, id),
				user.getFirstName());
		assertEquals(String.format(UserFactory.USER_NAME_FORMAT, id),
				user.getName());
		assertEquals(String.format(UserFactory.USER_USER_NAME_FORMAT, id),
				user.getUserName());
	}

	/**
	 * Testea que devuelve 404 ante un username no existente.
	 */
	@Test
	@DisplayName("Test find a not existing user")
	public void givenAnNotExistingUserWhenFindThenReturnOptionalEmpty() {
		when(this.userService.findByUserName(any())).thenReturn(Optional.ofNullable(null));

		ResponseEntity<UserDto> response = this.oauth2Controller.findByUserName(UserFactory.USER_NAME_FORMAT);

		verify(this.userService, times(1)).findByUserName(any());

		assertNotNull(response);
		assertTrue(HttpStatus.NOT_FOUND.equals(response.getStatusCode()));
	}
}
