package mca.filesmanagement.oauth2;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Tag("UnitTest")
@DisplayName("Oauth2App tests")
public class Oauth2AppTest {

	/** Se testea el manager de autenticación. */
	@Test
	@DisplayName("Test Oauth2App")
	public void oauth2AppTest() throws Exception {
		Oauth2App oauth2App = new Oauth2App();

		assertNotNull(oauth2App.passwordEncoder());
		assertNotNull(oauth2App.tokenStore());
		assertNotNull(oauth2App.modelMapper());
	}
}
