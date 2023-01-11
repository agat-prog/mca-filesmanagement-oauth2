package mca.filesmanagement.oauth2.repository;

import java.util.Optional;
import mca.filesmanagement.oauth2.infrastructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de JPA para la entidad de usuarios.
 *
 * @author agat
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * Realiza la búsqueda de un usuario a partir de su identificador externo.
	 *
	 * @param username Identificador externo.
	 * @return Un Optional con el usuario encontrado.
	 */
	Optional<UserEntity> findByUserName(String username);
}
