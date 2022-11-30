package mca.filesmanagement.oauth2.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mca.filesmanagement.oauth2.dto.UserDto;
import mca.filesmanagement.oauth2.port.in.IuserUseCase;

/**
 * Servicio que carga todos los datos de un determinado usuario.
 * 
 * @author agat
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailService.class);

	@Autowired
	private IuserUseCase userUseCase;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CustomUserDetailService() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		LOGGER.info(String.format("CustomUserDetailService.loadUserByUsername (%s)", username));
		
		Optional<UserDto> usuarioOpt = this.userUseCase.findByUserName(username);
		if (!usuarioOpt.isPresent()) {
			LOGGER.info(String.format("El usuario [%s] no existe", username));
			throw new UsernameNotFoundException(String.format("El usuario %s no existe", username));
		}

		UserDetails user = User.builder()
				.username(usuarioOpt.get().getUserName())
				.password(passwordEncoder.encode(usuarioOpt.get().getPassword()))
				.authorities("ADMIN")
				.roles("ADMIN")
				.build();
		return user;
	}
}
