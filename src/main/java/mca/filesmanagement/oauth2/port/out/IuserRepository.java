package mca.filesmanagement.oauth2.port.out;

import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;

/**
 * Puerto externo de acceso a información de Usuarios.
 *
 * @author agat
 */
public interface IuserRepository {

	/**
	 * Realiza la búsqueda de un usuario a partir de su identificador externo.
	 *
	 * @param username Identificador externo.
	 * @return Un Optional con el usuario encontrado.
	 */
	Optional<UserDto> findByUserName(String username);
}
