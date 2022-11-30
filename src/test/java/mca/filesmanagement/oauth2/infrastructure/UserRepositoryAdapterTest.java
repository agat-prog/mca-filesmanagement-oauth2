package mca.filesmanagement.oauth2.infrastructure;

import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mca.filesmanagement.oauth2.UserFactory;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.repository.UserJpaRepository;

@ExtendWith(SpringExtension.class)
@Tag("UnitTest")
@DisplayName("User repository adapter Use Case tests")
public class UserRepositoryAdapterTest {

	@InjectMocks
	private UserRepositoryAdapter userRepositoryAdapter;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private UserJpaRepository userJpaRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Test find a existing user")
	public void givenAExistingUserWhenFindThenReturnUserDto() {
		long id = 1;
		when(this.userJpaRepository.findByUserName(any())).thenReturn(
				Optional.ofNullable(UserFactory.createUserEntity(id)));

		when(this.modelMapper.map(any(), any()))
				.thenReturn(UserFactory.createUser(id));

		Optional<UserDto> userDtoOpt = this.userRepositoryAdapter
				.findByUserName(String.format(UserFactory.USER_NAME_FORMAT, id));
		
		verify(this.userJpaRepository, times(1)).findByUserName(any());
		
		assertTrue(userDtoOpt.isPresent());
	}
	
	@Test
	@DisplayName("Test find a not existing user")
	public void givenAnNotExistingUserWhenFindThenReturnOptionalEmpty() {
		long id = 1;
		when(this.userJpaRepository.findByUserName(any()))
				.thenReturn(Optional.ofNullable(null));

		Optional<UserDto> userDtoOpt = this.userRepositoryAdapter
				.findByUserName(String.format(UserFactory.USER_NAME_FORMAT, id));

		verify(this.userJpaRepository, times(1)).findByUserName(any());
		
		assertTrue(!userDtoOpt.isPresent());
		
		UserDto user = userDtoOpt.orElse(null);
		assertNull(user);
	}
}
