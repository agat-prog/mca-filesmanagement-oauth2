package mca.filesmanagement.oauth2.usescases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mca.filesmanagement.oauth2.UserFactory;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.port.out.IuserRepository;

@ExtendWith(SpringExtension.class)
@Tag("UnitTest")
@DisplayName("User Case tests")
public class UserUseCaseTest {

	@InjectMocks
	private UserUseCase userUseCase;

	@Mock
	private IuserRepository userRepository;

	@Captor
	private ArgumentCaptor<UserDto> userrDtoArgumentCaptor;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Test find a existing user")
	public void givenAExistingUserWhenFindThenReturnUserDto() {
		long id = 1;
		when(this.userRepository.findByUserName(any()))
				.thenReturn(Optional.ofNullable(UserFactory.createUser(id)));

		Optional<UserDto> userDtoOpt = this.userUseCase.findByUserName(
				String.format(UserFactory.USER_NAME_FORMAT, id));

		verify(this.userRepository, times(1)).findByUserName(any());

		assertTrue(userDtoOpt.isPresent());

		UserDto user = userDtoOpt.get();
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(String.format(UserFactory.USER_FIRST_NAME_FORMAT, id),
				user.getFirstName());
		assertEquals(String.format(UserFactory.USER_NAME_FORMAT, id),
				user.getName());
		assertEquals(String.format(UserFactory.USER_USER_NAME_FORMAT, id),
				user.getUserName());
	}

	@Test
	@DisplayName("Test find a not existing user")
	public void givenAnNotExistingUserWhenFindThenReturnOptionalEmpty() {
		long id = 1;
		when(this.userRepository.findByUserName(any()))
				.thenReturn(Optional.ofNullable(null));

		Optional<UserDto> userDtoOpt = this.userUseCase.findByUserName(
				String.format(UserFactory.USER_NAME_FORMAT, id));

		verify(this.userRepository, times(1)).findByUserName(any());

		assertTrue(!userDtoOpt.isPresent());

		UserDto user = userDtoOpt.orElse(null);
		assertNull(user);
	}

	@Test
	@DisplayName("Test find with null username")
	public void givenANullUserWhenFindThenThrowException() {
		long id = 1;
		when(this.userRepository.findByUserName(notNull()))
				.thenReturn(Optional.ofNullable(UserFactory.createUser(id)));

		assertThrows(IllegalArgumentException.class,
				() -> this.userUseCase.findByUserName(null));
	}
}
