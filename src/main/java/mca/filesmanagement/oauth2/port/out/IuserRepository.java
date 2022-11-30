package mca.filesmanagement.oauth2.port.out;

import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;

/**
 * Puerto externo de acceso a información de Usuarios.
 * 
 * @author agat
 */
public interface IuserRepository {

	Optional<UserDto> findByUserName(String username);
}
