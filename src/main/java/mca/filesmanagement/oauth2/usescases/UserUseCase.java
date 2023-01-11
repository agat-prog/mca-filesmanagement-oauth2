package mca.filesmanagement.oauth2.usescases;

import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.port.in.IuserUseCase;
import mca.filesmanagement.oauth2.port.out.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Implementación del caso de uso para Usuarios.

 * @author agat
 */
@Component
public class UserUseCase implements IuserUseCase {

	@Autowired
	private IuserRepository userRepository;

	/** Constructor por defecto. */
	public UserUseCase() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<UserDto> findByUserName(String username) {
		Assert.notNull(username, "username no puede ser null");

		return this.userRepository.findByUserName(username);
	}
}
