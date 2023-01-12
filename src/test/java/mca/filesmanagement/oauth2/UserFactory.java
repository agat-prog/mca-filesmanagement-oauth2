package mca.filesmanagement.oauth2;

import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.infrastructure.model.UserEntity;

public final class UserFactory {

	public static final String USER_NAME_FORMAT = "userName_%s";
	public static final String USER_FIRST_NAME_FORMAT = "userFirstName_%s";
	public static final String USER_USER_NAME_FORMAT = "userUserName_%s";
	public static final String USER_PASWORD_FORMAT = "userPassword_%s";

	/** Constructor por defecto. */
	private UserFactory() {
		super();
	}

	/**
	 * Crea un usuario con un identificador dado.
	 * @param id Identificador.
	 * @return DTO de usuario creado.
	 */
	public static UserDto createUser(long id) {
		UserDto user = new UserDto();
		user.setFirstName(String.format(USER_FIRST_NAME_FORMAT, id));
		user.setId(id);
		user.setName(String.format(USER_NAME_FORMAT, id));
		user.setPassword(String.format(USER_PASWORD_FORMAT, id));
		user.setUserName(String.format(USER_USER_NAME_FORMAT, id));
		return user;
	}

	/**
	 * Crea una entidad de usuario.
	 * @param id Identificador.
	 * @return Entidad de usuario creado.
	 */
	public static UserEntity createUserEntity(long id) {
		UserEntity user = new UserEntity();
		user.setFirstName(String.format(USER_FIRST_NAME_FORMAT, id));
		user.setId(id);
		user.setName(String.format(USER_NAME_FORMAT, id));
		user.setPassword(String.format(USER_PASWORD_FORMAT, id));
		user.setUserName(String.format(USER_USER_NAME_FORMAT, id));
		return user;
	}
}
