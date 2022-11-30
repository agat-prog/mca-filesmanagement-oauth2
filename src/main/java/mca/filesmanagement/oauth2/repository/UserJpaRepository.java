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

	Optional<UserEntity> findByUserName(String username);
}
