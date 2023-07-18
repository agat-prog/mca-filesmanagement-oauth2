package mca.filesmanagement.oauth2.it;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import mca.filesmanagement.oauth2.UserFactory;
import mca.filesmanagement.oauth2.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Oauth2Controller complete endpoint integration tests")
public class Oauth2ControllerIT {

	private static final String OAUTH2_BASE_URL = "/api/users";
	private static final String OAUTH2_TOKEN_URL = "/oauth/token";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private WebTestClient webTestClient;

	@BeforeEach
	public void setup() {
		this.webTestClient = MockMvcWebTestClient.bindTo(mockMvc).build();
	}

	@Test
	@DisplayName("Test")
	//@DirtiesContext
	public void givenUserNameWhenGetThenShouldReturn200()
			throws InterruptedException {
		long id = 1;
		when(this.userService.findByUserName(any())).thenReturn(Optional.ofNullable(UserFactory.createUser(id)));
		
		this.webTestClient.post().uri(OAUTH2_TOKEN_URL)
				.attribute("password", "secret")
				.attribute("username", "user")
				.attribute("grant_type", "password")
				//.headers(http -> http.setBearerAuth(token))
				.exchange()
				.expectStatus().isAccepted();
	}
}
