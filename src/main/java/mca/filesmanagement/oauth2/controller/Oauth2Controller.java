package mca.filesmanagement.oauth2.controller;

import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para el API de usuarios.
 * @author agat
 */
@RestController
@RequestMapping("/api/users")
public class Oauth2Controller {

	private static Logger LOGGER = LoggerFactory
			.getLogger(Oauth2Controller.class);

	@Autowired
	private UserService userService;

	/** Constructor por defecto. */
	public Oauth2Controller() {
		super();
	}

	/**
	 * Devuelve un usuario a partir de su userName.
	 *
	 * @param userName
	 *            Código identificativo del usuario o nick.
	 *
	 * @return Un ResponseEntity que envuelve con el HTTP code los datos del
	 *         usuario encontrado.
	 */
	@GetMapping(path = "/{userName}")
	public ResponseEntity<UserDto> findByUserName(
			@PathVariable(name = "userName", required = true) String userName) {
		LOGGER.info(String.format( "Oauth2Controller.findByUserName: userName -> %s", userName));

		Optional<UserDto> opUserDto = this.userService.findByUserName(userName);
		if (opUserDto.isPresent()) {
			return ResponseEntity.ok(opUserDto.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
