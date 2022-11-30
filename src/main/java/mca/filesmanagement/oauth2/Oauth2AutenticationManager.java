package mca.filesmanagement.oauth2;

import java.util.Arrays;
import java.util.Optional;
import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Procesador de la autenticación.
 * 
 * @author agat
 */
@Component
public class Oauth2AutenticationManager implements AuthenticationManager {

	private static Logger LOGGER = LoggerFactory
			.getLogger(Oauth2AutenticationManager.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Oauth2AutenticationManager() {
		super();
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Optional<UserDto> opUserDto = this.userService
				.findByUserName(authentication.getName());

		LOGGER.info("Oauth2AutenticationManager.user --> "
				+ authentication.getName() + "; pass:"
				+ authentication.getCredentials() + "; roles:"
				+ authentication.getAuthorities());

		if (opUserDto.isPresent() && this.passwordEncoder.matches(
				String.valueOf(authentication.getCredentials()),
				opUserDto.orElseThrow().getPassword())) {
			LOGGER.info(authentication.getName() + " autenticado");

			return new UsernamePasswordAuthenticationToken(
					authentication.getPrincipal(),
					authentication.getCredentials(),
					Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
		} else {
			throw new BadCredentialsException(String.format(
					"User [%s] not authenticated", authentication.getName()));
		}
	}
}
