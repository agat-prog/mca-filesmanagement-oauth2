package mca.filesmanagement.oauth2.service;

import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.port.in.IuserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Fachada de acceso a la capa de negocio para la información de usuarios.
 * 
 * @author agat
 */
@Service
public class UserService {

	@Autowired
	private IuserUseCase userUseCase;

	public UserService() {
		super();
	}

	/**
	 * Busca un usuario a partir de su identificador único.
	 * 
	 * @param username
	 *            Identificador de usuario (NOT NULL).
	 * @return Optional del usuario
	 */
	public Optional<UserDto> findByUserName(String username) {
		return this.userUseCase.findByUserName(username);
	}
}
