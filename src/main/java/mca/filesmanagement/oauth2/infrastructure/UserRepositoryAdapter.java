package mca.filesmanagement.oauth2.infrastructure;

import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.infrastructure.model.UserEntity;
import mca.filesmanagement.oauth2.port.out.IuserRepository;
import mca.filesmanagement.oauth2.repository.UserJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Adaptador del repositorio JPA de usuarios.
 *
 * @author agat
 */
@Component
public class UserRepositoryAdapter implements IuserRepository {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private ModelMapper modelMapper;

	/** Constructor por defecto. */
	public UserRepositoryAdapter() {
		super();
	}

	/**
	 * Realiza la búsqueda de un usuario a partir de su identificador externo.
	 * @return Optional con el usuario encontrado.
	 */
	@Override
	@Transactional
	public Optional<UserDto> findByUserName(String username) {
		UserDto dto = null;
		Optional<UserEntity> optEntity = this.userJpaRepository
				.findByUserName(username);
		if (optEntity.isPresent()) {
			dto = this.modelMapper.map(optEntity.get(), UserDto.class);
		}
		return Optional.ofNullable(dto);
	}
}
