package mca.filesmanagement.oauth2.port.in;

import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;

/**
 * Interface del puerto de entrada para el caso de uso de Usuarios.
 *
 * @author agat
 */
public interface IuserUseCase {

	/**
	 * Busca un usuario a partir de su identificador.
	 *
	 * @param username
	 *            Identificador único de usuario.
	 * @return Optional del usuario.
	 */
	Optional<UserDto> findByUserName(String username);
}
